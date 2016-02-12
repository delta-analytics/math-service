package deltaanalytics.octave.input;

public class HitranInput {

    // define some octave (internal) parameters which are fixed, never modfied
    public static final String csvFiles() {
        return "hitran_input = {'01.csv', '02.csv', '04.csv', '05.csv', '06.csv', '08.csv', '10.csv'};";
    }
    public static final String molWeights() {
        return "MW = [18.015, 44.010, 44.013, 28.010, 16.043, 30.006, 46,0055];";
    }

}
