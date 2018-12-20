package com.VedranDedus;

import java.util.ArrayList;

public class Solution {

    private int width;
    private int layerNumber;
    private ArrayList<Layer> layerList;

    public ArrayList<Layer> getLayerList() {
        return layerList;
    }

    public int getWidth() {
        return width;
    }

    public Solution(int width) {
        this.width = width;
        this.layerNumber = 0;
        this.layerList = new ArrayList<Layer>();
    }

    //Stavi u zadnji sloj ako ne moze otvori novi sloj
    public void nextFit (ArrayList<Rectangle> recList){
        Rectangle rec;
        for( int i = 0; i < recList.size();  i++) {
            rec = recList.get(i);
            if (this.layerList.isEmpty()) {
                this.layerNumber += 1;
                this.layerList.add(new Layer(rec, this.layerNumber, this.width));
            }
            else{
                if( this.layerList.get(this.layerNumber-1).addRectangle(recList.get(i)) == false){
                    this.layerNumber += 1;
                    this.layerList.add(new Layer(rec, this.layerNumber, this.width));
                }
            }
        }

        for(int i = 0; i < this.layerList.size(); i++){
            ArrayList<Rectangle> list = this.getLayerList().get(i).getRecList();
            int surface = 0;
            for(int j = 0; j < list.size(); j++){
                surface += list.get(j).getHeight()*list.get(j).getWidth();
            }
            this.layerList.get(i).setPercentageFilled((double)(surface)/(double)(this.layerList.get(i).getWidth()*this.layerList.get(i).getHeight()));
        }
    }

    //Pomocna pokazi sta je u kojem sloju
    public void print(){
        Layer layer;
        Rectangle rect;
        for(int i = 0; i < this.layerList.size(); i++){
            layer = this.layerList.get(i);
            for(int j = 0; j < layer.getRecList().size();  j++){
                rect = layer.getRecList().get(j);
                //System.out.print(rect.getRecNumber() + " ");
                System.out.println(rect.getRecNumber() + " u sloju " + layer.getNumber());
            }
            System.out.println();
        }
    }

    //Pogledaj sve slojeve stavi u prvi koji stane
    public void firstFit (ArrayList<Rectangle> recList){
        Rectangle rec;
        for( int i = 0; i < recList.size();  i++) {

            rec = recList.get(i);
            if (this.layerList.isEmpty()) {
                this.layerNumber += 1;
                this.layerList.add(new Layer(rec, this.layerNumber, this.width));
            }
            else{
                for(int j = 0; j < this.getLayerList().size(); j++){
                    if( this.layerList.get(j).addRectangle(recList.get(i))){
                        break;
                    }
                    if(j == this.getLayerList().size() - 1){
                        this.layerNumber += 1;
                        this.layerList.add(new Layer(rec, this.layerNumber, this.width));
                        break;
                    }
                }
            }
        }
        for(int i = 0; i < this.layerList.size(); i++){
            ArrayList<Rectangle> list = this.getLayerList().get(i).getRecList();
            int surface = 0;
            for(int j = 0; j < list.size(); j++){
                surface += list.get(j).getHeight()*list.get(j).getWidth();
            }
            this.layerList.get(i).setPercentageFilled((double)(surface)/(double)(this.layerList.get(i).getWidth()*this.layerList.get(i).getHeight()));
        }
    }

    //Pogledaj sve slojeve stavi u onaj u kojem stavljanjem ostaje najmanje mjesta
    public void bestFit (ArrayList<Rectangle> recList){
        Rectangle rec;
        for( int i = 0; i < recList.size();  i++) {

            rec = recList.get(i);
            if (this.layerList.isEmpty()) {
                this.layerNumber += 1;
                this.layerList.add(new Layer(rec, this.layerNumber, this.width));
            }
            else{
                int bestLayer = -1;
                int bestUsedSpace = this.getWidth();
                int width = rec.getWidth();
                for( int j = 0; j< this.getLayerList().size(); j++){
                    int current = this.getLayerList().get(j).getFreeWidth();
                    if( current - width > 0  && current - width < bestUsedSpace){
                        bestLayer = j;
                        bestUsedSpace = current - width;
                    }
                }
                if(bestLayer == -1){
                    this.layerNumber += 1;
                    this.layerList.add(new Layer(rec, this.layerNumber, this.width));
                }
                else {
                    this.getLayerList().get(bestLayer).addRectangle(recList.get(i));
                }
            }
        }
        for(int i = 0; i < this.layerList.size(); i++){
            ArrayList<Rectangle> list = this.getLayerList().get(i).getRecList();
            int surface = 0;
            for(int j = 0; j < list.size(); j++){
                surface += list.get(j).getHeight()*list.get(j).getWidth();
            }
            this.layerList.get(i).setPercentageFilled((double)(surface)/(double)(this.layerList.get(i).getWidth()*this.layerList.get(i).getHeight()));
        }
    }


}
