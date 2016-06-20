package deltaanalytics.octave.input;

import deltaanalytics.octave.entity.HitranParameters;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class HitranInputParametersTest {
       
    @Test
    public void getMoleculeEval(){
        HitranInputParameters inputParameter = new HitranInputParameters();
        inputParameter.setMolecule(1);
        String expected = "hitran_input = hitran_input{1};";
        
        assertThat(inputParameter.getMoleculeEval(), is(equalTo(expected)));
    }
    @Test
    public void getMoAndMoStrEval() {
        HitranInputParameters inputParameter = new HitranInputParameters();
        inputParameter.setMolecule(1);
        String expected_Mo = "mo = 1;";
        String expected_MoStr = "mo_str = 'H2O';";
        
        assertThat(inputParameter.getMoEval(), is(equalTo(expected_Mo)));
        assertThat(inputParameter.getMoStrEval(), is(equalTo(expected_MoStr)));        
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
        input.setMolecule(1);
        input.setCallHitran(false);
        input.setLowWN(3860);
        input.setHighWN(3965);
        input.setBaselineStep(0);
        input.setStp(0.02);
        input.setIntensThres1(1e-25);
        input.setIsotopo1(1);
        input.setIntensThres2(1e-25);
        input.setIsotopo2(2);
        input.setSf(1);
        input.setDd(5);
        HitranInputParameters inputParameter = new HitranInputParameters(input);

        assertThat(inputParameter.getStpEval(), is(equalTo("stp = 0.02;")));
        assertThat(inputParameter.getSfEval(), is(equalTo("sf = 1.0;")));
        assertThat(inputParameter.getIntensThres1Eval(), is(equalTo("intensThres1 = 1.0E-25;")));
        assertThat(inputParameter.getIntensThres2Eval(), is(equalTo("intensThres2 = 1.0E-25;")));
        assertThat(inputParameter.getIsotopo1Eval(), is(equalTo("isotopo1 = 1;")));
        assertThat(inputParameter.getIsotopo2Eval(), is(equalTo("isotopo2 = 2;")));
        assertThat(inputParameter.getDdEval(), is(equalTo("Dd = 5;")));
    }    
}
