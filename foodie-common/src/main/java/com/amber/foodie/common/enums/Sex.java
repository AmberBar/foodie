package com.amber.foodie.common.enums;

public enum Sex {
    FEMALE(0, "female"),
    MALE(1, "male"),
    SECRET(2, "secret");
    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

}
