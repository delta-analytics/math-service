package deltaanalytics.octave.input;

import deltaanalytics.octave.input.HitranInput;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HitranInputTest {
    @Test
    public void checkValues() {
        String[] expectedValues = new String[]{"01.csv", "02.csv", "04.csv", "05.csv", "06.csv", "08.csv", "10.csv"};

        assertThat(HitranInput.values(), is(equalTo(expectedValues)));
    }
}
