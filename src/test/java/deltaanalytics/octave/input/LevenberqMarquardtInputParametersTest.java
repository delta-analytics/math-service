package deltaanalytics.octave.input;

import deltaanalytics.octave.entity.LevenbergMarquardtParameters;
import org.junit.Test;
import static org.junit.Assert.*;

public class LevenberqMarquardtInputParametersTest {
        
    /**
     * Test of getStolEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetStolEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());
        
        String expResult = "stol = 1.0E-4;";
        String result = instance.getStolEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNiterEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetNiterEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());
        
        String expResult = "niter = 15;";
        String result = instance.getNiterEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDpEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetDpEvalNlCorrFalse() {
        LevenbergMarquardtParameters lmp = new LevenbergMarquardtParameters();
     
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(lmp);

        String expResult = "dp = [0.01, 0.01, 0.01, 0.01, 0.01, 0.01];";
        String result = instance.getDpEval();
        assertEquals(expResult, result);
        assertEquals(instance.getDp().length, 6);
    }
    /**
     * Test of getDpEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetDpEvalNlCorrTrue() {
        LevenbergMarquardtParameters lmp = new LevenbergMarquardtParameters();
        lmp.setNlCorr(true);
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(lmp);

        String expResult = "dp = [0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01];";
        String result = instance.getDpEval();
        assertEquals(expResult, result);        
    }    

    /**
     * Test of getPinEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetPinEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());
        
        String expResult = "pin = [1.0E-4, 1.0, 0.0225, 2.0E-6, 0.0, 0.0];";
        String result = instance.getPinEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMinValuesEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetMinValuesEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());
        
        String expResult = "minvalues = [-0.1, 0.5, 0.005, 0.0, 0.0, -0.1];";
        String result = instance.getMinValuesEval();
        assertEquals(expResult, result);
        assertEquals(instance.getMaxValues().length, 6);
    }

    /**
     * Test of getMaxValuesEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetMaxValuesEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());

        String expResult = "maxvalues = [0.1, 2.0, 0.04, 0.001, 1.0, 0.1];";
        String result = instance.getMaxValuesEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBaselineEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetBaselineEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());
        
        String expResult = "if(baseline_step == 0), dp(5) = 0; end";
        String result = instance.getBaselineEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDfdpEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetDfdpEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());
        
        String expResult = "dFdp = 'dfdp';";
        String result = instance.getDfdpEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStatWeightsEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetStatWeightsEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());
        
        String expResult = "wt1 = ones(length(wav(idx1:idx2)),1);";
        String result = instance.getStatWeightsEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMinstepEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetMinstepEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());
        
        String expResult = "minstep = ones(length(pin),1)*0;";
        String result = instance.getMinstepEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxstepEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetMaxstepEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());
        
        String expResult = "maxstep = ones(length(pin),1)*Inf;";
        String result = instance.getMaxstepEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOptFractPrecisionEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetOptFractPrecisionEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());
        
        String expResult = "options.fract_prec = minstep;";
        String result = instance.getOptFractPrecisionEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOptFractChangeEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetOptFractChangeEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());
        
        String expResult = "options.fract_change = maxstep;";
        String result = instance.getOptFractChangeEval();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOptBoundsEval method, of class LevenberqMarquardtInputParameters.
     */
    @Test
    public void testGetOptBoundsEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(new LevenbergMarquardtParameters());
        
        String expResult = "options.bounds = [minvalues', maxvalues'];";
        String result = instance.getOptBoundsEval();
        assertEquals(expResult, result);
    }
    
}
