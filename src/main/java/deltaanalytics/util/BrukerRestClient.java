package deltaanalytics.util;

import deltaanalytics.octave.dto.MeasureSampleDto;
import deltaanalytics.octave.dto.MoleculeResultDto;
import deltaanalytics.octave.dto.MoleculeResultsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BrukerRestClient {
    private static final Logger logger = LoggerFactory.getLogger(BrukerRestClient.class);

    private RestTemplate restTemplate = new RestTemplate();
    @Value("${bruker-service.host}")
    private String host;
    @Value("${bruker-service.port}")
    private int port;
    @Value("${bruker-service.getMeasuredSamples.url}")
    private String getMeasuredSamplesUrl;
    @Value("${bruker-service.moleculeResults.url}")
    private String moleculeResultsUrl;

    public MeasureSampleDto getMeasuredSample(Long id) {
        logger.info("getMeasuredSample " + id);
        String request = hostWithPort() + "/" + getMeasuredSamplesUrl + "/" + id;
        logger.info(request);
        return restTemplate.getForObject(request, MeasureSampleDto.class);
    }

    // MoleculeResultsDto is a list of MoleculeResultDto List<MoleculeResultDto>
    public void saveResults(MoleculeResultsDto moleculeResultsDto, Long brukerMeasureSampleId) {
        logger.info("save " + moleculeResultsDto + " for " + brukerMeasureSampleId);
        String request = hostWithPort() + "/" + moleculeResultsUrl + "/" + brukerMeasureSampleId;
        logger.info(request);
        restTemplate.postForLocation(request, moleculeResultsDto);
    }

    private String hostWithPort() {
        return host + ":" + port;
    }
}
