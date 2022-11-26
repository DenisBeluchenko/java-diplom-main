package ru.netology.graphics.image;

public class Encoding implements TextColorSchema {
    public Encoding(int color) {
    }

    @Override
    public char convert(int color) {
        String arrColor = "#$@%*+-/";
        return arrColor.charAt(color / 32);
    }
}

