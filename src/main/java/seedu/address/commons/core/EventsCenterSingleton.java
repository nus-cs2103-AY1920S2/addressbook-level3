package seedu.address.commons.core;

import com.google.common.eventbus.EventBus;
import seedu.address.commons.events.BaseEvent;

import java.util.logging.Logger;


/**
 * Manages the event dispatching of the app.
 */
public class EventsCenterSingleton {

    private static final Logger logger = LogsCenter.getLogger(EventsCenterSingleton.class);
    private static EventsCenterSingleton instance;
    private final EventBus eventBus;

    private EventsCenterSingleton() {
        eventBus = new EventBus();
    }

    // This logic is to make sure singleton design
    public static EventsCenterSingleton getInstance() {
        if (instance == null) {
            instance = new EventsCenterSingleton();
        }
        return instance;
    }

    public static void clearSubscribers() {
        instance = null;
    }

    public void registerHandler(Object handler) {
        eventBus.register(handler);
    }

    /**
     * Posts an event to the event bus.
     */
    public EventsCenterSingleton post(BaseEvent event) {
        logger.info(
                "------[Event Posted] " + event.getClass().getCanonicalName() + ": " + event.toString());
        eventBus.post(event);
        return this;
    }

}
