package com.intact.atomic;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class AtomicSupplierWithInitial<T> {
    private final T initial;
    private final Supplier<T> supplier;
    private final AtomicReference<T> reference;

    public AtomicSupplierWithInitial(T initial, Supplier<T> supplier) {
        this.initial = requireNonNull(initial);
        this.supplier = requireNonNull(supplier);
        this.reference = new AtomicReference<>(initial);
    }

    public T computeIfInitial() {
        return isInitial()
                ? reference.updateAndGet(current -> isInitial() ? supplier.get() : current)
                : reference.get();
    }

    private boolean isInitial() {
        return reference.get() == initial || (reference.get() != null && reference.get().equals(initial));
    }
}
