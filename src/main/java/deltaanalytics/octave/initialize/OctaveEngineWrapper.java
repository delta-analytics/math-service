package deltaanalytics.octave.initialize;

import dk.ange.octave.OctaveEngine;
import dk.ange.octave.OctaveEngineFactory;

import java.io.File;

public class OctaveEngineWrapper {
    private static OctaveEngine octaveEngine;

    public static OctaveEngine build(OctaveEngineFactory octaveEngineFactory) {
        OctaveEngine octave = null;
        if (octaveEngine == null) {
            OctaveEngineFactory factory = new OctaveEngineFactory();
            factory.setOctaveProgram(new File("C:\\Octave\\Octave-4.0.0\\bin\\octave-cli"));
            octave = factory.getScriptEngine();
            octave.eval("pkg load optim");  // load optimization package for levenberg marquart leasqr.m, etc.
        }
        return octave;
    }

    public static void close() {
        if (octaveEngine != null) {
            octaveEngine.close();
        }
    }
}
