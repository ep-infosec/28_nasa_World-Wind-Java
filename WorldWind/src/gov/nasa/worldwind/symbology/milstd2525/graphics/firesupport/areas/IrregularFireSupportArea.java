/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package gov.nasa.worldwind.symbology.milstd2525.graphics.firesupport.areas;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.symbology.SymbologyConstants;
import gov.nasa.worldwind.symbology.milstd2525.Label;
import gov.nasa.worldwind.symbology.milstd2525.graphics.command.general.areas.BasicArea;
import gov.nasa.worldwind.util.WWUtil;

import java.util.*;

/**
 * Implementation of the irregular Fire Support area graphics. This class implements the following graphics:
 * <p/>
 * <ul> <li>Area Target (2.X.4.3.1)</li> <li>Bomb (2.X.4.3.1.5)</li> <li>Airspace Coordination Area (ACA), Irregular
 * (2.X.4.3.2.2.1)</li> <li>Free Fire Area (FFA), Irregular (2.X.4.3.2.3.1)</li> <li>Restrictive Fire Area (RFA),
 * Irregular (2.X.4.3.2.5.1)</li> <li>Terminally Guided Munitions Footprint</li> <li>Sensor Zone, Irregular</li>
 * <li>Dead Space Area, Irregular</li> <li>Zone of Responsibility, Irregular</li> <li>Target Build-up Area,
 * Irregular</li> <li>Target Value Area, Irregular</li> <li>Artillery Target Intelligence Zone, Irregular (
 * 2.X.4.3.3.1.1)</li> <li>Call For Fire Zone, Irregular (2.X.4.3.3.2.1)</li> <li>Censor Zone, Irregular
 * (2.X.4.3.3.4.1)</li> <li>Critical Friendly Zone, Irregular (2.X.4.3.3.6.1)</li> </ul>
 *
 * @author pabercrombie
 * @version $Id$
 */
public class IrregularFireSupportArea extends BasicArea
{
    /** Function ID for the Fire Support Area graphic (2.X.4.3.2.1.1). */
    public final static String FUNCTION_ID_FSA = "ACSI--";
    /** Function ID of the Bomb graphic (2.X.4.3.1.5). */
    public final static String FUNCTION_ID_BOMB = "ATB---";
    /** Function ID of the Area Target graphic (2.X.4.3.1). */
    public final static String FUNCTION_ID_TARGET = "AT----";
    /** Function ID for the Free Fire Area graphic (2.X.4.3.2.3.1). */
    public final static String FUNCTION_ID_FFA = "ACFI--";
    /** Function ID for the Restrictive Fire Area graphic (2.X.4.3.2.5.1). */
    public final static String FUNCTION_ID_RFA = "ACRI--";
    /** Function ID of the Airspace Coordination Area graphic (2.X.4.3.2.2.1). */
    public final static String FUNCTION_ID_ACA = "ACAI--";
    /** Function ID of the Terminally Guided Munitions Footprint graphic. */
    public final static String FUNCTION_ID_TERMINALLY_GUIDED_MUNITIONS_FOOTPRINT = "ACT---";
    /** Function ID for the Sensor Zone graphic. */
    public final static String FUNCTION_ID_SENSOR_ZONE = "ACEI--";
    /** Function ID for the Dead Space Area graphic. */
    public final static String FUNCTION_ID_DEAD_SPACE_AREA = "ACDI--";
    /** Function ID for the Zone of Responsibility graphic. */
    public final static String FUNCTION_ID_ZONE_OF_RESPONSIBILITY = "ACZI--";
    /** Function ID for the Target Build-up Area graphic. */
    public final static String FUNCTION_ID_TARGET_BUILDUP = "ACBI--";
    /** Function ID for the Target Value Area graphic. */
    public final static String FUNCTION_ID_TARGET_VALUE = "ACVI--";
    /** Function ID for the Artillery Target Intelligence Zone graphic ( 2.X.4.3.3.1.1). */
    public final static String FUNCTION_ID_ATI = "AZII--";
    /** Function ID for the Call For Fire Zone graphic (2.X.4.3.3.2.1). */
    public final static String FUNCTION_ID_CFF = "AZXI--";
    /** Function ID for the Censor Zone graphic (2.X.4.3.3.4.1). */
    public final static String FUNCTION_ID_CENSOR_ZONE = "AZCI--";
    /** Function ID for the Critical Friendly Zone graphic (2.X.4.3.3.6.1). */
    public final static String FUNCTION_ID_CF = "AZFI--";

    /** Center text block on label position when the text is left aligned. */
    protected final static Offset LEFT_ALIGN_OFFSET = new Offset(-0.5d, -0.5d, AVKey.FRACTION, AVKey.FRACTION);

