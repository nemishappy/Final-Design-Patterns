package chain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.STATUS;
import data.Transaction;
import observer.Observer;
import observer.PropertyChangedEvent;

// instance for manage every chain
public class ChainManager implements Observer<Transaction> {

    private final Map<STATUS, Chain> chains;

    public ChainManager() {
        this.chains = new HashMap<>();
        for (STATUS status : STATUS.values()) {
            this.chains.put(status, new Chain(status));
        }
    }

    public void addTransaction(Transaction trx) {
        trx.subscribe(this); // observe every transcation when they change
        chains.get(STATUS.Awaiting).add(trx);
    }

    public Chain getChain(STATUS status) {
        return chains.get(status);
    }

    public Transaction findTransaction(String id) {
        // find in every chains
        for (Map.Entry<STATUS, Chain> entry : chains.entrySet()) {
            Chain value = entry.getValue();
            Transaction tmp = value.findById(id);
            if (tmp != null) {
                return tmp;
            }
        }
        return null;
    }

    public Chain findAllParentnOf(String id) {
        // find in every chains
        for (Map.Entry<STATUS, Chain> entry : chains.entrySet()) {
            Chain value = entry.getValue();
            List<Transaction> tmp = value.findAllParentnOf(id);
            if (tmp != null) {
                return new Chain(tmp);
            }
        }
        return null;
    }

    public Chain findAllChildrenOf(String id) {
        // find in every chains
        for (Map.Entry<STATUS, Chain> entry : chains.entrySet()) {
            Chain value = entry.getValue();
            List<Transaction> tmp = value.findAllChildrenOf(id);
            if (tmp != null) {
                return new Chain(tmp);
            }
        }
        return null;
    }

    private void changeChain(Transaction trx, STATUS newValue, STATUS oldValue) {
        chains.get(oldValue).remove(trx);
        chains.get(newValue).add(trx);
    }

    private void handleChangeStatus(PropertyChangedEvent<Transaction> args){
        // handle when transaction change status
        System.out.println(args.source.getId() + ": " + args.oldValue + " change to " + args.newValue);
        // find all children of changed transaction
        List<Transaction> children = chains.get(args.oldValue).findAllChildrenOf(args.source.getId());
        // change transaction to new chain
        changeChain(args.source, (STATUS) args.newValue, (STATUS) args.oldValue);
        STATUS childStatus = STATUS.Incomplete;
        
        // if it awaiting to complete all trx in awaiting must be Incomplete
        if (args.oldValue == STATUS.Awaiting && args.newValue == STATUS.Complete) {
            children = new ArrayList<>(chains.get(STATUS.Awaiting).getTransactions());
        }

        // change all children status
        if (children != null) {
            for (Transaction trx : children) {
                trx.setStatus(childStatus);
            }
        }
    }

    @Override
    public void handle(PropertyChangedEvent<Transaction> args) {
        // handle when transaction change
        if(args.propertyName.equals("status")){
            handleChangeStatus(args);
        }
    }

}
