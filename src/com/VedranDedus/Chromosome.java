package com.VedranDedus;

import java.util.ArrayList;

public class Chromosome {
    private ArrayList<Rectangle> rectangleList;
    private int height;
    private int width;
    private int area;

    public int getWidth() {
        return width;
    }

    public ArrayList<Rectangle> getRectangleList() {
        return rectangleList;
    }

    public int getHeight() {
        return height;
    }

    public int getArea() {
        return area;
    }

    public Chromosome(Solution solution) {
        this.rectangleList = new ArrayList<Rectangle>();
        this.width = solution.getWidth();
        this.height = 0;
        //pomocne varijable
        //histogram, puni se BL algoritmom, u njemu gledamo visinu pruge
        int[] hist = new int[solution.getWidth()];

        ArrayList<Layer> layerList = new ArrayList<Layer>(solution.getLayerList());
        //prodji kroz sve slojeve
        for( int i = 0; i < layerList.size(); i++){
            ArrayList<Rectangle> recList = layerList.get(i).getRecList();
            //prodji kroz sve pravokutnike u sloju
            for(int j = 0; j < recList.size(); j++){
                Rectangle rectangle = new Rectangle(0,0);
                rectangle.copy(recList.get(j));
                //promjeni histogram dodavanjem novog pravokutnika
                hist = change(hist,rectangle);

                //dodaj pravokutnik u kormosom
                this.rectangleList.add(rectangle);


            }

        }
        //odredi visinu, najveci elemnt histograma
        for(int i = 0; i < hist.length; i++){
            if(hist[i] > this.height){
                this.height = hist[i];
            }
        }
        //površina pravokutnika u koji stane ovakav raspored
        this.area = this.height*this.width;
    }

    //kopiraj kromosom
    public void copy (Chromosome chromo){
        this.height = chromo.getHeight();
        this.width = chromo.getWidth();
        this.area = chromo.getArea();
        ArrayList<Rectangle> list = new ArrayList<Rectangle>(chromo.getRectangleList());
        for(int i = 0; i < list.size(); i++){
            Rectangle rec = new Rectangle(0,0);
            rec.copy(list.get(i));
            this.getRectangleList().add(rec);
            this.getRectangleList().remove(0);
        }

    }

    //napravi kormosom preko liste
    public Chromosome( ArrayList<Rectangle> rectangleList, int width){
        this.rectangleList = rectangleList;
        this.width = width;
        this.height = 0;
        // histogram
        int[] hist = new int[width];
        for(int i = 0; i < rectangleList.size(); i++){
            hist = change(hist , rectangleList.get(i));
        }

        //odredi visinu, najveci elemnt histograma
        for(int i = 0; i < hist.length; i++){
            if(hist[i] > this.height){
                this.height = hist[i];
            }
        }
        //površina pravokutnika u koji stane ovakav raspored
        this.area = this.height*this.width;


    }

