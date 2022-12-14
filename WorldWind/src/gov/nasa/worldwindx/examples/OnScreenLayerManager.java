/*
Copyright (C) 2001, 2009 United States Government
as represented by the Administrator of the
National Aeronautics and Space Administration.
All Rights Reserved.
*/
package gov.nasa.worldwindx.examples;

import gov.nasa.worldwindx.examples.util.LayerManagerLayer;

/**
 * Demonstrates an on-screen layer manager using {@link LayerManagerLayer}. The layer manager allows individual layers
 * to be turned on or off. The layer list can be reordered by clicking and dragging the layer names.
 *
 * @author Patrick Murris
 * @version $Id$
 * @see LayerTreeUsage
 * @see LayerPanel
 */
public class OnScreenLayerManager extends ApplicationTemplate
{
    public static class AppFrame extends ApplicationTemplate.AppFrame
    {
        public AppFrame()
        {
            super(true, false, false);

            // Add the layer manager layer to the model layer list
            getWwd().getModel().getLayers().add(new LayerManagerLayer(getWwd()));
        }
    }

    public static void main(String[] args)
    {
        ApplicationTemplate.start("World Wind On-Screen Layer Manager", AppFrame.class);
    }
}
