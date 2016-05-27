package deltaanalytics.octave.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/calculation")
public class CalculationController {
    private static final Logger logger = LoggerFactory.getLogger(CalculationController.class);
    
    @RequestMapping(value = "/start/{measurementid}", method = RequestMethod.POST)
    public void startCalculationFor(@PathVariable("measurementid") Long measurementId) {
        logger.info("startCalculationFor " + measurementId);
        //ToDo Aufruf des Calculators. Input ist id. Der Calculator ruft dann lesend/schreibend BrukerRestClient auf. Es fehlt in Bruker noch die
        // MeasureSampleMoleculeResult Klasse. Diese kann n-mal an einem MeasureSample hängen. (ppm)
        logger.info("endCalculationFor "+ measurementId);
    }
}
