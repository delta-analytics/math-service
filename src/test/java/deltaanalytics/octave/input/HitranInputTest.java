package deltaanalytics.octave.input;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HitranInputTest {
    @Test
    public void checkCsvFiles() {
        String expectedValues = "hitran_input = {'01.csv', '02.csv', '04.csv', '05.csv', '06.csv', '08.csv', '10.csv'};";

        assertThat(HitranInput.csvFiles(), is(equalTo(expectedValues)));
    }
    @Test
    public void checkMolWeights() {
        String molWeightsExpected = "MW = [18.015, 44.010, 44.013, 28.010, 16.043, 30.006, 46,0055];";

        assertThat(HitranInput.molWeights(), is(equalTo(molWeightsExpected)));
    }    
}
