package deltaanalytics.util;

import deltaanalytics.octave.dto.JuekeMathParametersDto;
import deltaanalytics.octave.dto.MeasureSampleDto;
import deltaanalytics.octave.dto.MoleculeResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.time.LocalDateTime;

@Component
public class JuekeRestClient {
    private static final Logger logger = LoggerFactory.getLogger(JuekeRestClient.class);

    @Autowired
    private RestOperations restTemplate;
    @Value("${jueke-service.host}")
    private String host;
    @Value("${jueke-service.port}")
    private int port;
    @Value("${jueke-service.statusMiddled.url}")
    private String statusMiddledUrl;

    public JuekeMathParametersDto getJuekeMathParametersDto(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        logger.info("getJuekeMathParametersDto " + startDateTime + " <-> " + endDateTime);
        String request = hostWithPort() + "/" + statusMiddledUrl;
        logger.info(request);
        JuekeMathParametersDto juekeMathParametersDto = new JuekeMathParametersDto();
        juekeMathParametersDto.setStartDateTime(startDateTime);
        juekeMathParametersDto.setEndDateTime(endDateTime);
        HttpEntity<JuekeMathParametersDto> requestEntity = new HttpEntity<>(juekeMathParametersDto);
        ResponseEntity<JuekeMathParametersDto> responseEntity = restTemplate.exchange(request, HttpMethod.GET, requestEntity, JuekeMathParametersDto.class);
        return responseEntity.getBody();
    }

    private String hostWithPort() {
        return host + ":" + port;
    }
}