    /** Create the area graphic. */
    public IrregularFireSupportArea()
    {
        this.setShowHostileIndicator(false);
    }

    /**
     * Indicates the function IDs of rectangular Fire Support area graphics that display a date/time range as a separate
     * label at the left side of the rectangle. Whether or not a graphic supports this is determined by the graphic's
     * template in MIL-STD-2525C.
     *
     * @return A Set containing the function IDs of graphics that support a date/time label separate from the graphic's
     *         main label.
     */
    public static Set<String> getGraphicsWithTimeLabel()
    {
        return new HashSet<String>(Arrays.asList(
            FUNCTION_ID_FSA,
            FUNCTION_ID_SENSOR_ZONE,
            FUNCTION_ID_DEAD_SPACE_AREA,
            FUNCTION_ID_ZONE_OF_RESPONSIBILITY,
            FUNCTION_ID_TARGET_BUILDUP,
            FUNCTION_ID_TARGET_VALUE,
            FUNCTION_ID_ATI,
            FUNCTION_ID_CFF,
            FUNCTION_ID_CENSOR_ZONE,
            FUNCTION_ID_CF));
    }

    /** {@inheritDoc} */
    @Override
    public String getCategory()
    {
        return SymbologyConstants.CATEGORY_FIRE_SUPPORT;
    }

    @Override
    protected void createLabels()
    {
        FireSupportTextBuilder textBuilder = new FireSupportTextBuilder();
        String[] allText = textBuilder.createText(this);

        String text = allText[0];
        if (!WWUtil.isEmpty(text))
        {
            Label mainLabel = this.addLabel(text);
            mainLabel.setTextAlign(this.getLabelAlignment());
        }

        if (allText.length > 1 && !WWUtil.isEmpty(allText[1]))
        {
            Label timeLabel = this.addLabel(allText[1]);
            timeLabel.setTextAlign(AVKey.RIGHT);

            // Align the upper right corner of the time label with the upper right corner of the polygon.
            timeLabel.setOffset(new Offset(0d, 0d, AVKey.FRACTION, AVKey.FRACTION));
        }
    }

    @Override
    protected void determineLabelPositions(DrawContext dc)
    {
        // Determine main label position
        super.determineLabelPositions(dc);

        if (this.labels.size() > 1)
        {
            Position pos = this.computeTimeLabelPosition(dc);
            if (pos != null)
            {
                this.labels.get(1).setPosition(pos);
            }
        }
    }

    /**
     * Determine the position of the time range label. This label is placed at the North-West corner of the polygon.
     *
     * @param dc Current draw context.
     *
     * @return Position for the time range label, or null if the position cannot be determined.
     */
    protected Position computeTimeLabelPosition(DrawContext dc)
    {
        Iterable<? extends LatLon> positions = this.polygon.getLocations(dc.getGlobe());

        // Find the North-West corner of the bounding sector.
        Sector boundingSector = Sector.boundingSector(positions);
        LatLon nwCorner = new LatLon(boundingSector.getMaxLatitude(), boundingSector.getMinLongitude());

        Angle minDistance = Angle.POS180;
        LatLon nwMost = null;

        // We want to place the label at the North-West corner of the polygon. Loop through the locations
        // and find the one that is closest so the North-West corner of the bounding sector.
        for (LatLon location : positions)
        {
            Angle dist = LatLon.greatCircleDistance(location, nwCorner);
            if (dist.compareTo(minDistance) < 0)
            {
                minDistance = dist;
                nwMost = location;
            }
        }

        // Place the time label at the North-West position.
        if (nwMost != null)
        {
            return new Position(nwMost, 0);
        }
        return null;
    }

    /**
     * Indicates the alignment of the graphic's main label.
     *
     * @return Alignment for the main label. One of AVKey.CENTER, AVKey.LEFT, or AVKey.RIGHT.
     */
    @Override
    protected String getLabelAlignment()
    {
        boolean isACA = FUNCTION_ID_ACA.equals(this.getFunctionId());

        // Airspace Coordination Area labels are left aligned. All others are center aligned.
        if (isACA)
            return AVKey.LEFT;
        else
            return AVKey.CENTER;
    }

    /**
     * Indicates the default offset applied to the graphic's main label. This offset may be overridden by the graphic
     * attributes.
     *
     * @return Offset to apply to the main label.
     */
    @Override
    protected Offset getDefaultLabelOffset()
    {
        boolean isACA = FUNCTION_ID_ACA.equals(this.getFunctionId());

        // Airspace Coordination Area labels are left aligned. Adjust the offset to center the left aligned label
        // in the circle. (This is not necessary with a center aligned label because centering the text automatically
        // centers the label in the circle).
        if (isACA)
            return LEFT_ALIGN_OFFSET;
        else
            return super.getDefaultLabelOffset();
    }
}
