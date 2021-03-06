package com.intact.atomic;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class AtomicSupplier<T> {
    private final AtomicReference<T> reference;
    private final Supplier<T> supplier;

    public AtomicSupplier(Supplier<T> supplier) {
        this.supplier = requireNonNull(supplier);
        this.reference = new AtomicReference<>(null);
    }

    public T get() {
        return reference.get() != null
                ? reference.get()
                : reference.updateAndGet(current -> current == null ? supplier.get() : current);
    }

    @Override
    public String toString() {
        return "AtomicSupplier{" +
                "reference=" + reference +
                ", supplier=" + supplier +
                '}';
    }
}

