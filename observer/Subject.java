package observer;

import java.util.ArrayList;
import java.util.List;

public class Subject<T> {
    private List<Observer<T>> observers = new ArrayList<>();

    public void subscribe(Observer<T> observer) {
        observers.add(observer);
    }

    protected void propertyChanged(T source, String propertyName, Object newValue, Object oldValue)

    {
        for (Observer<T> o : observers) {
            o.handle(new PropertyChangedEvent(source, propertyName, newValue, oldValue));
        }
    }
}
