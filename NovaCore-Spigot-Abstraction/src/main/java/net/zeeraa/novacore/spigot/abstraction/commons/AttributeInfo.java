package net.zeeraa.novacore.spigot.abstraction.commons;

import net.zeeraa.novacore.spigot.abstraction.enums.Attribute;
import net.zeeraa.novacore.spigot.abstraction.enums.EquipmentSlot;
import net.zeeraa.novacore.spigot.abstraction.enums.Operation;

import java.util.*;

public class AttributeInfo {

    private Attribute attribute;
    private double value;
    private Operation operation;
    private Collection<EquipmentSlot> equipmentSlots;



    public AttributeInfo(Attribute attribute, double value, Operation operation) {
        this(attribute, value, operation, EquipmentSlot.ALL);
    }

    public AttributeInfo(Attribute attribute, double value, Operation operation, EquipmentSlot... equipmentSlots) {
        this(attribute, value, operation, new ArrayList<>(Arrays.asList(equipmentSlots)));
    }

    public AttributeInfo(Attribute attribute, double value, Operation operation, Collection<EquipmentSlot> equipmentSlots) {
        this.attribute = attribute;
        this.value = value;
        this.operation = operation;
        this.equipmentSlots = equipmentSlots;
    }


    public Attribute getAttribute() {
        return attribute;
    }

    public double getValue() {
        return value;
    }

    public Operation getOperation() {
        return operation;
    }

    public Collection<EquipmentSlot> getEquipmentSlots() {
        return equipmentSlots;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public void setEquipmentSlots(Collection<EquipmentSlot> equipmentSlots) {
        this.equipmentSlots = equipmentSlots;
    }


    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

