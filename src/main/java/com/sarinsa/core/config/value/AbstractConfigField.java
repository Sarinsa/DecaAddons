package com.sarinsa.core.config.value;

import java.util.function.Predicate;

public abstract class AbstractConfigField<T> {

    private T value;
    private final T defaultValue;
    private final String key;
    /** A predicate used to determine if this field's value is valid. */
    private Predicate<T> validator;

    public AbstractConfigField(String key, T defaultValue, Predicate<T> validator) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.validator = validator;
    }

    public T get() {
        return value == null ? defaultValue : value;
    }

    protected void setValue(T value) {
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }
}
