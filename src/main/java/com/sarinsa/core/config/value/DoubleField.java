package com.sarinsa.core.config.value;

import java.util.function.Predicate;

public class DoubleField extends AbstractConfigField<Double> {

    public DoubleField(String key, double defaultValue, Predicate<Double> validator) {
        super(key, defaultValue, validator);
    }

    public DoubleField(String key, double defaultValue, double minValue, double maxValue) {
        super(key, defaultValue, (value) -> value >= minValue && value <= maxValue);
    }
}
