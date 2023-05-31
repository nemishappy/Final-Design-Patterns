package utils;

import chain.ChainManager;
import data.CSToken;

public class Singleton {
    private static Singleton INSTANCE;

    public final CSToken cstoken;
    // public final Chain completChain;
    public final ChainManager chainManager;

    public Singleton() {
        this.cstoken = new CSToken("CS-KMITL Coin", "CSK", 6);
        this.chainManager = new ChainManager();
    }

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }
}
