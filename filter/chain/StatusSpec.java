package filter.chain;

import chain.Chain;
import data.STATUS;
import filter.Specification;

public class StatusSpec implements Specification<Chain> {
    private STATUS status;

    public StatusSpec(STATUS status) {
        this.status = status;
    }

    @Override
    public boolean isSatisfied(Chain item) {
        return item.chainStauts == this.status;
    }
}
