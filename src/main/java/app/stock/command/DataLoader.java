package app.stock.command;

import app.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final StockService stockService;

    @Autowired
    public DataLoader(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public void run(String... args) {
        stockService.populate();
    }
}
