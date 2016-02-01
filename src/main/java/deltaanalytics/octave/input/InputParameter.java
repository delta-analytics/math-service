package deltaanalytics.octave.input;

public class InputParameter {
    private boolean[] callHitran;
    private double[] low;
    private double[] high;

    public boolean[] getCallHitran() {
        return callHitran;
    }

    public void setCallHitran(boolean[] callHitran) {
        this.callHitran = callHitran;
    }

    public String getCallHitranEval() {
        String result = "call_hitran = [";
        for (int i = 0; i < callHitran.length; i++) {
            result += callHitran[i];
            if (i < callHitran.length - 1) {
                result += ", ";
            }
        }
        result += "];";
        return result;
    }

    public double[] getLow() {
        return low;
    }

    public void setLow(double[] low) {
        this.low = low;
    }

    public double[] getHigh() {
        return high;
    }

    public void setHigh(double[] high) {
        this.high = high;
    }
}
