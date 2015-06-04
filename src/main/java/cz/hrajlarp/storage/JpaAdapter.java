package cz.hrajlarp.storage;

import java.util.Collection;
import java.util.HashSet;

/**
 * One of the adapters which can be used by the Services to store the data. It can work with any type of the entity.
 */
public class JpaAdapter<T> implements Adapter<T> {
    @Override
    public void store(T toStore) {

    }

    @Override
    public void store(Collection<T> toStore) {

    }

    @Override
    public Collection<T> retrieve(Configuration retrieve) {
        return null;
    }

    @Override
    public Collection<T> retrieve() {
        return new HashSet<>();
    }

    @Override
    public void remove(T toDelete) {

    }

    @Override
    public void remove(Collection<T> toDelete) {

    }
}
