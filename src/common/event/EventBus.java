package common.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private static final EventBus INSTANCE = new EventBus();

    private final List<EventHandler> handlers = new CopyOnWriteArrayList<>();

    private EventBus() {}

    public static EventBus getInstance() {
        return INSTANCE;
    }

    public void register(EventHandler handler) {
        handlers.add(handler);
    }

    public void unregister(EventHandler handler) {
        handlers.remove(handler);
    }

    public void publish(Event event) {
        System.out.printf("[EVENT BUS] Published %s at %s (eventId=%s)%n",
                event.getClass().getSimpleName(),
                event.getTimestamp(),
                event.getEventId());

        for (EventHandler handler : handlers) {
            handler.handle(event);
        }
    }

    public interface EventHandler {
        void handle(Event event);
    }
}
