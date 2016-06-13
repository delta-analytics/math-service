package deltaanalytics.octave.controller;

import deltaanalytics.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/calculation")
public class CalculationController {
    private static final Logger logger = LoggerFactory.getLogger(CalculationController.class);
    @Autowired
    private Calculator calculator;

    @RequestMapping(value = "/start/{measurementid}", method = RequestMethod.POST)
    public void startCalculationFor(@PathVariable("measurementid") Long measurementId) {
        logger.info("startCalculationFor " + measurementId);
        //ToDo 7 Molekuele, sollte von der GUI eingeschränkt werden
        List<Integer> molecules = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        calculator.doCalculations(molecules, measurementId);
        logger.info("endCalculationFor " + measurementId);
    }
}
