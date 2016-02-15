package deltaanalytics.octave.input;

import deltaanalytics.gui.math.LevenbergMarquartParameters;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LevenberqMarquartInputParametersTest {
    
    public LevenberqMarquartInputParametersTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     * Test of getStolEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetStolEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        
        String expResult = "stol = 1.0E-4;";
        String result = instance.getStolEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNiterEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetNiterEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        
        String expResult = "niter = 15;";
        String result = instance.getNiterEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDpEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetDpEvalNlCorrFalse() {
        LevenbergMarquartParameters lmp = new LevenbergMarquartParameters();
     
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(lmp);

        String expResult = "dp = [0.01, 0.01, 0.01, 0.01, 0.01, 0.01];";
        String result = instance.getDpEval();
        assertEquals(expResult, result);
        assertEquals(instance.getDp().length, 6);
    }
    /**
     * Test of getDpEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetDpEvalNlCorrTrue() {
        LevenbergMarquartParameters lmp = new LevenbergMarquartParameters();
        lmp.setNlCorr(true);
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(lmp);

        String expResult = "dp = [0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01];";
        String result = instance.getDpEval();
        assertEquals(expResult, result);        
    }    

    /**
     * Test of getPinEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetPinEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        
        String expResult = "pin = [1.0E-4, 1.0, 0.0225, 2.0E-6, 0.0, 0.0];";
        String result = instance.getPinEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMinValuesEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetMinValuesEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        
        String expResult = "minvalues = [-0.1, 0.5, 0.005, 0.0, 0.0, -0.1];";
        String result = instance.getMinValuesEval();
        assertEquals(expResult, result);
        assertEquals(instance.getMaxValues().length, 6);
    }

    /**
     * Test of getMaxValuesEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetMaxValuesEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());

        String expResult = "maxvalues = [0.1, 2.0, 0.04, 0.001, 1.0, 0.1];";
        String result = instance.getMaxValuesEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBaselineEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetBaselineEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        
        String expResult = "if(baseline_step == 0), dp(5) = 0; end";
        String result = instance.getBaselineEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDfdpEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetDfdpEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        
        String expResult = "dFdp = 'dfdp';";
        String result = instance.getDfdpEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStatWeightsEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetStatWeightsEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        
        String expResult = "wt1 = ones(length(wav(idx1:idx2)),1);";
        String result = instance.getStatWeightsEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMinstepEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetMinstepEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        
        String expResult = "minstep = ones(length(pin),1)*0;";
        String result = instance.getMinstepEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxstepEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetMaxstepEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        
        String expResult = "maxstep = ones(length(pin),1)*Inf;";
        String result = instance.getMaxstepEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOptFractPrecisionEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetOptFractPrecisionEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        
        String expResult = "options.fract_prec = minstep;";
        String result = instance.getOptFractPrecisionEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOptFractChangeEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetOptFractChangeEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        
        String expResult = "options.fract_change = maxstep;";
        String result = instance.getOptFractChangeEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOptBoundsEval method, of class LevenberqMarquartInputParameters.
     */
    @Test
    public void testGetOptBoundsEval() {
        LevenberqMarquartInputParameters instance = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        
        String expResult = "options.bounds = [minvalues, maxvalues];";
        String result = instance.getOptBoundsEval();
        assertEquals(expResult, result);
    }
    
}
