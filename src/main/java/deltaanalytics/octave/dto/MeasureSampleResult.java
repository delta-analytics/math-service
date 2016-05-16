package deltaanalytics.octave.dto;


public class MeasureSampleResult {

    private long id;
    private double firstValue;
    private double secondValue;
    private MeasureSampleDto measureSample;

    public MeasureSampleResult() {
    }

    public MeasureSampleResult(String row) {
        String[] commaSplit = row.split(",");
        firstValue = Double.parseDouble(commaSplit[0].trim());
        secondValue = Double.parseDouble(commaSplit[0].trim());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(double firstValue) {
        this.firstValue = firstValue;
    }

    public double getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(double secondValue) {
        this.secondValue = secondValue;
    }

    public MeasureSampleDto getMeasureSample() {
        return measureSample;
    }

    public void setMeasureSample(MeasureSampleDto measureSample) {
        this.measureSample = measureSample;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasureSampleResult that = (MeasureSampleResult) o;

        if (id != that.id) return false;
        if (Double.compare(that.firstValue, firstValue) != 0) return false;
        return Double.compare(that.secondValue, secondValue) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(firstValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(secondValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "MeasurementResultRow{" +
                "id=" + id +
                ", firstValue=" + firstValue +
                ", secondValue=" + secondValue +
                '}';
    }
}
