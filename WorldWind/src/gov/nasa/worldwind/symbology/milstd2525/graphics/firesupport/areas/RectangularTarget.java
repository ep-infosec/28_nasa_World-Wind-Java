/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package gov.nasa.worldwind.symbology.milstd2525.graphics.firesupport.areas;

import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.symbology.*;
import gov.nasa.worldwind.util.*;

import java.util.*;

/**
 * Implementation of the Rectangular Target graphic (hierarchy 2.X.4.3.1.1, SIDC: G*FPATR---****X).
 *
 * @author pabercrombie
 * @version $Id$
 */
public class RectangularTarget extends AbstractRectangularGraphic
{
    /** Function ID for the Rectangular Target graphic. */
    public final static String FUNCTION_ID = "ATR---";

    /** Create a new target. */
    public RectangularTarget()
    {
        super();
    }

    /**
     * {@inheritDoc}
     *
     * @param positions Control points. This graphic uses only one control point, which determines the center of the
     *                  quad.
     */
    public void setPositions(Iterable<? extends Position> positions)
    {
        if (positions == null)
        {
            String message = Logging.getMessage("nullValue.PositionsListIsNull");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }

        Iterator<? extends Position> iterator = positions.iterator();
        if (!iterator.hasNext())
        {
            String message = Logging.getMessage("generic.InsufficientPositions");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }

        this.quad.setCenter(iterator.next());
    }

    /** {@inheritDoc} */
    @Override
    public void setModifier(String modifier, Object value)
    {
        if (SymbologyConstants.DISTANCE.equals(modifier) && (value instanceof Iterable))
        {
            Iterator iterator = ((Iterable) value).iterator();
            this.setWidth((Double) iterator.next());
            this.setLength((Double) iterator.next());
        }
        else if (SymbologyConstants.AZIMUTH.equals(modifier) && (value instanceof Angle))
        {
            this.quad.setHeading((Angle) value);
        }
        else
        {
            super.setModifier(modifier, value);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Object getModifier(String modifier)
    {
        if (SymbologyConstants.DISTANCE.equals(modifier))
            return Arrays.asList(this.getWidth(), this.getLength());
        else if (SymbologyConstants.AZIMUTH.equals(modifier))
            return this.quad.getHeading();
        else
            return super.getModifier(modifier);
    }

    /** {@inheritDoc} */
    public Iterable<? extends Position> getPositions()
    {
        return Arrays.asList(new Position(this.quad.getCenter(), 0));
    }

    /** Create labels for the graphic. */
    @Override
    protected void createLabels()
    {
        String text = this.getText();
        if (!WWUtil.isEmpty(text))
        {
            this.addLabel(this.getText());
        }
    }

    @Override
    protected void determineLabelPositions(DrawContext dc)
    {
        this.labels.get(0).setPosition(new Position(this.quad.getCenter(), 0));
    }
}
