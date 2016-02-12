package deltaanalytics.octave.input;

import deltaanalytics.gui.math.HitranParameters;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HitranInputParametersTest {  
    @Test
    public void getCallHitranEval() {
        HitranInputParameters inputParameter = new HitranInputParameters();
        inputParameter.setCallHitran(new boolean[]{false, false, false, false, false, false, false});
        String expected = "call_hitran = [false, false, false, false, false, false, false];";

        assertThat(inputParameter.getCallHitranEval(), is(equalTo(expected)));
    }
    @Test
    public void getMoleculeEval(){
        HitranInputParameters inputParameter = new HitranInputParameters();
        inputParameter.setMolecule(1);
        String expected = "hitran_input = hitran_input{1};";
        
        assertThat(inputParameter.getMoleculeEval(), is(equalTo(expected)));
    }
    @Test
    public void getBaselineStepEval(){
        HitranInputParameters inputParameter = new HitranInputParameters();
        inputParameter.setBaselineStep(50);
        String expected = "baseline_step = 50;";
        
        assertThat(inputParameter.getBaselineStepEval(), is(equalTo(expected)));
    }
    @Test
    public void getMWEval(){
        HitranInputParameters inputParameter = new HitranInputParameters();
        inputParameter.setMolecule(4);
        String expected = "MW = MW(4);";
        
        assertThat(inputParameter.getMolWeightEval(), is(equalTo(expected)));
    }
    @Test
    public void getAnfagEndeEval(){
        HitranInputParameters inputParameter = new HitranInputParameters();
        inputParameter.setMolecule(4);
        String expected1 = "anfang = low(4);";
        String expected2 = "ende = high(4);";
        
        assertThat(inputParameter.getAnfangEval(), is(equalTo(expected1)));
        assertThat(inputParameter.getEndeEval(), is(equalTo(expected2)));
    }

    @Test 
    public void getAllOthers(){
        HitranInputParameters inputParameter = new HitranInputParameters(new HitranParameters());

        assertThat(inputParameter.getStpEval(), is(equalTo("stp = 0.02;")));
        assertThat(inputParameter.getSfEval(), is(equalTo("sf = 1.0;")));
        assertThat(inputParameter.getIntensThres1Eval(), is(equalTo("intensThres1 = 1.0E-25;")));
        assertThat(inputParameter.getIntensThres2Eval(), is(equalTo("intensThres2 = 1.0E-25;")));
        assertThat(inputParameter.getIsotopo1Eval(), is(equalTo("isotopo1 = 1;")));
        assertThat(inputParameter.getIsotopo2Eval(), is(equalTo("isotopo2 = 2;")));
        assertThat(inputParameter.getTempEval(), is(equalTo("Temp = 313.0;")));
        assertThat(inputParameter.getPatmEval(), is(equalTo("Patm = 1.0;")));
        assertThat(inputParameter.getDdEval(), is(equalTo("Dd = 5;")));
    }
}
