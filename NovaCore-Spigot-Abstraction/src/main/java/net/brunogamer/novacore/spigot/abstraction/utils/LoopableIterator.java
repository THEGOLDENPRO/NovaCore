package net.brunogamer.novacore.spigot.abstraction.utils;


import java.util.*;

public class LoopableIterator<T> extends ArrayList<T> {

    private int currentObject = Integer.MIN_VALUE;
    public T next() {
        if (currentObject == Integer.MIN_VALUE || currentObject == size() - 1)
            currentObject = 0;
        else
            currentObject++;
        return get(currentObject);
    }
    public T current() {
        return get(currentObject);
    }
}
