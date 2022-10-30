package net.zeeraa.novacore.spigot.abstraction.enums;

public enum Operation {
    ADD_NUMBER(0),
    ADD_SCALAR(1),
    MULTIPLY_SCALAR_1(2);

    private final int value;

    Operation(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }


}
