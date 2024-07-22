package app.stock.api.alpha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AlphaVantageService {

    private static final Logger logger = LoggerFactory.getLogger(AlphaVantageService.class);

    @Value("${alpha.vantage.api.url}")
    private String apiUrl;
    @Value("${alpha.vantage.api.key}")
    private String apiKey;


    private final RestTemplate restTemplate;

    public AlphaVantageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getInfo(String symbol) {
        String url = apiUrl + "function=" + AlphaVantageFunction.OVERVIEW + "&symbol=" + symbol + "&apikey=" + apiKey;
        logger.info("Fetching data from: {}", url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }


    public String getTimeSeriesMonthly(String symbol) {
        String url = apiUrl + "function=" + AlphaVantageFunction.TIME_SERIES_MONTHLY + "&symbol=" + symbol + "&apikey=" + apiKey;
        logger.info("Fetching data from: {}", url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String search(String keywords) {
        String url = apiUrl + "function=" + AlphaVantageFunction.SYMBOL_SEARCH + "&keywords=" + keywords + "&apikey=" + apiKey;
        logger.info("Fetching data from: {}", url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

}