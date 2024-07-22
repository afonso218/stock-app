package app.stock.tasks;

import app.stock.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(MyScheduledTasks.class);
    private final StockService stockService;

    public MyScheduledTasks(StockService stockService) {
        this.stockService = stockService;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void update() {
        logger.info("[Task started] {}", java.time.LocalDateTime.now());
        stockService.updateData();
        logger.info("[Task ended  ] {}", java.time.LocalDateTime.now());
    }
}
