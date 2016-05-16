package deltaanalytics.util;

import deltaanalytics.octave.dto.MeasureSampleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
public class BrukerRestClient {
    @Autowired
    private RestOperations restTemplate;
    @Value("${bruker-service.host}")
    private String host;
    @Value("${bruker-service.port}")
    private int port;
    @Value("${bruker-service.measureSamples.url}")
    private String measureSamplesUrl;

    public MeasureSampleDto getMeasureSample(Long id) {
        return restTemplate.getForObject(hostWithPort() + "/" + measureSamplesUrl + "/" + id, MeasureSampleDto.class);
    }

    private String hostWithPort() {
        return host + ":" + port;
    }
}
