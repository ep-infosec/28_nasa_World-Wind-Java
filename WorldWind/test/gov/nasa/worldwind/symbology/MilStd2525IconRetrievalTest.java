/*
 * Copyright (C) 2011 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package gov.nasa.worldwind.symbology;

import gov.nasa.worldwind.avlist.*;
import gov.nasa.worldwind.symbology.milstd2525.MilStd2525IconRetriever;
import junit.framework.*;
import junit.textui.TestRunner;

import java.awt.image.*;

public class MilStd2525IconRetrievalTest
{
    // TODO: test all possible values for Standard Identity and Status
    // TODO: test unframed icons

    // This path should correspond to the location of the appropriate symbology source icons on your system
    private final static String URL
        = "http://worldwindserver.net/milstd2525/";

    public static class RetrievalTests extends TestCase
    {
        //////////////////////////////////////////////////////////
        // Test retrieval of a MilStd2525 icon from both a remote
        // server and the local file system.
        //////////////////////////////////////////////////////////

        @org.junit.Test
        public void testServerRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVListImpl params = new AVListImpl();
            BufferedImage img = symGen.createIcon("SUAPC----------", params);
            assertNotNull(img);
        }
    }

    public static class ParsingTests extends TestCase
    {
        //////////////////////////////////////////////////////////
        // Test parsing of the Symbol Code.
        // MilStd2525 SymCodes should be exactly 15 characters.
        //////////////////////////////////////////////////////////

        @org.junit.Test
        public void testParseCodeTooShort()
        {
            try
            {
                MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
                symGen.createIcon("SUAPC");
                fail("Should raise an IllegalArgumentException");
            }
            catch (Exception e)
            {
            }
        }

        @org.junit.Test
        public void testParseCodeTooLong()
        {
            try
            {
                MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
                symGen.createIcon("SUAPCTEST");
                fail("Should raise an IllegalArgumentException");
            }
            catch (Exception e)
            {
            }
        }

        @org.junit.Test
        public void testParseNullCode()
        {
            try
            {
                MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
                symGen.createIcon(null);
                fail("Should raise an IllegalArgumentException");
            }
            catch (Exception e)
            {
            }
        }
    }

    public static class CodeTests extends TestCase
    {
        //////////////////////////////////////////////////////////
        // Test validity of Symbol Code.
        // Codes containing invalid letters should retrieve a null image.
        // TODO: is this correct?
        //////////////////////////////////////////////////////////

        @org.junit.Test
        public void testInvalidCodingScheme()
        {
            try
            {
                MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
                symGen.createIcon(".UAPC----------");
                fail("Should raise an IllegalArgumentException");
            }
            catch (Exception e)
            {
            }
        }

        @org.junit.Test
        public void testInvalidStandardIdentity()
        {
            try
            {
                MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
                symGen.createIcon("S.APC----------");
                fail("Should raise an IllegalArgumentException");
            }
            catch (Exception e)
            {
            }
        }

        @org.junit.Test
        public void testInvalidBattleDimension()
        {
            try
            {
                MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
                symGen.createIcon("SU.PC----------");
                fail("Should raise an IllegalArgumentException");
            }
            catch (Exception e)
            {
            }
        }

        @org.junit.Test
        public void testInvalidStatus()
        {
            try
            {
                MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
                symGen.createIcon("SUA.C----------");
                fail("Should raise an IllegalArgumentException");
            }
            catch (Exception e)
            {
            }
        }

        @org.junit.Test
        public void testInvalidFunctionID()
        {
            try
            {
                MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
                symGen.createIcon("SUAPZ----------");
                fail("Should raise an IllegalArgumentException");
            }
            catch (Exception e)
            {
            }
        }

        /*
        @org.junit.Test
        public void testInvalidModifierCode()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            symGen.createIcon("SUAP------ZZ---");
            assertNull(img);
        }

        @org.junit.Test
        public void testInvalidCountryCode()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            symGen.createIcon("SUAPC-------ZZ-");
            assertNull(img);
        }
        */

        @org.junit.Test
        public void testInvalidOrderOfBattle()
        {
            try
            {
                MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
                symGen.createIcon("SUAPC---------.");
                fail("Should raise an IllegalArgumentException");
            }
            catch (Exception e)
            {
            }
        }
    }

    public static class WarfightingFunctionIDTests extends TestCase
    {
        //////////////////////////////////////////////////////////
        // Test for the presence and retrieval of a every possible Warfighting base icon by
        // iterating through all combinations of Standard Identity and FunctionID.
        //////////////////////////////////////////////////////////

        @org.junit.Test
        public void testUnknownFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : WarfightingUnknownFunctionIDs)
            {
                img = symGen.createIcon("SUZP" + s + "-----", params);
                assertNotNull("Icon " + "0.suzp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SFZP" + s + "-----", params);
                assertNotNull("Icon " + "1.sfzp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SNZP" + s + "-----", params);
                assertNotNull("Icon " + "2.snzp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SHZP" + s + "-----", params);
                assertNotNull("Icon " + "3.shzp" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testSpaceFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : WarfightingSpaceFunctionIDs)
            {
                img = symGen.createIcon("SUPP" + s + "-----", params);
                assertNotNull("Icon " + "0.supp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SFPP" + s + "-----", params);
                assertNotNull("Icon " + "1.sfpp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SNPP" + s + "-----", params);
                assertNotNull("Icon " + "2.snpp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SHPP" + s + "-----", params);
                assertNotNull("Icon " + "3.shpp" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testAirFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : WarfightingAirFunctionIDs)
            {
                img = symGen.createIcon("SUAP" + s + "-----", params);
                assertNotNull("Icon " + "0.suap" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SFAP" + s + "-----", params);
                assertNotNull("Icon " + "1.sfap" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SNAP" + s + "-----", params);
                assertNotNull("Icon " + "2.snap" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SHAP" + s + "-----", params);
                assertNotNull("Icon " + "3.shap" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testGroundFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : WarfightingGroundFunctionIDs)
            {
                String padding = "-----";
                if (s.substring(0, 1).equalsIgnoreCase("I"))    // handle special case of installations
                    padding = "H----";

                img = symGen.createIcon("SUGP" + s + padding, params);
                assertNotNull("Icon " + "0.sugp" + s.toLowerCase() + padding + ".png not found.", img);

                img = symGen.createIcon("SFGP" + s + padding, params);
                assertNotNull("Icon " + "1.sfgp" + s.toLowerCase() + padding + ".png not found.", img);

                img = symGen.createIcon("SNGP" + s + padding, params);
                assertNotNull("Icon " + "2.sngp" + s.toLowerCase() + padding + ".png not found.", img);

                img = symGen.createIcon("SHGP" + s + padding, params);
                assertNotNull("Icon " + "3.shgp" + s.toLowerCase() + padding + ".png not found.", img);
            }
        }

        @org.junit.Test
        public void testSeaSurfaceFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : WarfightingSeaSurfaceFunctionIDs)
            {
                img = symGen.createIcon("SUSP" + s + "-----", params);
                assertNotNull("Icon " + "0.susp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SFSP" + s + "-----", params);
                assertNotNull("Icon " + "1.sfsp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SNSP" + s + "-----", params);
                assertNotNull("Icon " + "2.snsp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SHSP" + s + "-----", params);
                assertNotNull("Icon " + "3.shsp" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testSubsurfaceFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : WarfightingSubsurfaceFunctionIDs)
            {
                img = symGen.createIcon("SUUP" + s + "-----", params);
                assertNotNull("Icon " + "0.suup" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SFUP" + s + "-----", params);
                assertNotNull("Icon " + "1.sfup" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SNUP" + s + "-----", params);
                assertNotNull("Icon " + "2.snup" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SHUP" + s + "-----", params);
                assertNotNull("Icon " + "3.shup" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testSpecialOpsFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : WarfightingSOFFunctionIDs)
            {
                img = symGen.createIcon("SUFP" + s + "-----", params);
                assertNotNull("Icon " + "0.sufp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SFFP" + s + "-----", params);
                assertNotNull("Icon " + "1.sffp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SNFP" + s + "-----", params);
                assertNotNull("Icon " + "2.snfp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("SHFP" + s + "-----", params);
                assertNotNull("Icon " + "3.shfp" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testIconOverlayRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            BufferedImage img = null;

            for (String s : IconOverlays)
            {
                img = symGen.retrieveImageFromURL(s, img);
                assertNotNull("Icon overlay " + s + " not found.", img);
            }
        }
    }

    public static class SignalsIntelligenceFunctionIDTests extends TestCase
    {
        //////////////////////////////////////////////////////////
        // Test for the presence and retrieval of a every possible Signals Intelligence
        // base icon by iterating through all combinations of Standard Identity and
        // FunctionID.
        //////////////////////////////////////////////////////////

        @org.junit.Test
        public void testSpaceFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : SignalsIntelligenceSpaceFunctionIDs)
            {
                img = symGen.createIcon("IUPP" + s + "-----", params);
                assertNotNull("Icon " + "0.iupp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("IFPP" + s + "-----", params);
                assertNotNull("Icon " + "1.ifpp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("INPP" + s + "-----", params);
                assertNotNull("Icon " + "2.inpp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("IHPP" + s + "-----", params);
                assertNotNull("Icon " + "3.ihpp" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testAirFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : SignalsIntelligenceAirFunctionIDs)
            {
                img = symGen.createIcon("IUAP" + s + "-----", params);
                assertNotNull("Icon " + "0.iuap" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("IFAP" + s + "-----", params);
                assertNotNull("Icon " + "1.ifap" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("INAP" + s + "-----", params);
                assertNotNull("Icon " + "2.inap" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("IHAP" + s + "-----", params);
                assertNotNull("Icon " + "3.ihap" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testGroundFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : SignalsIntelligenceGroundFunctionIDs)
            {
                img = symGen.createIcon("IUGP" + s + "-----", params);
                assertNotNull("Icon " + "0.iugp" + s.toLowerCase() + "-----" + ".png not found.", img);

                img = symGen.createIcon("IFGP" + s + "-----", params);
                assertNotNull("Icon " + "1.ifgp" + s.toLowerCase() + "-----" + ".png not found.", img);

                img = symGen.createIcon("INGP" + s + "-----", params);
                assertNotNull("Icon " + "2.ingp" + s.toLowerCase() + "-----" + ".png not found.", img);

                img = symGen.createIcon("IHGP" + s + "-----", params);
                assertNotNull("Icon " + "3.ihgp" + s.toLowerCase() + "-----" + ".png not found.", img);
            }
        }

        @org.junit.Test
        public void testSeaSurfaceFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : SignalsIntelligenceSeaSurfaceFunctionIDs)
            {
                img = symGen.createIcon("IUSP" + s + "-----", params);
                assertNotNull("Icon " + "0.iusp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("IFSP" + s + "-----", params);
                assertNotNull("Icon " + "1.ifsp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("INSP" + s + "-----", params);
                assertNotNull("Icon " + "2.insp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("IHSP" + s + "-----", params);
                assertNotNull("Icon " + "3.ihsp" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testSubsurfaceFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : SignalsIntelligenceSubsurfaceFunctionIDs)
            {
                img = symGen.createIcon("IUUP" + s + "-----", params);
                assertNotNull("Icon " + "0.iuup" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("IFUP" + s + "-----", params);
                assertNotNull("Icon " + "1.ifup" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("INUP" + s + "-----", params);
                assertNotNull("Icon " + "2.inup" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("IHUP" + s + "-----", params);
                assertNotNull("Icon " + "3.ihup" + s.toLowerCase() + "-----.png not found.", img);
            }
        }
    }

    public static class StabilityOperationsFunctionIDTests extends TestCase
    {
        //////////////////////////////////////////////////////////
        // Test for the presence and retrieval of a every possible Stability Operations
        // base icon by iterating through all combinations of Standard Identity and
        // FunctionID.
        //////////////////////////////////////////////////////////

        @org.junit.Test
        public void testViolentActivitiesFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : StabilityOperationsViolentActivitiesFunctionIDs)
            {
                img = symGen.createIcon("OUVP" + s + "-----", params);
                assertNotNull("Icon " + "0.ouvp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OFVP" + s + "-----", params);
                assertNotNull("Icon " + "1.ofvp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("ONVP" + s + "-----", params);
                assertNotNull("Icon " + "2.onvp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OHVP" + s + "-----", params);
                assertNotNull("Icon " + "3.ohvp" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testLocationsFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : StabilityOperationsLocationsFunctionIDs)
            {
                img = symGen.createIcon("OULP" + s + "-----", params);
                assertNotNull("Icon " + "0.oulp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OFLP" + s + "-----", params);
                assertNotNull("Icon " + "1.oflp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("ONLP" + s + "-----", params);
                assertNotNull("Icon " + "2.onlp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OHLP" + s + "-----", params);
                assertNotNull("Icon " + "3.ohlp" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testOperationsFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : StabilityOperationsOperationsFunctionIDs)
            {
                img = symGen.createIcon("OUOP" + s + "-----", params);
                assertNotNull("Icon " + "0.ouop" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OFOP" + s + "-----", params);
                assertNotNull("Icon " + "1.ofop" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("ONOP" + s + "-----", params);
                assertNotNull("Icon " + "2.onop" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OHOP" + s + "-----", params);
                assertNotNull("Icon " + "3.ohop" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testItemsFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : StabilityOperationsItemsFunctionIDs)
            {
                img = symGen.createIcon("OUIP" + s + "-----", params);
                assertNotNull("Icon " + "0.ouip" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OFIP" + s + "-----", params);
                assertNotNull("Icon " + "1.ofip" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("ONIP" + s + "-----", params);
                assertNotNull("Icon " + "2.onip" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OHIP" + s + "-----", params);
                assertNotNull("Icon " + "3.ohip" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testIndividualFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : StabilityOperationsIndividualFunctionIDs)
            {
                img = symGen.createIcon("OUPP" + s + "-----", params);
                assertNotNull("Icon " + "0.oupp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OFPP" + s + "-----", params);
                assertNotNull("Icon " + "1.ofpp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("ONPP" + s + "-----", params);
                assertNotNull("Icon " + "2.onpp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OHPP" + s + "-----", params);
                assertNotNull("Icon " + "3.ohpp" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testNonmilitaryFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : StabilityOperationsNonmilitaryFunctionIDs)
            {
                img = symGen.createIcon("OUGP" + s + "-----", params);
                assertNotNull("Icon " + "0.ougp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OFGP" + s + "-----", params);
                assertNotNull("Icon " + "1.ofgp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("ONGP" + s + "-----", params);
                assertNotNull("Icon " + "2.ongp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OHGP" + s + "-----", params);
                assertNotNull("Icon " + "3.ohgp" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        @org.junit.Test
        public void testRapeFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : StabilityOperationsRapeFunctionIDs)
            {
                img = symGen.createIcon("OURP" + s + "-----", params);
                assertNotNull("Icon " + "0.ourp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OFRP" + s + "-----", params);
                assertNotNull("Icon " + "1.ofrp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("ONRP" + s + "-----", params);
                assertNotNull("Icon " + "2.onrp" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("OHRP" + s + "-----", params);
                assertNotNull("Icon " + "3.ohrp" + s.toLowerCase() + "-----.png not found.", img);
            }
        }
    }

    public static class EmergencyManagementFunctionIDTests extends TestCase
    {
        //////////////////////////////////////////////////////////
        // Test for the presence and retrieval of a every possible Emergency Management
        // base icon by iterating through all combinations of Standard Identity and
        // FunctionID.
        //////////////////////////////////////////////////////////

        @org.junit.Test
        public void testIncidentFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : EmergencyManagementIncidentsFunctionIDs)
            {
                img = symGen.createIcon("EUIP" + s + "-----", params);
                assertNotNull("Icon " + "0.euip" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("EFIP" + s + "-----", params);
                assertNotNull("Icon " + "1.efip" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("ENIP" + s + "-----", params);
                assertNotNull("Icon " + "2.enip" + s.toLowerCase() + "-----.png not found.", img);

                img = symGen.createIcon("EHIP" + s + "-----", params);
                assertNotNull("Icon " + "3.ehip" + s.toLowerCase() + "-----.png not found.", img);
            }
        }

        /*
                @org.junit.Test
                public void testNaturalEventFunctionIDRetrieval()
                {
                    MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
                    AVList params = new AVListImpl();
                    BufferedImage img;

                    for (String s : EmergencyManagementNaturalEventsFunctionIDs)
                    {
                        img = symGen.createIcon("EUNP" + s + "-----", params);
                        assertNotNull("Icon " + "0.eunp" + s.toLowerCase() + "-----.png not found.", img);

                        img = symGen.createIcon("EFNP" + s + "-----", params);
                        assertNotNull("Icon " + "1.efnp" + s.toLowerCase() + "-----.png not found.", img);

                        img = symGen.createIcon("ENNP" + s + "-----", params);
                        assertNotNull("Icon " + "2.ennp" + s.toLowerCase() + "-----.png not found.", img);

                        img = symGen.createIcon("EHNP" + s + "-----", params);
                        assertNotNull("Icon " + "3.ehnp" + s.toLowerCase() + "-----.png not found.", img);
                    }
                }
        */
        @org.junit.Test
        public void testOperationFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : EmergencyManagementOperationsFunctionIDs)
            {
                img = symGen.createIcon("EUOP" + s, params);
                assertNotNull("Icon " + "0.euop" + s.toLowerCase() + ".png not found.", img);

                img = symGen.createIcon("EFOP" + s, params);
                assertNotNull("Icon " + "1.efop" + s.toLowerCase() + ".png not found.", img);

                img = symGen.createIcon("ENOP" + s, params);
                assertNotNull("Icon " + "2.enop" + s.toLowerCase() + ".png not found.", img);

                img = symGen.createIcon("EHOP" + s, params);
                assertNotNull("Icon " + "3.ehop" + s.toLowerCase() + ".png not found.", img);
            }
        }

        @org.junit.Test
        public void testInfrastructureFunctionIDRetrieval()
        {
            MilStd2525IconRetriever symGen = new MilStd2525IconRetriever(URL);
            AVList params = new AVListImpl();
            BufferedImage img;

            for (String s : EmergencyManagementInfrastructureFunctionIDs)
            {
                img = symGen.createIcon("EUFP" + s, params);
                assertNotNull("Icon " + "0.eufp" + s.toLowerCase() + ".png not found.", img);

                img = symGen.createIcon("EFFP" + s, params);
                assertNotNull("Icon " + "1.effp" + s.toLowerCase() + ".png not found.", img);

                img = symGen.createIcon("ENFP" + s, params);
                assertNotNull("Icon " + "2.enfp" + s.toLowerCase() + ".png not found.", img);

                img = symGen.createIcon("EHFP" + s, params);
                assertNotNull("Icon " + "3.ehfp" + s.toLowerCase() + ".png not found.", img);
            }
        }
    }

    public static void main(String[] args)
    {
        TestSuite testSuite = new TestSuite();
        testSuite.addTestSuite(RetrievalTests.class);
        testSuite.addTestSuite(ParsingTests.class);
        testSuite.addTestSuite(CodeTests.class);
        //testSuite.addTestSuite(WarfightingFunctionIDTests.class);
        //testSuite.addTestSuite(SignalsIntelligenceFunctionIDTests.class);
        //testSuite.addTestSuite(StabilityOperationsFunctionIDTests.class);
        testSuite.addTestSuite(EmergencyManagementFunctionIDTests.class);
        new TestRunner().doRun(testSuite);
    }

    private final static String[] IconOverlays = {"arch_overlay.png",
        "circle_overlay.png",
        "clover_overlay.png",
        "clovertop_overlay.png",
        "diamond_overlay.png",
        "rectangle_overlay.png",
        "tent_overlay.png",
        "installation_clover_overlay.png",
        "installation_diamond_overlay.png",
        "installation_rectangle_overlay.png"};

    //////////////////////
    // Warfighting

    private final static String[] WarfightingUnknownFunctionIDs = {"------"};

    private final static String[] WarfightingSpaceFunctionIDs = {"------",
        "S-----",
        "V-----",
        "T-----",
        "L-----"};

    private final static String[] WarfightingAirFunctionIDs = {"------",
        "C-----",
        "M-----",
        "MF----",
        "MFB---",
        "MFF---", "MFFI--",
        "MFT---",
        "MFA---",
        "MFL---",
        "MFK---", "MFKB--", "MFKD--",
        "MFC---", "MFCL--", "MFCM--", "MFCH--",
        "MFJ---",
        "MFO---",
        "MFR---", "MFRW--", "MFRZ--", "MFRX--",
        "MFP---", "MFPN--", "MFPM--",
        "MFU---", "MFUL--", "MFUM--", "MFUH--",
        "MFY---",
        "MFH---",
        "MFD---",
        "MFQ---",
        "MFQA--",
        "MFQB--",
        "MFQC--",
        "MFQD--",
        "MFQF--",
        "MFQH--",
        "MFQJ--",
        "MFQK--",
        "MFQL--",
        "MFQM--",
        "MFQI--",
        "MFQN--",
        "MFQP--",
        "MFQR--",
        "MFQRW-", "MFQRZ-", "MFQRX-",
        "MFQS--",
        "MFQT--",
        "MFQU--",
        "MFQY--",
        "MFQO--",
        "MFS---",
        "MFM---",
        "MH----",
        "MHA---",
        "MHS---",
        "MHU---", "MHUL--", "MHUM--", "MHUH--",
        "MHI---",
        "MHH---",
        "MHR---",
        "MHQ---",
        "MHC---",
        "MHCL--",
        "MHCM--",
        "MHCH--",
        "MHT---",
        "MHO---",
        "MHM---",
        "MHD---",
        "MHK---",
        "MHJ---",
        "ML----",
        "MV----",
        "ME----",
        "W-----",
        "WM----",
        "WMS---",
        "WMSS--",
        "WMSA--",
        "WMSU--",
        "WMSB--",
        "WMA---",
        "WMAS--",
        "WMAA--",
        "WMAP--",
        "WMU---",
        "WMCM--",
        "WMB---",
        "WB----",
        "WD----",
        "C-----",
        "CF----",
        "CH----",
        "CL----"};

    private final static String[] WarfightingGroundFunctionIDs = {"------",
        "U-----",
        "UC----",
        "UCD---",
        "UCDS--",
        "UCDSC-",
        "UCDSS-",
        "UCDSV-",
        "UCDM--",
        "UCDML-",
        "UCDMLA",
        "UCDMM-",
        "UCDMH-",
        "UCDH--",

        "UCDHH-",
        "UCDHP-",
        "UCDG--",
        "UCDC--",
        "UCDT--",
        "UCDO--",
        "UCA---",
        "UCAT--",
        "UCATA-",
        "UCATW-",
        "UCATWR",
        "UCATL-",
        "UCATM-",
        "UCATH-",
        "UCATR-",
        "UCAW--",
        "UCAWS-",
        "UCAWA-",
        "UCAWW-",
        "UCAWWR",
        "UCAWL-",
        "UCAWM-",
        "UCAWH-",
        "UCAWR-",
        "UCAA--",

        "UCAAD-",
        "UCAAL-",
        "UCAAM-",
        "UCAAS-",
        "UCAAU-",
        "UCAAC-",
        "UCAAA-",
        "UCAAAT",
        "UCAAAW",
        "UCAAAS",
        "UCAAO-",
        "UCAAOS",
        "UCV---",
        "UCVF--",
        "UCVFU-",
        "UCVFA-",
        "UCVFR-",
        "UCVR--",
        "UCVRA-",
        "UCVRS-",
        "UCVRW-",
        "UCVRU-",
        "UCVRUL",
        "UCVRUM",
        "UCVRUH",

        "UCVRUC",
        "UCVRUE",
        "UCVRM-",
        "UCVS--",
        "UCVC--",
        "UCVV--",
        "UCVU--",
        "UCVUF-",
        "UCVUR-",
        "UCI---",
        "UCIL--",
        "UCIM--",
        "UCIO--",
        "UCIA--",
        "UCIS--",
        "UCIZ--",
        "UCIN--",
        "UCII--",
        "UCIC--",
        "UCE---",
        "UCEC--",
        "UCECS-",
        "UCECA-",
        "UCECC-",

        "UCECL-",
        "UCECM-",
        "UCECH-",
        "UCECT-",
        "UCECW-",
        "UCECO-",
        "UCECR-",
        "UCEN--",
        "UCENN-",
        "UCF---",
        "UCFH--",
        "UCFHE-",
        "UCFHS-",
        "UCFHA-",
        "UCFHC-",
        "UCFHO-",
        "UCFHL-",
        "UCFHM-",
        "UCFHH-",
        "UCFHX-",
        "UCFR--",
        "UCFRS-",
        "UCFRSS",
        "UCFRSR",
        "UCFRST",

        "UCFRM-",
        "UCFRMS",
        "UCFRMR",
        "UCFRMT",
        "UCFT--",
        "UCFTR-",
        "UCFTS-",
        "UCFTF-",
        "UCFTC-",
        "UCFTCD",
        "UCFTCM",
        "UCFTA-",
        "UCFM--",
        "UCFMS-",
        "UCFMW-",
        "UCFMT-",
        "UCFMTA",
        "UCFMTS",
        "UCFMTC",
        "UCFMTO",
        "UCFML-",
        "UCFS--",
        "UCFSS-",
        "UCFSA-",
        "UCFSL-",

        "UCFSO-",
        "UCFO--",
        "UCFOS-",
        "UCFOA-",
        "UCFOL-",
        "UCFOO-",
        "UCR---",
        "UCRH--",
        "UCRV--",
        "UCRVA-",
        "UCRVM-",
        "UCRVG-",
        "UCRVO-",
        "UCRC--",
        "UCRS--",
        "UCRA--",
        "UCRO--",
        "UCRL--",
        "UCRR--",
        "UCRRD-",
        "UCRRF-",
        "UCRRL-",
        "UCRX--",
        "UCM---",

        "UCMT--",
        "UCMS--",
        "UCS---",
        "UCSW--",
        "UCSG--",
        "UCSGD-",
        "UCSGM-",
        "UCSGA-",
        "UCSM--",
        "UCSR--",
        "UCSA--",
        "UU----",
        "UUA---",
        "UUAC--",
        "UUACC-",
        "UUACCK",
        "UUACCM",
        "UUACS-",
        "UUACSM",
        "UUACSA",
        "UUACR-",
        "UUACRW",
        "UUACRS",
        "UUAN--",

        "UUAB--",
        "UUABR-",
        "UUAD--",
        "UUM---",
        "UUMA--",
        "UUMS--",
        "UUMSE-",
        "UUMSEA",
        "UUMSED",
        "UUMSEI",
        "UUMSEJ",
        "UUMSET",
        "UUMSEC",
        "UUMC--",
        "UUMR--",
        "UUMRG-",
        "UUMRS-",
        "UUMRSS",
        "UUMRX-",
        "UUMMO-",
        "UUMO--",
        "UUMT--",
        "UUMQ--",
        "UUMJ--",
        "UUL---",

        "UULS--",
        "UULM--",
        "UULC--",
        "UULF--",
        "UULD--",
        "UUS---",
        "UUSA--",
        "UUSC--",
        "UUSCL-",
        "UUSO--",
        "UUSF--",
        "UUSM--",
        "UUSMS-",
        "UUSML-",
        "UUSMN-",
        "UUSR--",
        "UUSRS-",
        "UUSRT-",
        "UUSRW-",
        "UUSS--",
        "UUSW--",
        "UUSX--",
        "UUI---",
        "UUP---",
        "UUE---",

        "US----",
        "USA---",
        "USAT--",
        "USAC--",
        "USAJ--",
        "USAJT-",
        "USAJC-",
        "USAO--",
        "USAOT-",
        "USAOC-",
        "USAF--",
        "USAFT-",
        "USAFC-",
        "USAS--",
        "USAST-",
        "USASC-",
        "USAM--",
        "USAMT-",
        "USAMC-",
        "USAR--",
        "USART-",
        "USARC-",
        "USAP--",
        "USAPT-",
        "USAPC-",

        "USAPB-",
        "USAPBT",
        "USAPBC",
        "USAPM-",
        "USAPMT",
        "USAPMC",
        "USAX--",
        "USAXT-",
        "USAXC-",
        "USAL--",
        "USALT-",
        "USALC-",
        "USAW--",
        "USAWT-",
        "USAWC-",
        "USAQ--",
        "USAQT-",
        "USAQC-",
        "USM---",
        "USMT--",
        "USMC--",
        "USMM--",
        "USMMT-",
        "USMMC-",
        "USMV--",

        "USMVT-",
        "USMVC-",
        "USMD--",
        "USMDT-",
        "USMDC-",
        "USMP--",
        "USMPT-",
        "USMPC-",
        "USS---",
        "USST--",
        "USSC--",
        "USS1--",
        "USS1T-",
        "USS1C-",
        "USS2--",
        "USS2T-",
        "USS2C-",
        "USS3--",
        "USS3T-",
        "USS3C-",
        "USS3A-",
        "USS3AT",
        "USS3AC",
        "USS4--",
        "USS4T-",

        "USS4C-",
        "USS5--",
        "USS5T-",
        "USS5C-",
        "USS6--",
        "USS6T-",
        "USS6C-",
        "USS7--",
        "USS7T-",
        "USS7C-",
        "USS8--",
        "USS8T-",
        "USS8C-",
        "USS9--",
        "USS9T-",
        "USS9C-",
        "USSX--",
        "USSXT-",
        "USSXC-",
        "USSL--",
        "USSLT-",
        "USSLC-",
        "USSW--",
        "USSWT-",
        "USSWC-",

        "USSWP-",
        "USSWPT",
        "USSWPC",
        "UST---",
        "USTT--",
        "USTC--",
        "USTM--",
        "USTMT-",
        "USTMC-",
        "USTR--",
        "USTRT-",
        "USTRC-",
        "USTS--",
        "USTST-",
        "USTSC-",
        "USTA--",
        "USTAT-",
        "USTAC-",
        "USTI--",
        "USTIT-",
        "USTIC-",
        "USX---",
        "USXT--",
        "USXC--",
        "USXH--",

        "USXHT-",
        "USXHC-",
        "USXR--",
        "USXRT-",
        "USXRC-",
        "USXO--",
        "USXOT-",
        "USXOC-",
        "USXOM-",
        "USXOMT",
        "USXOMC",
        "USXE--",
        "USXET-",
        "USXEC-",
        "UH----",
        "E-----",
        //"EW----",         // icon not used
        "EWM---",
        "EWMA--",
        "EWMAS-",
        "EWMASR",
        "EWMAI-",
        "EWMAIR",
        "EWMAIE",

        "EWMAL-",
        "EWMALR",
        "EWMALE",
        "EWMAT-",
        "EWMATR",
        "EWMATE",
        "EWMS--",
        "EWMSS-",
        "EWMSI-",
        "EWMSL-",
        "EWMT--",
        "EWMTL-",
        "EWMTM-",
        "EWMTH-",
        "EWS---",
        "EWSL--",
        "EWSM--",
        "EWSH--",
        "EWX---",
        "EWXL--",
        "EWXM--",
        "EWXH--",
        "EWT---",
        "EWTL--",
        "EWTM--",

        "EWTH--",
        "EWR---",
        "EWRR--",
        "EWRL--",
        "EWRH--",
        "EWZ---",
        "EWZL--",
        "EWZM--",
        "EWZH--",
        "EWO---",
        "EWOL--",
        "EWOM--",
        "EWOH--",
        "EWH---",
        "EWHL--",
        "EWHLS-",
        "EWHM--",
        "EWHMS-",
        "EWHH--",
        "EWHHS-",
        "EWG---",
        "EWGL--",
        "EWGM--",
        "EWGH--",
        "EWGR--",

        "EWD---",
        "EWDL--",
        "EWDLS-",
        "EWDM--",
        "EWDMS-",
        "EWDH--",
        "EWDHS-",
        "EWA---",
        "EWAL--",
        "EWAM--",
        "EWAH--",
        "EV----",
        "EVA---",
        "EVAT--",
        "EVATL-",
        "EVATLR",
        "EVATM-",
        "EVATMR",
        "EVATH-",
        "EVATHR",
        "EVAA--",
        "EVAAR-",
        "EVAI--",
        "EVAC--",
        "EVAS--",

        "EVAL--",
        "EVU---",
        "EVUB--",
        "EVUS--",
        "EVUSL-",
        "EVUSM-",
        "EVUSH-",
        "EVUL--",
        "EVUX--",
        "EVUR--",
        "EVUT--",
        "EVUTL-",
        "EVUTH-",
        "EVUA--",
        "EVUAA-",
        "EVE---",
        "EVEB--",
        "EVEE--",
        "EVEC--",
        "EVEM--",
        "EVEMV-",
        "EVEML-",
        "EVEA--",
        "EVEAA-",
        "EVEAT-",

        "EVED--",
        "EVEDA-",
        "EVES--",
        "EVER--",
        "EVEH--",
        "EVEF--",
        "EVT---",
        "EVC---",
        "EVCA--",
        "EVCAL-",
        "EVCAM-",
        "EVCAH-",
        "EVCO--",
        "EVCOL-",
        "EVCOM-",
        "EVCOH-",
        "EVCM--",
        "EVCML-",
        "EVCMM-",
        "EVCMH-",
        "EVCU--",
        "EVCUL-",
        "EVCUM-",
        "EVCUH-",
        "EVCJ--",

        "EVCJL-",
        "EVCJM-",
        "EVCJH-",
        "EVCT--",
        "EVCTL-",
        "EVCTM-",
        "EVCTH-",
        "EVCF--",
        "EVCFL-",
        "EVCFM-",
        "EVCFH-",
        "EVM---",
        "EVS---",
        "EVST--",
        "EVSR--",
        "EVSC--",
        "EVSP--",
        "EVSW--",
        "ES----",
        "ESR---",
        "ESE---",
        //"EX----",         // icon not used
        "EXI---",
        "EXL---",
        "EXN---",

        "EXF---",
        "EXM---",
        "EXMC--",
        "EXML--",
        "I-----",
        "IR----",
        "IRM---",
        "IRP---",
        "IRN---",
        "IRNB--",
        "IRNC--",
        "IRNN--",
        "IP----",
        "IPD---",
        "IE----",
        "IU----",
        "IUR---",
        "IUT---",
        "IUE---",
        "IUEN--",
        "IUED--",
        "IUEF--",
        "IUP---",
        //"IM----",         // icon not used
        "IMF---",

        "IMFA--",
        "IMFP--",
        "IMFPW-",
        "IMFS--",
        "IMA---",
        "IME---",
        "IMG---",
        "IMV---",
        "IMN---",
        "IMNB--",
        "IMC---",
        "IMS---",
        "IMM---",
        "IG----",
        "IB----",
        "IBA---",
        "IBN---",
        "IT----",
        "IX----",
        "IXH---"};

    private final static String[] WarfightingSeaSurfaceFunctionIDs = {"------",
        "C-----",
        "CL----",
        "CLCV--",
        "CLBB--",

        "CLCC--",
        "CLDD--",
        "CLFF--",
        "CLLL--",
        "CLLLAS",
        "CLLLMI",
        "CLLLSU",
        "CA----",
        "CALA--",
        "CALS--",
        "CALSM-",
        "CALST-",
        "CALC--",
        "CM----",
        "CMML--",
        "CMMS--",
        "CMMH--",
        "CMMA--",
        "CP----",
        "CPSB--",
        "CPSU--",
        "CPSUM-",
        "CPSUT-",
        "CPSUG-",
        "CH----",

        "G-----",
        "GT----",
        "GG----",
        "GU----",
        "GC----",
        "CD----",
        "CU----",
        "CUM---",
        "CUS---",
        "CUN---",
        "CUR---",
        "N-----",
        "NR----",
        "NF----",
        "NI----",
        "NS----",
        "NM----",
        "NH----",
        //"X-----",     // icon not used
        "XM----",
        "XMC---",
        "XMR---",
        "XMO---",
        "XMTU--",
        "XMF---",

        "XMP---",
        "XMH---",
        "XMTO--",
        "XF----",
        "XFDF--",
        "XFDR--",
        "XFTR--",
        "XR----",
        "XL----",
        "XH----",
        "XA----",
        "XAR---",
        "XAS---",
        "XP----",
        "O-----"};

    private final static String[] WarfightingSubsurfaceFunctionIDs = {"------",
        "S-----",
        "SF----",
        "SB----",
        "SR----",
        "SX----",
        "SN----",
        "SNF---",
        "SNA---",
        "SNM---",

        "SNG---",
        "SNB---",
        "SC----",
        "SCF---",
        "SCA---",
        "SCM---",
        "SCG---",
        "SCB---",
        "SO----",
        "SOF---",
        "SU----",
        "SUM---",
        "SUS---",
        "SUN---",
        "S1----",
        "S2----",
        "S3----",
        "S4----",
        "SL----",
        "SK----",
        "W-----",
        "WT----",
        "WM----",
        "WMD---",
        "WMG---",

        "WMGD--",
        "WMGX--",
        "WMGE--",
        "WMGC--",
        "WMGR--",
        "WMGO--",
        "WMM---",
        "WMMD--",
        "WMMX--",
        "WMME--",
        "WMMC--",
        "WMMR--",
        "WMMO--",
        "WMF---",
        "WMFD--",
        "WMFX--",
        "WMFE--",
        "WMFC--",
        "WMFR--",
        "WMFO--",
        "WMO---",
        "WMOD--",
        "WMX---",
        "WME---",
        "WMA---",

        "WMC---",
        "WMR---",
        "WMB---",
        "WMBD--",
        "WMN---",
        "WMS---",
        "WMSX--",
        "WMSD--",
        "WD----",
        "WDM---",
        "WDMG--",
        "WDMM--",
        //"N-----",         // icon not used
        "ND----",
        "E-----",
        "V-----",
        "X-----"};

    private final static String[] WarfightingSOFFunctionIDs = {"------",
        "A-----",
        "AF----",
        "AFA---",
        "AFK---",
        "AFU---",
        "AFUL--",
        "AFUM--",

        "AFUH--",
        "AV----",
        "AH----",
        "AHH---",
        "AHA---",
        "AHU---",
        "AHUL--",
        "AHUM--",
        "AHUH--",
        "N-----",
        "NS----",
        "NU----",
        "NB----",
        "NN----",
        "G-----",
        "GS----",
        "GR----",
        "GP----",
        "GPA---",
        "GC----",
        "B-----"};

    //////////////////////
    //  Signals Intelligence

    public static String[] SignalsIntelligenceSpaceFunctionIDs = {//"------",
        //"S-----",
        //"SC----",     // icons not used
        "SCD---",
        //"SR----",     // icon not used
        "SRD---",
        "SRE---",
        "SRI---",
        "SRM---",
        "SRT---",
        "SRS---",
        "SRU---"};

    public static String[] SignalsIntelligenceAirFunctionIDs = {//"------",
        //"S-----",
        //"SC----",     // icons not used
        "SCC---",
        "SCO---",
        "SCP---",
        "SCS---",
        //"SR----",     // icon not used
        "SRAI--",

        "SRAS--",
        "SRC---",
        "SRD---",
        "SRE---",
        "SRF---",
        "SRI---",
        "SRMA--",
        "SRMD--",
        "SRMG--",
        "SRMT--",
        "SRMF--",
        "SRTI--",
        "SRTA--",
        "SRTT--",
        "SRU---"};

    public static String[] SignalsIntelligenceGroundFunctionIDs = {//"------",
        //"S-----",
        //"SC----",     // icons not used
        "SCC---",
        "SCO---",
        "SCP---",
        "SCS---",
        "SCT---",
        //"SR----",     // icon not used
        "SRAT--",

        "SRAA--",
        "SRB---",
        "SRCS--",
        "SRCA--",
        "SRD---",
        "SRE---",
        "SRF---",
        "SRH---",
        "SRI---",
        "SRMM--",
        "SRMA--",
        "SRMG--",
        "SRMT--",
        "SRMF--",
        "SRS---",
        "SRTA--",
        "SRTI--",
        "SRTT--",
        "SRU---"};

    public static String[] SignalsIntelligenceSeaSurfaceFunctionIDs = {//"------",
        //"S-----",
        //"SC----",     // icons not used
        "SCC---",
        "SCO---",
        "SCP---",

        "SCS---",
        //"SR----",     // icon not used
        "SRAT--",
        "SRAA--",
        "SRCA--",
        "SRCI--",
        "SRD---",
        "SRE---",
        "SRF---",
        "SRH---",
        "SRI---",
        "SRMM--",
        "SRMA--",
        "SRMG--",
        "SRMT--",
        "SRMF--",
        "SRS---",
        "SRTA--",
        "SRTI--",
        "SRTT--",
        "SRU---"};

    public static String[] SignalsIntelligenceSubsurfaceFunctionIDs = {//"------",
        //"S-----",
        //"SC----",     // icons not used
        "SCO---",

        "SCP---",
        "SCS---",
        //"SR----",     // icon not used
        "SRD---",
        "SRE---",
        "SRM---",
        "SRS---",
        "SRT---",
        "SRU---"};

    ///////////////////////////////
    //  Stability Operations

    public static String[] StabilityOperationsViolentActivitiesFunctionIDs = {//"------",
        "A-----",
        "M-----",
        "MA----",
        "MB----",
        "MC----",
        "B-----",
        "Y-----",
        "D-----",
        "S-----",
        "P-----",
        "E-----",
        "EI----"};

    public static String[] StabilityOperationsLocationsFunctionIDs = {//"------",
        "B-----",
        "G-----",
        "W-----",
        "M-----"};

    public static String[] StabilityOperationsOperationsFunctionIDs = {//"------",
        "P-----",
        //"R-----",     // icon not used
        "RW----",
        "RC----",
        "D-----",
        "M-----",
        "Y-----",
        "YT----",
        "YW----",
        "YH----",
        "F-----",
        "S-----",
        "O-----",
        "E-----",
        //"H-----",     // icon not used
        "HT----",
        "HA----",
        "HV----",
        "K-----",
        "KA----",
        "A-----",
        "U-----",
        "C-----",
        "CA----",
        "CB----",
        "CC----"};

    public static String[] StabilityOperationsItemsFunctionIDs = {//"------",
        "R-----",
        "S-----",
        "G-----",
        "V-----",
        "I-----",
        "D-----",
        "F-----"};

    public static String[] StabilityOperationsIndividualFunctionIDs = {"------",
        "A-----",
        "B-----",
        "C-----"};

    public static String[] StabilityOperationsNonmilitaryFunctionIDs = {"------",
        "A-----",
        "B-----",
        "C-----",
        "D-----",
        "E-----",
        "F-----"};

    public static String[] StabilityOperationsRapeFunctionIDs = {"------",
        "A-----"};

    /////////////////////////////
    //  Emergency Management

    public static String[] EmergencyManagementIncidentsFunctionIDs = {//"------",
        "A-----",
        "AC----",
        "B-----",
        "BA----",
        "BC----",
        "BD----",
        "BF----",
        "C-----",
        "CA----",
        "CB----",
        "CC----",
        "CD----",
        "CE----",
        "CF----",
        "CG----",
        "CH----",
        "D-----",
        "DA----",
        "DB----",
        "DC----",
        "DE----",
        "DF----",
        "DG----",
        "DH----",
        "DI----",
        "DJ----",
        "DK----",
        "DL----",
        "DM----",
        "DN----",
        "DO----",
        "E-----",
        "EA----",
        "F-----",
        "FA----",
        "G-----",
        "GA----",
        "GB----",
        "H-----",
        "HA----"};

    public static String[] EmergencyManagementNaturalEventsFunctionIDs = {//"------",
        //"A-----",     // icon not used
        "AA----",
        "AB----",
        "AC----",
        "AD----",
        "AE----",
        "AG----",
        //"B-----",     // icon not used
        "BB----",
        "BC----",
        "BF----",
        "BM----",
        //"C-----",     // icon not used
        "CA----",
        "CB----",
        "CC----",
        "CD----",
        "CE----"};

    public static String[] EmergencyManagementOperationsFunctionIDs = {//"-----------",
        "A-----H----",
        "AA---------",
        "AB---------",
        "AC----H----",
        "AD----H----",
        "AE---------",
        "AF---------",
        "AG----H----",
        "AJ----H----",
        "AK----H----",
        "AL----H----",
        "AM----H----",
        "B----------",
        "BA---------",
        "BB---------",
        "BC----H----",
        "BD---------",
        "BE----H----",

        "BF----H----",
        "BG----H----",
        "BH----H----",
        "BI----H----",
        "BJ---------",
        "BK----H----",
        "BL----H----",
        "C----------",
        "CA---------",
        "CB---------",
        "CC---------",
        "CD----H----",
        "CE----H----",
        "D----------",      // Friend Standard Identity only
        "DA---------",      //
        "DB---------",      //
        "DC----H----",      //
        "DD---------",
        "DDA--------",
        "DDB--------",
        "DDC---H----",
        "DE---------",
        "DEA--------",
        "DEB--------",
        "DEC---H----",

        "DF---------",
        "DFA--------",
        "DFB--------",
        "DFC---H----",
        "DG---------",        // Friend Standard Identity only
        "DGA--------",        //
        "DGB--------",        //
        "DGC---H----",        //
        "DH---------",        //
        "DHA--------",        //
        "DHB--------",        //
        "DHC---H----",        //
        "DI---------",        //
        "DIA--------",        //
        "DIB--------",        //
        "DIC---H----",        //
        "DJ---------",
        "DJB--------",
        "DJC---H----",
        "DK---------",
        "DL---------",        // Friend Standard Identity only
        "DLA--------",        //
        "DLB--------",        //
        "DLC---H----",        //

        "DM---------",        //
        "DMA--------",        //
        "DMB--------",        //
        "DMC---H----",        //
        "DN---------",
        "DNA--------",
        "DNC---H----",
        "DO---------",        // Friend Standard Identity only
        "DOA--------",        //
        "DOB--------",        //
        "DOC---H----",        //
        "EA---------",
        "EB---------",
        "EC---------",
        "ED---------",
        "EE---------"};

    public static String[] EmergencyManagementInfrastructureFunctionIDs = {//"------",
        "A----------",
        "AA----H----",
        "AB----H----",
        "AC----H----",
        "AD----H----",
        "AE----H----",

        "AF----H----",
        "AG----H----",
        "B-----H----",
        "BA---------",
        "BB----H----",
        "BC----H----",
        "BD----H----",
        "BE----H----",
        "BF----H----",
        "C-----H----",
        "CA----H----",
        "CB----H----",
        "CC----H----",
        "CD----H----",
        "CE----H----",
        "CF----H----",
        "CG----H----",
        "CH----H----",
        "CI----H----",
        "CJ----H----",
        "D-----H----",
        "DA----H----",
        "DB----H----",
        "EA----H----",

        "EB----H----",
        "EE----H----",
        "F-----H----",
        "G-----H----",
        "GA----H----",
        "H-----H----",
        "HA----H----",
        "HB----H----",
        "I-----H----",
        "IA----H----",
        "IB----H----",
        "IC----H----",
        "ID----H----",
        "J-----H----",
        "JA----H----",
        "JB----H----",
        "JC----H----",
        "K-----H----",
        "KB----H----",
        "LA----H----",

        "LD----H----",
        "LE----H----",
        "LF----H----",
        "LH----H----",
        "LJ----H----",
        "LK----H----",
        "LM----H----",
        "LO----H----",
        "LP----H----",
        "MA---------",
        "MB----H----",
        "MC---------",
        "MD----H----",
        "ME----H----",
        "MF----H----",
        "MG----H----",
        "MH----H----",
        "MI----H----"};
}

