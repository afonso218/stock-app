package app.stock;

import app.stock.api.yahoo.YahooFinanceData;
import app.stock.api.yahoo.YahooFinanceScraper;
import app.stock.entity.Stock;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void populate() {

        final String filename = "database/stocks.csv";
        logger.info("[START] Loading stocks from file: {}", filename);

        List<Stock> stocks = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource(filename).getInputStream()))) {
            reader.skip(1);

            String[] line;
            while ((line = reader.readNext()) != null) {
                Stock data = new Stock();
                data.setSymbol(line[0]);
                data.setName(line[1]);
                data.setSector(line[2]);
                stocks.add(data);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Fail to load", e);
        }

        stockRepository.deleteAll();
        stockRepository.saveAllAndFlush(stocks);

        logger.info("[FINISH] Loading stocks from file. Total: {}", stocks.size());
    }

    public List<Stock> getAll() {
        return stockRepository.findByLastUpdateIsNotNull();
    }

    public List<Stock> search(String name) {
        return stockRepository.findByNameContainingIgnoreCase(name);
    }

    public Stock getStockInfo(String symbol) {
        return stockRepository.findBySymbolIgnoreCase(symbol);
    }

    public void updateData() {

        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

        Pageable pageable = PageRequest.of(0, 5);
        List<Stock> stocksForUpdate = stockRepository.findStocksWithOldOrNullLastUpdate(oneWeekAgo, pageable);

        for (Stock stock : stocksForUpdate) {
            try {
                YahooFinanceData data = YahooFinanceScraper.getYahooFinanceData(stock.getSymbol());
                stock.setCurrentValue(data.getPrice());
                stock.setDayChange(data.getDayChange());
                stock.setDayChangePercentage(data.getChangePercentage());
                stock.setMarketCap(data.getMarketCap());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                stock.setLastUpdate(LocalDateTime.now());
                stockRepository.save(stock);
            }
        }

    }

}
