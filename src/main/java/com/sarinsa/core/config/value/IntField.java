package com.sarinsa.core.config.value;

import java.util.function.Predicate;

public class IntField extends AbstractConfigField<Integer> {

    public IntField(String key, int defaultValue, Predicate<Integer> validator) {
        super(key, defaultValue, validator);
    }

    public IntField(String key, int minValue, int maxValue, int defaultValue) {
        super(key, defaultValue, (integer) -> integer >= minValue && integer <= maxValue);
    }
}
