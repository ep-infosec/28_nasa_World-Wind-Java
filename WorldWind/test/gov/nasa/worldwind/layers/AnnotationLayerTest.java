/*
Copyright (C) 2001, 2007 United States Government as represented by
the Administrator of the National Aeronautics and Space Administration.
All Rights Reserved.
*/
package gov.nasa.worldwind.layers;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;

/**
 * @author dcollins
 * @version $Id$
 */
public class AnnotationLayerTest extends junit.framework.TestCase
{
    /*************************************************************************************************************/
    /** Basic Operation Tests **/
    /*************************************************************************************************************/

    @org.junit.Test
    public void testConstructor()
    {
        AnnotationLayer layer;

        // Test the parameterless constructor.
        layer = new AnnotationLayer();
        assertNotNull("", layer);
    }

    @org.junit.Test
    public void testAddAnnotation()
    {
        Iterable<Annotation> annotations = createExampleIterable();

        AnnotationLayer layer = new AnnotationLayer();
        for (Annotation item : annotations)
            layer.addAnnotation(item);

        // Test that the layer contains the annotations.
        assertEquals("", annotations, layer.getAnnotations());
    }

    @org.junit.Test
    public void testAddAnnotations()
    {
        Iterable<Annotation> annotations = createExampleIterable();

        AnnotationLayer layer = new AnnotationLayer();
        layer.addAnnotations(annotations);

        // Test that the layer contains the annotations.
        assertEquals("", annotations, layer.getAnnotations());
    }

    @org.junit.Test
    public void testRemoveAnnotation()
    {
        Iterable<Annotation> annotations = createExampleIterable();

        AnnotationLayer layer = new AnnotationLayer();
        for (Annotation item : annotations)
            layer.addAnnotation(item);
        for (Annotation item : annotations)
            layer.removeAnnotation(item);

        // Test that the layer contains no annotations.
        assertFalse("", layer.getAnnotations().iterator().hasNext());
    }

    @org.junit.Test
    public void testRemoveAllAnnotations()
    {
        Iterable<Annotation> annotations = createExampleIterable();

        AnnotationLayer layer = new AnnotationLayer();
        layer.addAnnotations(annotations);
        layer.removeAllAnnotations();

        // Test that the layer contains no annotations.
        assertFalse("", layer.getAnnotations().iterator().hasNext());
    }

    @org.junit.Test
    public void testSetAnnotations()
    {
        Iterable<Annotation> annotations = createExampleIterable();

        AnnotationLayer layer = new AnnotationLayer();
        layer.setAnnotations(annotations);

        // Test that the layer points to the Iterable.
        assertSame("", annotations, layer.getAnnotations());
    }

    /*************************************************************************************************************/
    /** Edge Case Tests **/
    /*************************************************************************************************************/

    @org.junit.Test
    public void testSetAnnotationsClearsAnnotations()
    {
        Iterable<Annotation> annotations = createExampleIterable();

        AnnotationLayer layer = new AnnotationLayer();
        layer.addAnnotations(annotations);
        layer.setAnnotations(annotations);
        layer.setAnnotations(null);

        // Test that the layer does not point to the Iterable.
        assertNotSame("", annotations, layer.getAnnotations());
        // Test that the layer contains no annotations.
        assertFalse("", layer.getAnnotations().iterator().hasNext());
    }

    @org.junit.Test
    public void testSetAnnotationsThenAddAnnotations()
    {
        Iterable<Annotation> annotations = createExampleIterable();

        AnnotationLayer layer = new AnnotationLayer();
        layer.setAnnotations(annotations);
        layer.setAnnotations(null);
        layer.addAnnotations(annotations);

        // Test that the layer does not point to the Iterable.
        assertNotSame("", annotations, layer.getAnnotations());
        // Test that the layer contains the annotations.
        assertEquals("", annotations, layer.getAnnotations());
    }

    @org.junit.Test
    public void testMaliciousGetAnnotations()
    {
        Iterable<Annotation> annotations = createExampleIterable();

        AnnotationLayer layer = new AnnotationLayer();
        layer.addAnnotations(annotations);

        Iterable<Annotation> layerAnnotations = layer.getAnnotations();

        // Test that the returned list cannot be modified.
        try
        {
            if (layerAnnotations instanceof java.util.Collection)
            {
                java.util.Collection<Annotation> collection = (java.util.Collection<Annotation>) layerAnnotations;
                collection.clear();
            }
            else
            {
                java.util.Iterator<Annotation> iter = layerAnnotations.iterator();
                while (iter.hasNext())
                {
                    iter.next();
                    iter.remove();
                }
            }
        }
        catch (UnsupportedOperationException e)
        {
            e.printStackTrace();
        }

        // Test that the layer contents do not change, even if the returned list can be modified.
        assertEquals("", annotations, layerAnnotations);
    }

