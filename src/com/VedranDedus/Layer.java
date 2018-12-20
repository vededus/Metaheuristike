package com.VedranDedus;

import java.util.ArrayList;

public class Layer {
    private int width;
    private int height;
    private int number;
    private int freeWidth;
    private double percentageFilled;
    private ArrayList<Rectangle> recList;

    public double getPercentageFilled() {
        return percentageFilled;
    }

    public void setPercentageFilled(double percentageFilled) {
        this.percentageFilled = percentageFilled;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumber() {
        return number;
    }

    public int getFreeWidth() {
        return freeWidth;
    }

    public ArrayList<Rectangle> getRecList() {
        return recList;
    }

    public Layer (Rectangle rectangle, int number, int width){
        this.height = rectangle.getHeight();
        this.width = width;
        this.freeWidth = width - rectangle.getWidth();
        this.number = number;
        this.recList = new ArrayList<Rectangle>();
        this.recList.add(rectangle);
    }

    //Ako moze dodaj pravokutnik, ako ne moze vrati false
    public boolean addRectangle (Rectangle rectangle){
        if(this.freeWidth < rectangle.getWidth() || this.height < rectangle.getHeight()){
            return false;
        }
        else{
            this.recList.add(0,rectangle);
            this.freeWidth -=rectangle.getWidth();
            return true;
        }
    }

    //pomocna za ispis sloja u obliku matrice
    public void show() {
        int[][] matrix = new int[this.getHeight()][this.getWidth()];
        Rectangle rec;
        int recNumber;
        ArrayList<Rectangle> list = this.getRecList();
        int position = 0;
        for (int i = 0; i < list.size(); i++) {
            rec = list.get(i);
            recNumber = rec.getRecNumber();
            int h = rec.getHeight();
            int w = rec.getWidth();
            for (int j = 0; j < h; j++) {
                for (int k = 0; k < w; k++) {
                    matrix[j][position + k] = recNumber;
                }
            }
            position += w;
        }


        for (int j = this.getHeight()-1; j >=0 ; j--) {
            for (int k = 0; k < this.getWidth(); k++) {
                System.out.print(matrix[j][k] + ",");
            }
            System.out.println("");
        }

    }
}
