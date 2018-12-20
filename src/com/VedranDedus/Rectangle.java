package com.VedranDedus;

public class Rectangle {
    private int recNumber;
    private int width;
    private int height;

    public int getRecNumber() {
        return recNumber;
    }

    public Rectangle(int height , int width) {
        this.width = width;
        this.height = height;
        this.recNumber = 0;
    }

    public Rectangle(int recNumber, int height, int width) {
        this.recNumber = recNumber;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //kopiraj vrjenosti pravuknika rec u zadani pravokunik
    public void copy (Rectangle rec){
        this.recNumber = rec.getRecNumber();
        this. width = rec.getWidth();
        this.height = rec.getHeight();
    }




}
