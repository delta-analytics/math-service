package deltaanalytics.octave.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoleculeResultsDto {
    private List<MoleculeResultDto> allMolecules = new ArrayList<>();

  
    public void addMoleculeResult(MoleculeResultDto moleculeResult) {
        this.allMolecules.add(moleculeResult);
    }

    public List<MoleculeResultDto> getMoleculeResultList() {
        return allMolecules;
    }

    public void setMoleculeResultList(List<MoleculeResultDto> allMolecules) {
        this.allMolecules = allMolecules;
    }    
    
    
    @Override
    public String toString() {
        return "MoleculeResultDto{" +
               "allMoleculres = " + allMolecules.stream().map(Object::toString).collect(Collectors.joining(", ")) +
                '}';
    }    
}
