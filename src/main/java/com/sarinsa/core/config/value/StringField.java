package com.sarinsa.core.config.value;

import java.util.function.Predicate;

public class StringField extends AbstractConfigField<String> {

    public StringField(String key, String defaultValue, Predicate<String> validator) {
        super(key, defaultValue, validator);
    }

    public StringField(String key, String defaultValue) {
        super(key, defaultValue, (string) -> true);
    }
}