    @org.junit.Test
    public void testMaliciousSetAnnotations()
    {
        // Create an Iterable with null elements.
        java.util.List<Annotation> list = new java.util.ArrayList<Annotation>();
        list.add(null);

        AnnotationLayer layer = new AnnotationLayer()
        {
            // Override to avoid View initialization issues.
            public boolean isLayerActive(DrawContext dc)
            {
                return true;
            }
        };
        layer.setAnnotations(list);

        DrawContext dc = new DrawContextImpl();
        dc.setModel(new BasicModel());
        dc.setView(new BasicOrbitView());
        
        try
        {
            // Test that the layer does not fail when the Iterable is used.
            layer.render(dc);
        }
        catch (NullPointerException e)
        {
            fail("Layer does not check for null elements in Iterable");
        }
    }

    /*************************************************************************************************************/
    /** Exceptional Condition Tests **/
    /*************************************************************************************************************/

    @org.junit.Test
    public void testAddAnnotationFail()
    {
        Iterable<Annotation> annotations = createExampleIterable();

        AnnotationLayer layer = new AnnotationLayer();
        layer.setAnnotations(annotations);

        try
        {
            // Expecting an IllegalStateException here.
            layer.addAnnotation(new GlobeAnnotation("", Position.ZERO));
            fail("");
        }
        catch (IllegalStateException e)
        {
        }
    }

    @org.junit.Test
    public void testAddAnnotationsFail()
    {
        Iterable<Annotation> annotations = createExampleIterable();

        AnnotationLayer layer = new AnnotationLayer();
        layer.setAnnotations(annotations);

        try
        {
            // Expecting an IllegalStateException here.
            layer.addAnnotations(annotations);
            fail("");
        }
        catch (IllegalStateException e)
        {
        }
    }

    @org.junit.Test
    public void testRemoveAnnotationFail()
    {
        Iterable<Annotation> annotations = createExampleIterable();

        AnnotationLayer layer = new AnnotationLayer();
        layer.setAnnotations(annotations);

        try
        {
            // Expecting an IllegalStateException here.
            layer.removeAnnotation(new GlobeAnnotation("", Position.ZERO));
            fail("");
        }
        catch (IllegalStateException e)
        {
        }
    }

    @org.junit.Test
    public void testRemoveAllAnnotationsFail()
    {
        Iterable<Annotation> annotations = createExampleIterable();

        AnnotationLayer layer = new AnnotationLayer();
        layer.setAnnotations(annotations);

        try
        {
            // Expecting an IllegalStateException here.
            layer.removeAllAnnotations();
            fail("");
        }
        catch (IllegalStateException e)
        {
        }
    }

    /*************************************************************************************************************/
    /** Helper Methods **/
    /*************************************************************************************************************/

    @SuppressWarnings({"JavaDoc"})
    private static void assertEquals(String message, Iterable<Annotation> expected, Iterable<Annotation> actual)
    {
        if (expected == null)
        {
            assertNull(message, actual);
        }
        else
        {
            java.util.Iterator<Annotation> expectedIter = expected.iterator(), actualIter = actual.iterator();
            // Compare the elements in each iterator, as long as they both have elements.
            while (expectedIter.hasNext() && actualIter.hasNext())
                assertEquals(message, expectedIter.next(), actualIter.next());
            // If either iterator has more elements, then their lengths are different.
            assertFalse(message, expectedIter.hasNext() || actualIter.hasNext());
        }
    }

    private static Iterable<Annotation> createExampleIterable()
    {
        //noinspection RedundantArrayCreation
        return java.util.Arrays.asList(new Annotation[] {
                new GlobeAnnotation("", Position.ZERO),
                new GlobeAnnotation("", Position.ZERO),
                new GlobeAnnotation("", Position.ZERO)});
    }

    public static void main(String[] args)
    {
        new junit.textui.TestRunner().doRun(new junit.framework.TestSuite(AnnotationLayerTest.class));
    }
}