    //napravi novi crossover metodom
    public Chromosome (Chromosome chromo1, Chromosome chromo2){
        //napravim kopiju liste da ne dohvacam stalno iz kromosoma
        ArrayList<Rectangle> list1 = new ArrayList<Rectangle>(chromo1.getRectangleList());
        ArrayList<Rectangle> list2 = new ArrayList<Rectangle>(chromo2.getRectangleList());
        ArrayList<Rectangle> list = new ArrayList<Rectangle>();
        //liste srednjih djelova, oni koji se zamjene
        ArrayList<Rectangle> middle1 = new ArrayList<Rectangle>();
        ArrayList<Rectangle> middle2 = new ArrayList<Rectangle>();
        int size = list1.size();
        int pos1, pos2;
        do{
            pos1 = (int)(Math.random()*size);
            pos2 = (int)(Math.random()*size);
        }while(pos1 == pos2);

        if(pos1 > pos2){
            int tmp = pos2;
            pos2 = pos1;
            pos1 = tmp;
        }

        //System.out.println(pos1 + " i " + pos2);
        //napravi novu listu dodavanjems redine iz druge lsite u prvu
        for(int i = 0; i < size; i++){
            if(i < pos1 || i >= pos2) {
                Rectangle rec = new Rectangle(0,0);
                rec.copy(list1.get(i));
                list.add(rec);
            }
            else{
                Rectangle rec = new Rectangle(0,0);
                rec.copy(list2.get(i));
                list.add(rec);
                middle1.add(list1.get(i));
                middle2.add(rec);
            }
        }
        //popravi listu tj zamjeni duplice
        //nadji duplice i s cime moramo zamjenit

        for(int i = 0; i < middle1.size(); i++){
            int rec = middle1.get(i).getRecNumber();
            for(int j = 0; j < middle2.size(); j++){
                if(rec == middle2.get(j).getRecNumber()){
                    middle1.remove(i);
                    middle2.remove(j);
                    i--;
                }
            }
        }
        //middle2 = duplici
        //middle1 = zamjene
        for(int i = 0; i < middle2.size(); i++){
            int rec = middle2.get(i).getRecNumber();
            for(int j = 0; j < list.size(); j++){
                if(j < pos1 || j >= pos2){
                    if(rec == list.get(j).getRecNumber()){
                        list.get(j).copy(middle1.get(i));
                        break;
                    }
                }
            }
        }
        //testiram dal sam mjenjo ista
        /*System.out.println("test");
        for(int i = 0; i < list1.size(); i++){
            System.out.print(list1.get(i).getRecNumber()+ " ");
        }
        System.out.println();
        for(int i = 0; i < list1.size(); i++){
            System.out.print(chromo1.getRectangleList().get(i).getRecNumber()+ " ");
        }
        System.out.println();
        for(int i = 0; i < list1.size(); i++){
            System.out.print(list2.get(i).getRecNumber()+ " ");
        }
        System.out.println();*/

        //napravi kromosom sa novom listom nakon crossovera
        this.rectangleList = list;
        this.width = chromo1.getWidth();
        this.height = 0;
        // histogram
        int[] hist = new int[width];
        for(int i = 0; i < list.size(); i++){
            hist = change(hist , list.get(i));
        }

        //odredi visinu, najveci elemnt histograma
        for(int i = 0; i < hist.length; i++){
            if(hist[i] > this.height){
                this.height = hist[i];
            }
        }
        //površina pravokutnika u koji stane ovakav raspored
        this.area = this.height*this.width;

    }

    //mutacija kromosoma
    public boolean mutation(double chance){
        ArrayList<Rectangle> list = new ArrayList<Rectangle>(this.getRectangleList());
        // nadji psudo random algoritam
        boolean firstFound = false;
        boolean mutation = false;
        int first = -1;
        for(int i = 0; i < list.size(); i++){
            double random = Math.random();
            if(random > (0.95 + chance)){
                if (firstFound){
                    firstFound = false;
                    mutation = true;
                    Rectangle tmpRec = new Rectangle(0,0);
                    tmpRec.copy(list.get(i));
                    list.get(i).copy(list.get(first));
                    list.get(first).copy(tmpRec);
                }
                else{
                   firstFound = true;
                   first = i;
                }
            }
        }
        Chromosome chromo = new Chromosome(list , this.getWidth());
        this.area = chromo.getArea();
        this.height = chromo.getHeight();
        return mutation;
    }

    //pomocna fja
    public void printChromo(){
        for (int i = 0; i < this.rectangleList.size(); i++){
            System.out.print(this.rectangleList.get(i).getRecNumber() + " ");
        }
        System.out.println();
        System.out.println("..." + this.getHeight());
    }

    //BL algoritam za punjenje histograma
    private static int[] change (int[] currentHist , Rectangle rec){
        int w = rec.getWidth();
        if(currentHist.length < w){
            System.out.println("Nije moguce, pravokutnik veci od histograma");
            return currentHist;
        }
        int h = rec.getHeight();
        int maxValue = 0;
        boolean first = true;
        int position = 0;
        for(int i = currentHist.length  - w; i >= 0; i--){
            int tmpMax = 0;
            position = i;
            for(int j = 0; j < w; j++){
                if(currentHist[i+j] + h > tmpMax){
                    tmpMax = currentHist[i+j] + h;
                }
            }
            //prije prvog pomicanja inicijaliziramo trenutnu vjernost naseg djelau histogramu
            //kao najvecu vrjednost koju dopustamo
            if(first == true){
                first = false;
                maxValue = tmpMax;
            }
            else{
                //provjera ako pomak u lijevo povecava nasu vrjednost
                if(tmpMax > maxValue){
                    break;
                }
                else{
                    maxValue = tmpMax;
                }
            }
        }
        //Ako smo dosli do lijevog ruba od te vrjednosti promjeni histogram
        if(position == 0){
            for(int i = 0; i < w; i++){
                currentHist[i] = maxValue;
            }
        }
        //ako smo stali ranije dosli smo do vrjednosti gdje raste
        //kod dodavanja histogramu pomakni u desno na vrjednost gdje nije raslo
        else{
            for(int i = 0; i < w; i++){
                currentHist[position+1+i] = maxValue;
            }
        }
        return currentHist;
    }


}
