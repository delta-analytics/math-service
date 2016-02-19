package deltaanalytics.octave.input;

import org.junit.Test;
import static org.junit.Assert.*;

public class SpectrumInputTest {
        
    @Test
    public void testGetAndSetDataPointTableFile() {
        SpectrumInput instance = new SpectrumInput();
        instance.setDataPointTableFile("Test1.10.dpt");
        String expResult = "Test1.10.dpt";
        String result = instance.getDataPointTableFile();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDataFileEval() {
        SpectrumInput instance = new SpectrumInput();
        instance.setDataPointTableFile("Test1.10.dpt");
        String expResult = "ftir_data = Test1.10.dpt;";
        String result = instance.getDataFileEval();
        assertEquals(expResult, result);
    }
    
}
