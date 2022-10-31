package net.zeeraa.novacore.spigot.abstraction.enums;

public enum EquipmentSlot {
    HAND("mainhand"),
    OFF_HAND("offhand"),
    FEET("feet"),
    LEGS("legs"),
    CHEST("chest"),
    HEAD("head"),
    ALL();


    String value;
    EquipmentSlot(String value) {
        this.value = value;
    }


    EquipmentSlot() {
        this.value = "";
    }

    public String getValue() {
        return value;
    }

}
