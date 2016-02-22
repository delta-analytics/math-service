package deltaanalytics.octave.initialize;

import dk.ange.octave.OctaveEngine;
import dk.ange.octave.OctaveEngineFactory;

import java.io.File;

public class OctaveEngineWrapper {
    private OctaveEngine octaveEngine;

    public OctaveEngine build(OctaveEngineFactory octaveEngineFactory, String octaveCliPath) {
        if (octaveEngine == null) {
            OctaveEngineFactory factory = new OctaveEngineFactory();
            factory.setOctaveProgram(new File(octaveCliPath));  // "C:\\Octave\\Octave-4.0.0\\bin\\octave-cli"
            octaveEngine = factory.getScriptEngine();
            octaveEngine.eval("pkg load optim");  // load optimization package for levenberg marquart leasqr.m, etc.
        }
        return octaveEngine;
    }

    public void close() {
        if (octaveEngine != null) {
            octaveEngine.close();
        }
    }
}
