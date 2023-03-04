package com.sarinsa.core.config.value;

import java.util.List;
import java.util.function.Predicate;

public class StringListField extends AbstractConfigField<List<String>> {

    public StringListField(String key, List<String> defaultValue, Predicate<List<String>> validator) {
        super(key, defaultValue, validator);
    }

    public StringListField(String key, List<String> defaultValue) {
        super(key, defaultValue, (list) -> true);
    }
}
