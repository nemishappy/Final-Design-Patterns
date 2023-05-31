package observer;

public class PropertyChangedEvent<T> {
    public T source;
    public String propertyName;
    public Object newValue;
    public Object oldValue;

    public PropertyChangedEvent(T source, String propertyName, Object newValue, Object oldValue) {
        this.source = source;
        this.propertyName = propertyName;
        this.newValue = newValue;
        this.oldValue = oldValue;
    }
}
