package com.VedranDedus;

import java.util.ArrayList;

public class Population {
    private ArrayList<Chromosome> chromoList;
    //površina koju pravokutnici zauzimaju
    private int recArea;
    private int chromoNumber;
    private Chromosome bestChromosome;

    public int getRecArea() {
        return recArea;
    }

    public void setChromoNumber(int chromoNumber) {
        this.chromoNumber = chromoNumber;
    }

    public Chromosome getBestChromosome() {
        return bestChromosome;
    }

    public Population (Chromosome chromo){
        this.chromoList = new ArrayList<Chromosome>();
        this.chromoList.add(chromo);
        this.recArea = 0;
        this.chromoNumber = 1;
        ArrayList<Rectangle> recList;
        recList = chromo.getRectangleList();
        for (int i = 0; i < recList.size(); i++){
            this.recArea += recList.get(i).getHeight()*recList.get(i).getWidth();
        }
        this.bestChromosome = new Chromosome(recList, chromo.getWidth());
    }

    public ArrayList<Chromosome> getChromoList() {
        return chromoList;
    }

    public int getChromoNumber() {
        return chromoNumber;
    }


    //OVDJE SE ZADAJE VELICINA POCETNE POPULACIJE
    public void fillPopulation(){
        //ako se ovdje promjeni treba se i removeWorst koji odrzava velicninu populacije
        while(chromoNumber != 100){
            int last = this.getChromoNumber() - 1;
            ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>(this.getChromoList().get(last).getRectangleList());
            ArrayList<Rectangle> tmpList = new ArrayList<Rectangle>();
            for(int i = 0; i < rectangles.size(); i++){
                tmpList.add(rectangles.get(i));
            }
            //random genriraj
            ArrayList<Rectangle> newList = new ArrayList<Rectangle>();
            while((double)tmpList.size() > (double)(rectangles.size())/3){
                int position  = (int)(Math.random()*tmpList.size());
                newList.add(tmpList.get(position));
                tmpList.remove(position);
            }
            for (int i = 0; i < tmpList.size(); i++){
                newList.add(tmpList.get(i));
            }
            this.addChromsome(new Chromosome(newList,this.getChromoList().get(0).getWidth()));
        }
        if(this.bestChromosome.getHeight() > this.min().getHeight()){
            this.bestChromosome.copy(this.min());
        }
    }

    public void mutation(){
        //minimalno je 0,05 posto da dode do mtacije
        //kolki chance stavimo tolko se smanji sansa
        //treba bit veci sto je lista veca, ovisi o list.size()
        //njaveci test primjer nam je 15000 pa za njega treba bit jako malo
        double chance = 1;
        chance -= 10.0/(double)this.getChromoList().size();
        chance *= 0.05;
        if(chance > 0.04){
            chance = 0.04;
        }

        for(int i = 0; i < this.getChromoList().size(); i++){
            if(this.getChromoList().get(i).mutation(chance)){
            }
        }
        for(int i = 0; i < this.getChromoList().size(); i++){
            if(this.bestChromosome.getHeight() > this.getChromoList().get(i).getHeight()){
                this.bestChromosome.copy(this.getChromoList().get(i));
            }
        }
    }

    public Chromosome min(){
        int  min = this.getChromoList().get(0).getHeight();
        int minPos = 0;
        for(int i = 1; i < this.getChromoList().size(); i++){
            if(min > this.getChromoList().get(i).getHeight()){
                min = this.getChromoList().get(i).getHeight();
                minPos = i;
            }
        }
        return this.getChromoList().get(minPos);
    }

    public void crossOver(){
        ArrayList<Chromosome> chromoList = new ArrayList<Chromosome>(this.getChromoList());
        int listSize = chromoList.size();
        int lastBest = 2*listSize/3;
        for(int i = 0; i < lastBest/2; i++){
            //System.out.println(i + " " + (lastBest-i));
            this.addChromsome(new Chromosome(chromoList.get(i),chromoList.get(lastBest-i)));
        }
        for(int i = listSize; i < this.getChromoList().size(); i++){
            if(this.bestChromosome.getHeight() > this.getChromoList().get(i).getHeight()){
                this.bestChromosome.copy(this.getChromoList().get(i));
            }
        }
    }

    //dodaj
    public boolean addChromsome (Chromosome chromosome){
        ArrayList<Chromosome> list = new ArrayList<Chromosome>(this.chromoList);
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>(chromosome.getRectangleList());
        for(int i = 0; i < list.size(); i++){
            ArrayList<Rectangle> recList = new ArrayList<Rectangle>(list.get(i).getRectangleList());
            if(equal(recList,rectangles)){
                return false;
            }
        }


        this.chromoList.add(chromosome);
        this.chromoNumber +=1;
        return true;


    }

    //makni visak
    //OVDJE SE KONTROLIRA VELICINA POPULACIJE
    public void removeWorst(){
        while(this.chromoNumber != 40) {
            int worstPos = 0;
            int worstVaule = this.getChromoList().get(0).getHeight();
            for(int i = 1; i < this.getChromoList().size(); i++){
                if(this.getChromoList().get(i).getHeight() > worstVaule){
                    worstPos = i;
                    worstVaule = this.getChromoList().get(i).getHeight();
                }
            }
            this.getChromoList().remove(worstPos);
            int number = this.getChromoNumber()-1;
            this.setChromoNumber(number);
        }
    }

    // pomocna funkcija
    public void count (){
        System.out.println(chromoList.size());
    }

    public void printPopulation(){
        for(int i = 0; i < this.getChromoList().size(); i++){
            this.getChromoList().get(i).printChromo();
        }
    }

    public void sort(){

    }

    static boolean equal(ArrayList<Rectangle> list1, ArrayList<Rectangle> list2){
        for(int i  = 0; i < list1.size(); i++){
            if(list1.get(i).getRecNumber() != list2.get(i).getRecNumber()){
                return false;
            }
        }
        return true;
    }
}
