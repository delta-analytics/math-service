package deltaanalytics.octave.dto;

import deltaanalytics.octave.output.MoleculeResult;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoleculeResultListDto {
    private List<MoleculeResult> allMolecules = new ArrayList<>();

  
    public void addMoleculeResult(MoleculeResult moleculeResult) {
        this.allMolecules.add(moleculeResult);
    }

    public List<MoleculeResult> getMoleculeResultList() {
        return allMolecules;
    }

    public void setMoleculeResultList(List<MoleculeResult> allMolecules) {
        this.allMolecules = allMolecules;
    }    
    
    
    @Override
    public String toString() {
        return "MoleculeResultDto{" +
               "allMoleculres = " + allMolecules.stream().map(Object::toString).collect(Collectors.joining(", ")) +
                '}';
    }    
}
