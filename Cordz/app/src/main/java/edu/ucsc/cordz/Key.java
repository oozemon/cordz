package edu.ucsc.cordz;

import android.graphics.RectF;

public class Key {
    public int sound;
    public RectF rect;
    public boolean down;

    public Key(RectF rect, int sound) {
        this.rect = rect;
        this.sound = sound;
    }
}
