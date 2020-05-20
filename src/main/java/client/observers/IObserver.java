package client.observers;

public interface IObserver {
    //method to update the observer, used by subject
    void update();

    //attach with subject to observe
    void setSubject(IObservable sub);
}
