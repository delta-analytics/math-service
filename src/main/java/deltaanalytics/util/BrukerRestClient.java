package deltaanalytics.util;

import deltaanalytics.octave.dto.MeasureSampleDto;
import deltaanalytics.octave.dto.MoleculeResultsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Component
public class BrukerRestClient {
    private static final Logger logger = LoggerFactory.getLogger(BrukerRestClient.class);

    private RestTemplate restTemplate = new RestTemplate();
    @Value("${bruker-service.host}")
    private String host;
    @Value("${bruker-service.port}")
    private int port;
    @Value("${bruker-service.measureSamples.url}")
    private String measureSamplesUrl;
    @Value("${bruker-service.moleculeResult.url}")
    private String moleculeResultUrl;

    public MeasureSampleDto getMeasureSample(Long id) {
        logger.info("getMeasureSample " + id);
        String request = hostWithPort() + "/" + measureSamplesUrl + "/" + id;
        logger.info(request);
        return restTemplate.getForObject(request, MeasureSampleDto.class);
    }

    public void save(MoleculeResultsDto moleculesResultDto, Long brukerMeasureSampleId) {
        logger.info("save " + moleculesResultDto + " for " + brukerMeasureSampleId);
        String request = hostWithPort() + "/" + moleculeResultUrl + "/" + brukerMeasureSampleId;
        logger.info(request);
        restTemplate.postForLocation(request, moleculesResultDto);
    }

    private String hostWithPort() {
        return host + ":" + port;
    }
}
