package com.example.jimp2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GraphGenerator {

    public Container container;
    private int rowNum;
    private int colNum;
    private double fromBound;
    private double toBound;
    private String fileName;
    private double howMuchCon;
    private boolean Debug;

    // Ważna informacja, wierzchołki liczone są od 1;


    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public double getFromBound() {
        return fromBound;
    }

    public double getToBound() {
        return toBound;
    }

    public String getFileName() {
        return fileName;
    }

    public GraphGenerator(int rowNum, int colNum, double fromBound, double toBound, String fileName, double howMuchCon){
        this.container = new Container();
        this.fileName = "data/"+fileName+".txt";
        this.fromBound = fromBound;
        this.toBound = toBound;
        this.colNum = colNum;
        this.rowNum = rowNum;
        this.howMuchCon = howMuchCon;
        this.Debug = false; //można zmienić na true w razie potrzeby ;)
    }

    public double getRandomCost(){
        Random random = new Random();
        return random.nextDouble(fromBound, toBound);
    }

    private boolean getRandomStatement(){
        Random random = new Random();
        return random.nextDouble() >= 1-howMuchCon  ? true : false;
    }

    public void graphGen(){
        int RowTimesCol = rowNum * colNum;
        container.initNodes(RowTimesCol);
        for(int i = 1; i<= RowTimesCol; i++){
            if((i) <= colNum) {
                if( i == 1){
                    if(Debug)   System.out.println(i+ " lewy róg góry siatki");
                    if(getRandomStatement())
                        container.addEgde(i, i + 1, getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, i + colNum, getRandomCost());

                } else if (colNum != 1 ? (i) % (colNum) == 0 : false ) {
                    if(Debug)   System.out.println(i+" prawy róg góry siatki");
                    if(getRandomStatement())
                        container.addEgde(i, i - 1, getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, i + colNum, getRandomCost());

                } else{
                    if(Debug)   System.out.println(i+" góra siatki");
                    if(getRandomStatement())
                        container.addEgde(i, i + 1, getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, i - 1, getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, i + colNum, getRandomCost());

                }
            }

            if((i > (RowTimesCol - colNum))){ // doł siatki
                if((i-1)%(colNum) == 0){
                    if(Debug)   System.out.println(i+"lewy róg doł siatki");
                    if(getRandomStatement())
                        container.addEgde(i, i + 1, getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, i - colNum, getRandomCost());

                }else if ( (i)%(colNum) == 0 ){
                    if(Debug)   System.out.println(i+" prawy róg doł siatki");
                    if(getRandomStatement())
                        container.addEgde(i, i - 1, getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, i - colNum, getRandomCost());
                } else {
                    if(Debug)   System.out.println(i+ " dół siatki");
                    if(getRandomStatement())
                        container.addEgde(i, i - 1, getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, i + 1, getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, i - colNum, getRandomCost());
                }
            }
            if(i > colNum && i <= (RowTimesCol-colNum) ){
                if( (i-1)%(colNum) == 0 ) {
                    if(Debug)   System.out.println(i + "lewy bok siatki");
                    if(getRandomStatement())
                        container.addEgde(i, (i - colNum), getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, (i + colNum), getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, (i + 1), getRandomCost());
                }else if ( (i)%(colNum) == 0 ){
                    if(Debug)    System.out.println(i+"prawy bok siatki");
                    if(getRandomStatement())
                        container.addEgde(i, (i - colNum), getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, (i + colNum), getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, (i - 1), getRandomCost());
                } else{
                    if(Debug)    System.out.println(i+ " wnętrze siatki");
                    if(getRandomStatement())
                        container.addEgde(i, (i - colNum), getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, (i + colNum), getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, (i - 1), getRandomCost());
                    if(getRandomStatement())
                        container.addEgde(i, (i + 1), getRandomCost());
                }
            }
        }
        System.out.println("liczba wierzchołków: " + container.getN() );
    }

    public void saveToFile(){

        try {
                PrintWriter writer = new PrintWriter(fileName, "UTF-8");
                writer.println(rowNum + " " + colNum);
                for (Integer i : container.Graph.keySet()) {
                    writer.print("\t ");
                    for (Object j : container.Graph.get(i).keySet()) {
                        writer.print(j + " :" + container.Graph.get(i).get(j) + "  ");
                    }
                    writer.print("\n");
                }
                writer.close();
            System.out.println("Udało się zapisać graf do pliku: "+fileName);
        } catch (IOException e) {
            System.out.println("Nie udało się zapisać grafu do pliku.");
        }
    }
}
