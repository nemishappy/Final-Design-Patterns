package filter.transaction;

import java.util.List;
import java.util.stream.Stream;

import data.Transaction;
import filter.Specification;
import filter.Filter;

public class FilterTransaction implements Filter<Transaction> {
    @Override
    public Stream<Transaction> filter(List<Transaction> items, Specification<Transaction> spec) {
        return items.stream().filter(p -> spec.isSatisfied(p));
    }
}
