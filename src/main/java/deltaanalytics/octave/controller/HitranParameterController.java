package deltaanalytics.octave.controller;

import deltaanalytics.octave.entity.HitranParameters;
import deltaanalytics.octave.repositories.HitranParametersRepository;
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
@RequestMapping("/hitran")
public class HitranParameterController {
    private static final Logger logger = LoggerFactory.getLogger(HitranParameterController.class);
    @Autowired
    private HitranParametersRepository hitranParametersRepository;

    @RequestMapping("")
    @ResponseBody
    public List<HitranParameters> getActualHitran() {
        logger.info("getActualHitranParameters");
        return hitranParametersRepository.findByCurrentDefaultTrue();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void setHitranNewDefaults(@RequestBody HitranParameters hitranParameters) {
        logger.info("setHitran " + hitranParameters.toString());
        hitranParametersRepository.saveAndMarkNewDefaults(hitranParameters);
    }
}
