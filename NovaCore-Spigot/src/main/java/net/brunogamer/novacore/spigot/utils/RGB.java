package net.brunogamer.novacore.spigot.utils;

import java.awt.*;

public class RGB {

    private final int r;
    private final int g;
    private final int b;

    public RGB(Color color) {
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
    }

    public RGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }


    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }
}
