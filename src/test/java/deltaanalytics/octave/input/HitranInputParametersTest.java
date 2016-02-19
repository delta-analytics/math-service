package deltaanalytics.octave.input;

import deltaanalytics.gui.math.HitranParameters;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HitranInputParametersTest {
    @Test
    public void checkMoleculeNumber_4(){
        int molecule = 4;
        HitranParameters input = new HitranParameters();
        input.setCallHitran(new boolean[] {false, false, false, true, false, false, false});

        HitranInputParameters inputParameter = new HitranInputParameters(input, molecule);
        
        assertThat(inputParameter.getMolecule(), is(equalTo(molecule)));
        assertThat(inputParameter.getLowWN(), is(equalTo(2080.0)));
        assertThat(inputParameter.getHighWN(), is(equalTo(2141.0))); 
        assertThat(inputParameter.getBaselineStep(), is(equalTo(0)));
        assertThat(inputParameter.getCallHitran(), is(equalTo(true)));        
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
    public void getAnfangEndeEval(){
        HitranInputParameters inputParameter = new HitranInputParameters();
        inputParameter.setLowWN(2080);
        inputParameter.setHighWN(2141);
        String expected1 = "anfang = 2080.0;";
        String expected2 = "ende = 2141.0;";
        
        assertThat(inputParameter.getAnfangEval(), is(equalTo(expected1)));
        assertThat(inputParameter.getEndeEval(), is(equalTo(expected2)));
    }

    @Test 
    public void getAllOthers(){
        HitranParameters input = new HitranParameters();
        HitranInputParameters inputParameter = new HitranInputParameters(input, 4);

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
