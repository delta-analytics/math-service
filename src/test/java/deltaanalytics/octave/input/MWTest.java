package deltaanalytics.octave.input;

import deltaanalytics.octave.input.MW;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MWTest {
    @Test
    public void checkValues() {
        String mwExpected = "MW = [18.015, 44.010, 44.013, 28.010, 16.043, 30.006, 46,0055];";

        assertThat(MW.values(), is(equalTo(mwExpected)));
    }
}
