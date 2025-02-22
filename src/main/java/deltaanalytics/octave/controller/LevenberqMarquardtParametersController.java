package deltaanalytics.octave.controller;

import deltaanalytics.octave.entity.LevenbergMarquardtParameters;
import deltaanalytics.octave.repositories.LevenberqMarquardtParametersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/levenberg")
public class LevenberqMarquardtParametersController {
    private static final Logger logger = LoggerFactory.getLogger(LevenberqMarquardtParametersController.class);
    @Autowired
    private LevenberqMarquardtParametersRepository levenberqMarquardtParametersRepository;

    @RequestMapping("")
    @ResponseBody
    public List<LevenbergMarquardtParameters> getActualLevenberg() {
        logger.info("getActualLevenbergMarquardtParameters");
        return levenberqMarquardtParametersRepository.findByCurrentDefaultTrue();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void setLevenberqMarquardtParametersNewDefaults(@RequestBody LevenbergMarquardtParameters levenbergMarquardtParameters) {   // FJ this is for one molecule type!
        logger.info("setLevenbergMarquardtParameters " + levenbergMarquardtParameters.toString());
        levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(levenbergMarquardtParameters);
    }
}
