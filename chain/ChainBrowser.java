package chain;

import java.util.List;

import data.Transaction;

public interface ChainBrowser {
    List<Transaction> findAllChildrenOf(String id);

    List<Transaction> findAllParentnOf(String id);
}
