package app.stock.api.yahoo;

/**
 * Enum representing various data fields for Yahoo Finance.
 */
public enum DataField {

    MARKET_PRICE("regularMarketPrice"),
    MARKET_DAY_CHANGE("regularMarketChange"),
    MARKET_DAY_CHANGE_PERCENTAGE("regularMarketChangePercent"),
    MARKET_CAP("marketCap");

    private final String value;

    DataField(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "fin-streamer[data-field='" + value + "']";
    }
}
