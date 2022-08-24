package com.example.jimp2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {

    private final String fileName;
    public Container container;
    private double fromBoundery;
    private double toBoundery;
    private int rowNum;
    private int colNum;


    public double getFromBoundery() {
        return fromBoundery;
    }

    public double getToBoundery() {
        return toBoundery;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    //TODO: nie koniecznie .txt, ale narazie zostawiam ;)
    //TODO: rozpoznawanie złego formatu, bo póki co jeżeli jest zły to tylko pomija ten wierzchołek np: (dobry:    4 :0.213112     zły:   4 0.1231   lub 4 : 12312

    public Reader(String fileName){
        this.fileName = "data/"+fileName+".txt";
        this.toBoundery = 0;
        this.fromBoundery = Double.MAX_VALUE;
    }

    public void readFromFile(){
        BufferedReader reader;
        container = new Container();
        Pattern IntRegex = Pattern.compile("\\d+");
        Pattern IntRegexWithDouble = Pattern.compile("\\d+(\\s)+(\\:)");
        Pattern DoubleRegex = Pattern.compile("\\d+(\\.\\d+)");
        Matcher matcherInt;
        Matcher matcherDouble;
        int i = 0;
        int ifwrong = 0; // jeżeli przeczyta za dużo liczb to coś jest źle
        try{
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null){
                if(i ==0){
                    matcherInt = IntRegex.matcher(line);
                    while (matcherInt.find()) {
                        if(ifwrong == 0)
                            rowNum = Integer.parseInt(matcherInt.group());
                        if(ifwrong == 1)
                            colNum = Integer.parseInt(matcherInt.group());
                        ifwrong++;
                        if(ifwrong>2){
                            System.out.println("Zły format pliku!");
                            System.exit(1);
                        }
                    }
                    container.initNodes(rowNum*colNum);
                    ifwrong =0;
                } else {
                matcherInt = IntRegexWithDouble.matcher(line);
                matcherDouble = DoubleRegex.matcher(line);
                while(matcherInt.find() && matcherDouble.find()){
                    double temp = Double.parseDouble(matcherDouble.group());
                    container.addEgde(i,Integer.parseInt( matcherInt.group().replace(" :","")),temp);

                    if(fromBoundery > temp)
                        fromBoundery = temp;
                    if(toBoundery < temp)
                        toBoundery = temp;
                    ifwrong++;
                    if(ifwrong > 4){  //IMPORTANT: gdy graf nie jest siatką to ten warunek będzie zły!!!, max 4 połączenia z wierzchołka
                        System.out.println("Zły format pliku!");
                        System.exit(1);
                    }
                }
                ifwrong = 0;
                }
                if(i < rowNum*colNum+1) { // jeżeli doda się enter ( pusty wierzchołek ) na końcu pliku to to się wywoła
                    i++;
                    line = reader.readLine();
                } else{
                    System.out.println("Zły format pliku!");
                    System.exit(1);
                }

            }
            reader.close();
            if(toBoundery == 0) {
                fromBoundery = -1;
                toBoundery = -1;
                System.out.println("Min val: " + fromBoundery);
                System.out.println("Max val: " + toBoundery);
                System.out.println("Brak połączeń, ale okej!");
            }
        } catch (IOException e){
            System.out.println("Nie udało sie odnaleźć pliku :(");
        }
    }
}
