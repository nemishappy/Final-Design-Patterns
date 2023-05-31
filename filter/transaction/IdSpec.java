package filter.transaction;

import data.Transaction;
import filter.Specification;

public class IdSpec implements Specification<Transaction> {
    private String id;

    public IdSpec(String id) {
        this.id = id;
    }

    @Override
    public boolean isSatisfied(Transaction item) {
        return item.getId().equals(id);
    }

}
