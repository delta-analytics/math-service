package deltaanalytics.octave.input;

import deltaanalytics.octave.entity.LevenbergMarquardtParameters;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.Arrays;

public class LevenberqMarquardtInputParametersTest {
    
    private LevenbergMarquardtParameters levenbergMarquardtParameters;
    
    @Before
    public void setup(){
        levenbergMarquardtParameters = new LevenbergMarquardtParameters();
        levenbergMarquardtParameters.setDp( Arrays.asList(0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01) );
        levenbergMarquardtParameters.setPin( Arrays.asList(1e-4, 1.0, 22.5 * 1e-3, 1e-8, 1e-5, 0.0, -1e-5, 1e-10) );
        levenbergMarquardtParameters.setMinValues( Arrays.asList(-0.1, 0.5, 5 * 1e-3, 0.0, 0.0, -0.1, -1e-2, 0.0) );
        levenbergMarquardtParameters.setMaxValues( Arrays.asList(0.1, 2.0, 40 * 1e-3, 1e-3, 1.0, 0.1, 0.0, 1e-2) );
        levenbergMarquardtParameters.setMolecule(4);
        levenbergMarquardtParameters.setStol(1e-4);
        levenbergMarquardtParameters.setNiter(15);
        levenbergMarquardtParameters.setNlCorr(false); 
    }
    @Test
    public void testGetStolEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
        
        String expResult = "stol = 1.0E-4;";
        String result = instance.getStolEval();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetNiterEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
        
        String expResult = "niter = 15;";
        String result = instance.getNiterEval();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDpEvalNlCorrFalse() {
     
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);

        String expResult = "dp = [0.01, 0.01, 0.01, 0.01, 0.01, 0.01];";
        String result = instance.getDpEval();
        assertEquals(expResult, result);
        assertEquals(instance.getDp().size(), 6);
    }

    @Test
    public void testGetDpEvalNlCorrTrue() {
       
        levenbergMarquardtParameters.setNlCorr(true);
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);

        String expResult = "dp = [0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01];";
        String result = instance.getDpEval();
        assertEquals(expResult, result);        
    }    

    @Test
    public void testGetPinEval() {
        levenbergMarquardtParameters.setPin( Arrays.asList(1e-4, 1.0, 22.5 * 1e-3, 2.0E-6, 0.0, 0.0) );
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
        String expResult = "pin = [1.0E-4, 1.0, 0.0225, 2.0E-6, 0.0, 0.0];";
        String result = instance.getPinEval();
        assertEquals(expResult, result);
       
    }
   
    @Test
    public void testGetMinValuesEval() {
       LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
        String expResult = "minvalues = [-0.1, 0.5, 0.005, 0.0, 0.0, -0.1];";
        String result = instance.getMinValuesEval();
        assertEquals(expResult, result);
        assertEquals(instance.getMaxValues().size(), 6);
    }

    @Test
    public void testGetMaxValuesEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
               String expResult = "maxvalues = [0.1, 2.0, 0.04, 0.001, 1.0, 0.1];";
        String result = instance.getMaxValuesEval();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetBaselineEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
        
        String expResult = "if(baseline_step == 0), dp(5) = 0; end";
        String result = instance.getBaselineEval();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDfdpEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
        
        String expResult = "dFdp = 'dfdp';";
        String result = instance.getDfdpEval();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetStatWeightsEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
        
        String expResult = "wt1 = ones(length(wav(idx1:idx2)),1);";
        String result = instance.getStatWeightsEval();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMinstepEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
        
        String expResult = "minstep = ones(length(pin),1)*0;";
        String result = instance.getMinstepEval();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMaxstepEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
        
        String expResult = "maxstep = ones(length(pin),1)*Inf;";
        String result = instance.getMaxstepEval();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOptFractPrecisionEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
        
        String expResult = "options.fract_prec = minstep;";
        String result = instance.getOptFractPrecisionEval();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOptFractChangeEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
        
        String expResult = "options.fract_change = maxstep;";
        String result = instance.getOptFractChangeEval();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOptBoundsEval() {
        LevenberqMarquardtInputParameters instance = 
                new LevenberqMarquardtInputParameters(levenbergMarquardtParameters);
        
        String expResult = "options.bounds = [minvalues', maxvalues'];";
        String result = instance.getOptBoundsEval();
        assertEquals(expResult, result);
    }
    
}
