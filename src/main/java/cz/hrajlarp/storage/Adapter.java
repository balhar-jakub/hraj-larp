package cz.hrajlarp.storage;

import java.util.Collection;

/**
 * Interface explaining DSL for storing and retrieving data from persistent store. It defines language necessary to use
 * standard crud operations.
 */
public interface Adapter<T> {
    void store(T toStore);

    void store(Collection<T> toStore);

    Collection<T> retrieve(Configuration retrieve);

    Collection<T> retrieve();

    void remove(T toDelete);

    void remove(Collection<T> toDelete);
}
