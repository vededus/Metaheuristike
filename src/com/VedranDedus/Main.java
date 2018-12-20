package com.VedranDedus;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        int width = 0;
        ArrayList<Rectangle> list = new ArrayList<Rectangle>();

        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader("lw733.txt"));
            String line;
            line = br.readLine();
            String[] lineArray = line.split("\\s+");

            width = Integer.parseInt(lineArray[0]);
            System.out.println("Zadana širina je " + width);
            System.out.println("Najbolje visina je " + lineArray[1]);
            line = br.readLine();
            System.out.println(line + " pravokunika je u danom setu");
            int i =1;
            while((line = br.readLine()) != null){
                String[] rec = line.split("\\s+");
                int recWidth = Integer.parseInt(rec[0]);
                int recHeight = Integer.parseInt(rec[1]);
                list.add(new Rectangle(i,recHeight,recWidth));
                //System.out.println("...." + i);
                i++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        /*width=20;
        list.add(new Rectangle(1,4,14));
        list.add(new Rectangle(2,5,2));
        list.add(new Rectangle(3,2,2));
        list.add(new Rectangle(4,9,7));
        list.add(new Rectangle(5,5,5));
        list.add(new Rectangle(6,2,5));
        list.add(new Rectangle(7,7,7));
        list.add(new Rectangle(8,3,5));
        list.add(new Rectangle(9,6,5));
        list.add(new Rectangle(10,3,2));
        list.add(new Rectangle(11,6,2));
        list.add(new Rectangle(12,4,6));
        list.add(new Rectangle(13,6,3));
        list.add(new Rectangle(14,10,3));
        list.add(new Rectangle(15,6,3));
        list.add(new Rectangle(16,10,3));*/


        Solution sol1 = new Solution(width);
        Solution sol2 = new Solution(width);
        Solution sol3 = new Solution(width);












        Rectangle rec = new Rectangle(0,0);


        for(int i = 0; i < list.size(); i++){
            for (int j = i; j < list.size(); j++){
                if(list.get(i).getHeight() < list.get(j).getHeight()){
                    rec.copy(list.get(i));
                    list.get(i).copy(list.get(j));
                    list.get(j).copy(rec);
                }
            }
        }
        //System.out.println("nextFit");
        sol1.nextFit(list);
        //System.out.println("firstFit");
        sol2.firstFit(list);
        //System.out.println("bestFit");
        sol3.bestFit(list);



        Population population = new Population(new Chromosome(sol1));
        population.addChromsome(new Chromosome(sol2));
        population.addChromsome(new Chromosome(sol3));
        //Chromosome cr1 = new Chromosome(sol1);
        //Chromosome cr2 = new Chromosome(sol3);
        population.fillPopulation();
       /* System.out.println(".....");
        population.getBestChromosome().printChromo();
        population.count();
        population.crossOver();
        population.count();
        population.min().printChromo();
        System.out.println("prije remove");
        population.count();
        population.removeWorst();
        System.out.println("nakon remove");
        population.count();
        population.getBestChromosome().printChromo();


        population.mutation();
        population.min().printChromo();*/

        /*Chromosome cr5 = new Chromosome(cr1,cr2);
        cr1.printChromo();
        cr2.printChromo();

        cr5.printChromo();*/
        System.out.println();
        System.out.println("evolution");
        int count = 0;
        int stoppingCondition = population.getRecArea();
        stoppingCondition +=population.getBestChromosome().getWidth();
        while(true){
            count++;
            population.crossOver();
            population.mutation();
            population.removeWorst();
            if(population.getBestChromosome().getArea() < stoppingCondition ){
                break;
            }
            // za 1000  radi dosta brzo a ze povecanje iteracije ne daje bolje rezulate
            if(count >= 1000){
                break;
            }
        }
        System.out.println("najbolji kromosom i njegova visina");
        population.getBestChromosome().printChromo();
        System.out.println("Potrebno iteracija = " + count);

    }


}
