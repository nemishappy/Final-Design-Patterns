package chain;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import data.STATUS;
import data.Transaction;
import filter.transaction.FilterTransaction;
import filter.transaction.IdSpec;

public class Chain implements ChainBrowser {

    List<Transaction> transactions;
    public final STATUS chainStauts;
    private boolean isLocked;
    private FilterTransaction ft = new FilterTransaction(); // filter

    public Chain(STATUS chainStauts) { // for main chain
        this.transactions = new ArrayList<>();
        this.isLocked = false;
        this.chainStauts = chainStauts;
    }

    public Chain(List<Transaction> transactions) { // for temporarily chain
        this.transactions = transactions;
        this.isLocked = false;
        this.chainStauts = null; 
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void add(Transaction trx) {
        // check transaction status
        if (trx.getStatus() != chainStauts) {
            System.out.println("Invalid status.");
            return;
        }
        // lock process
        if (isLocked)
            return;
        isLocked = true;
        int size = transactions.size();
        if (size == 0) {
            trx.setPrevId("0");
        } else {
            trx.setPrevId(transactions.get(size - 1).getId());
            transactions.get(size - 1).setNextId(trx.getId());
        }
        this.transactions.add(trx);
        trx.setNextId("0");
        isLocked = false;
    }

    public void remove(Transaction trx) {
        // remove and reset next trx id
        this.transactions.remove(trx);
        if (transactions.size() > 0)
            transactions.get(transactions.size() - 1).setNextId("0");
    }

    public Transaction findById(String id) {
        // find transaction by ID select the first element, all id is uniqe
        Transaction target = ft.filter(transactions, new IdSpec(id)).findFirst().orElse(null);
        return target;
    }

    @Override
    public List<Transaction> findAllChildrenOf(String id) {
        // findById and retrun sublist children
        Transaction target = findById(id);
        if (target == null)
            return null;
        int index = transactions.indexOf(target);
        List<Transaction> child = new ArrayList<>(transactions.subList(index + 1, transactions.size()));
        return child;
    }

    @Override
    public List<Transaction> findAllParentnOf(String id) {
        // findById and retrun sublist parent
        Transaction target = findById(id);
        if (target == null)
            return null;
        int index = transactions.indexOf(target);
        List<Transaction> child = new ArrayList<>(transactions.subList(0, index));
        return child;
    }

    @Override
    public String toString() {
        // build string look like chain
        StringBuilder sb = new StringBuilder();
        sb.append(chainStauts == null ? "" : chainStauts + " chain" + System.lineSeparator());
        if (transactions.size() > 0) {
            ListIterator<Transaction> iterator = transactions.listIterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next().getId() + System.lineSeparator())
                        .append(iterator.hasNext() ? "\u26D3" + System.lineSeparator() : "");
            }
        } else {
            sb.append("Empty." + System.lineSeparator());
        }
        return sb.toString();
    }

}
