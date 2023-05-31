package observer;

public interface Observer<T> {
    void handle(PropertyChangedEvent<T> args);
}
