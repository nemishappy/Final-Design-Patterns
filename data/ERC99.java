package data;

abstract class ERC99 {
    public final String name, symbol;
    public final int decimals;

    public ERC99(String name, String symbol, int decimals) {
        this.name = name;
        this.symbol = symbol;
        this.decimals = decimals;
    }
}
