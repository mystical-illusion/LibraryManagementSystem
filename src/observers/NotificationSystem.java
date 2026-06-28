package observers;

import java.util.ArrayList;
import java.util.List;

public class NotificationSystem {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
        System.out.println("Observer registered for notifications!");
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
        System.out.println("Observer unregistered!");
    }

    public void notifyAll(String message) {
        for (Observer o : observers) {
            o.receiveNotification(message);
        }
    }
}