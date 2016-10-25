package kohl.hadrien.vtl.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * Abstract component implementation.
 */
public abstract class AbstractComponent<T> implements Component<T> {

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(type())
                .add(name(), get().toString())
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                name(),
                type(),
                get()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component that = (Component) o;
        return Objects.equals(that.name(), name())
                && Objects.equals(that.type(), type())
                && Objects.equals(that.get(), get());
    }
}