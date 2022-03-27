package pers.ricardo.control;

public class ProcessTracker {

    public void track(Category value) {
        System.out.println("category value: " + value);
    }

    public enum Category {
        MANUFACTURE, UNSUSED
    }

}
