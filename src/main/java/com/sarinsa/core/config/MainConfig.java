package com.sarinsa.core.config;

import com.sarinsa.core.config.value.IntField;

public class MainConfig extends AbstractConfig {

    public IntField amount;

    public MainConfig() {
        super("main");
    }

    @Override
    protected void build() {
        this.amount = new IntField("amount", 1, 10, 4);
    }
}
