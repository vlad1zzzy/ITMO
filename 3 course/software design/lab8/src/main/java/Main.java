import event.EventsStatisticImpl;

public class Main {
    public static void main(String[] args) {
        var events = new EventsStatisticImpl();

        events.incEvent("main");

        events.printStatistic();
    }
}
