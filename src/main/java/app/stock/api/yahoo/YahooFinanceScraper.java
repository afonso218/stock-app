package app.stock.api.yahoo;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YahooFinanceScraper {

    private static final Logger logger = LoggerFactory.getLogger(YahooFinanceScraper.class);

    public static YahooFinanceData getYahooFinanceData(String ticker) throws IOException {
        logger.info("Fetching data for ticker: {}", ticker);

        String url = "https://finance.yahoo.com/quote/" + ticker;
        Document doc = Jsoup.connect(url).get();

        YahooFinanceData data = new YahooFinanceData();
        data.setTicker(ticker);

        double price = Double.parseDouble(getData(doc, DataField.MARKET_PRICE));
        data.setPrice(price);

        double dayChange = convertChangeToNumber(getData(doc, DataField.MARKET_DAY_CHANGE));
        data.setDayChange(dayChange);

        double dayChangePercentage = convertPercentageToNumber(getData(doc, DataField.MARKET_DAY_CHANGE_PERCENTAGE));
        data.setChangePercentage(dayChangePercentage);

        String marketCap = getData(doc, DataField.MARKET_CAP);
        data.setMarketCap(marketCap);

        logger.info("Fetched data: {}", data);

        return data;
    }

    private static String getData(Document doc, DataField field) {
        Element element = doc.select(field.toString()).first();
        if (element != null) {
            return element.text().replace(",", "");
        } else {
            return null;
        }
    }

    private static double convertChangeToNumber(String change) {
        if (change == null || change.isEmpty()) {
            throw new IllegalArgumentException("Change string cannot be null or empty");
        }

        if (change.charAt(0) == '+') {
            change = change.substring(1);
        }

        return Double.parseDouble(change);
    }

    private static double convertPercentageToNumber(String percentage) {
        if (percentage == null || percentage.isEmpty()) {
            throw new IllegalArgumentException("Percentage string cannot be null or empty");
        }

        percentage = percentage
          .replace("(", "")
          .replace(")", "")
          .replace("%", "")
          .replace("+", "");

        return Double.parseDouble(percentage);
    }
}
