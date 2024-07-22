package app.stock.api.yahoo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YahooFinanceData {

    String ticker;
    Double price;
    Double dayChange;
    Double changePercentage;
    String marketCap;

    @Override
    public String toString() {
        return "YahooFinanceData{" +
          "ticker='" + ticker + '\'' +
          ", price=" + price +
          ", change=" + dayChange +
          ", changePercentage=" + changePercentage +
          ", marketCap=" + marketCap +
          '}';
    }
}
