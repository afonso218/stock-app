package app.stock;

import app.stock.entity.Stock;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findBySymbolIgnoreCase(String symbol);

    @Query("SELECT DISTINCT s.sector FROM Stock s")
    List<String> findSectors();

    List<Stock> findByNameContainingIgnoreCase(String name);

    @Query("SELECT s FROM Stock s WHERE s.lastUpdate IS NULL OR s.lastUpdate <= :oneWeekAgo")
    List<Stock> findStocksWithOldOrNullLastUpdate(@Param("oneWeekAgo") LocalDateTime oneWeekAgo, Pageable pageable);

    List<Stock> findByLastUpdateIsNotNull();
}