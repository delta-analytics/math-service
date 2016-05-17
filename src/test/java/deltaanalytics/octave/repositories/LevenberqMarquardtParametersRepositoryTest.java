package deltaanalytics.octave.repositories;

import deltaanalytics.octave.Application;
import deltaanalytics.octave.entity.LevenbergMarquardtParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Transactional
public class LevenberqMarquardtParametersRepositoryTest {

    @Autowired
    private LevenberqMarquardtParametersRepository levenberqMarquardtParametersRepository;

    @Test
    public void save(){
        LevenbergMarquardtParameters levenbergMarquardtParameters = new LevenbergMarquardtParameters();
        double[] dp = {2.1, 0.1};
        levenbergMarquardtParameters.setDp(dp);
        levenberqMarquardtParametersRepository.save(levenbergMarquardtParameters);

        assertThat(levenberqMarquardtParametersRepository.count(), is(1l));
        assertThat(levenberqMarquardtParametersRepository.findOne(levenbergMarquardtParameters.getId()).getDp(), is(dp));
    }
}