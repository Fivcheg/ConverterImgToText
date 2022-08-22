package ru.netology.graphics.image;

import java.util.TreeMap;

    public class ColorSchema implements TextColorSchema{

    @Override
    public char convert(int color) {
        TreeMap<Integer, Character> colorMap = new TreeMap<>();
        colorMap.put( 0, '#'); //  0–31
        colorMap.put(31, '$'); // 32–63
        colorMap.put(63, '@'); // 64–94
        colorMap.put(95, '%'); // 95–126
        colorMap.put(127, '*'); // 127–158
        colorMap.put(159, '+'); // 159-190
        colorMap.put(191, '-'); // 191-222
        colorMap.put(223, '\''); // 223-255
        return colorMap.floorEntry(color).getValue();
    }
}
