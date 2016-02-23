package deltaanalytics.octave.input;

public class SpectrumInput {
    private String dataPointTableFile;
    
    public String getDataPointTableFile() {
        return dataPointTableFile;
    }
    public void setDataPointTableFile(String dataPointTableFile) {
        this.dataPointTableFile = dataPointTableFile;
    }
    public String getDataFileEval(){
        return "ftir_data = " + getDataPointTableFile() + ";";
    }  
    
}
