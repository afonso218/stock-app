package app.stock;

import app.stock.entity.Stock;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<Stock> getAll() {
        return stockService.getAll();
    }

    @GetMapping("/search")
    public List<Stock> search(@RequestParam String name) {
        return stockService.search(name);
    }

    @GetMapping("{symbol}")
    public Stock get(@PathVariable String symbol) {
        return stockService.getStockInfo(symbol);
    }

}