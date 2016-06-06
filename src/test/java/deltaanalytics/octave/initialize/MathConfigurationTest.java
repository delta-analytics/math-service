package deltaanalytics.octave.initialize;

import deltaanalytics.octave.Application;
import deltaanalytics.octave.repositories.HitranParametersRepository;
import deltaanalytics.octave.repositories.LevenberqMarquardtParametersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Transactional
public class MathConfigurationTest {

    @Autowired
    private LevenberqMarquardtParametersRepository levenberqMarquardtParametersRepository;

    @Autowired
    private HitranParametersRepository hitranParametersRepository;

    @Test
    public void testSetup() throws Exception {

    }
}