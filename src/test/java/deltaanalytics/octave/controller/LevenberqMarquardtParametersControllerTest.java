package deltaanalytics.octave.controller;

import deltaanalytics.octave.entity.LevenbergMarquardtParameters;
import deltaanalytics.octave.repositories.LevenberqMarquardtParametersRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LevenberqMarquardtParametersControllerTest {
    private LevenberqMarquardtParametersController levenberqMarquardtParametersController;
    private LevenberqMarquardtParametersRepository levenberqMarquardtParametersRepository;

    @Before
    public void setup() {
        levenberqMarquardtParametersController = new LevenberqMarquardtParametersController();
        levenberqMarquardtParametersRepository = mock(LevenberqMarquardtParametersRepository.class);
        ReflectionTestUtils.setField(levenberqMarquardtParametersController, "levenberqMarquardtParametersRepository", levenberqMarquardtParametersRepository);
    }

    @Test
    public void getActualLevenberqMarquardtParameters() throws Exception {
        levenberqMarquardtParametersController.getActualLevenberg();

        verify(levenberqMarquardtParametersRepository).findByCurrentDefaultTrue();
    }

    @Test
    public void setLevenberqMarquardtParametersNewDefaults() throws Exception {
        LevenbergMarquardtParameters levenberqMarquardtParameters = new LevenbergMarquardtParameters();

        levenberqMarquardtParametersController.setLevenberqMarquardtParametersNewDefaults(levenberqMarquardtParameters);

        verify(levenberqMarquardtParametersRepository).saveAndMarkNewDefaults(levenberqMarquardtParameters);
    }
}