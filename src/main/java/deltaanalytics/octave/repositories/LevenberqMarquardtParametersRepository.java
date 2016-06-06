package deltaanalytics.octave.repositories;

import deltaanalytics.octave.entity.LevenbergMarquardtParameters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevenberqMarquardtParametersRepository extends JpaRepository<LevenbergMarquardtParameters, Long>, LevenberqMarquardtParametersRepositoryCustom {
    LevenbergMarquardtParameters findByCurrentDefaultTrueAndMolecule(int molecule);
}
