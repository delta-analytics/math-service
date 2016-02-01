package deltaanalytics.octave.input;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InputParameterTest {
    @Test
    public void getCallHitran() {
        InputParameter inputParameter = new InputParameter();
        inputParameter.setCallHitran(new boolean[]{false, false, false, false, false, false, false});
        String expected = "call_hitran = [false, false, false, false, false, false, false];";

        assertThat(inputParameter.getCallHitranEval(), is(equalTo(expected)));
    }
}
