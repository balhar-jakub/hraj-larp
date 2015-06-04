package cz.hrajlarp.service.calendar;

import cz.hrajlarp.storage.Adapter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * It probably should be Singleton as it actually is unique internal representation of the Calendar. You can have more
 * configured differently.
 */
public class LarpCalendar implements Calendar {
    private Set<Event> allEvents;
    private Adapter<Event> persistentStore;

    public LarpCalendar(Adapter<Event> persistentStore) {
        this.persistentStore = persistentStore;
        allEvents = new HashSet<>(persistentStore.retrieve()); // When starting application all the data are loaded into the memory
    }

    @Override
    public Collection<Event> events(Configuration options) {
        return allEvents.stream()
                .filter(event -> options.from() != null || event.from().after(options.from()))
                .filter(event -> options.to() != null || event.to().before(options.to()))
                .filter(event -> options.location() == null || event.location().equals(options.location()))
                .collect(toList());
    }

    @Override
    public Collection<Event> events() {
        return allEvents;
    }

    @Override
    public boolean contains(Event event) {
        return allEvents.contains(event);
    }

    @Override
    public Calendar add(Event event) {
        assertEventIsNotPresent(event);

        allEvents.add(event);
        persistentStore.store(event); // Possibly use replace?

        return this;
    }

    private void assertEventIsNotPresent(Event event) {
        if(allEvents.contains(event)) {
            throw new RuntimeException("Event is already present in the calendar.");
        }
    }

    @Override
    public Calendar remove(Event event) {
        assertEventIsPresent(event);

        allEvents.remove(event);
        persistentStore.remove(event);

        return this;
    }

    private void assertEventIsPresent(Event event) {
        if(!allEvents.contains(event)) {
            throw new RuntimeException("Event is not present in current calendar.");
        }
    }
}
