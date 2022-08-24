package com.example.jimp2;

public class testDijkstra {
    public static void main(String [] args){
        Reader reader = new Reader("zupa");
        reader.readFromFile();

        Dijkstra dijkstra = new Dijkstra(reader.container, reader.getRowNum(), reader.getColNum());
        dijkstra.initDijkstra();
        dijkstra.doDijkstra(1,9);
        dijkstra.showPathDij(1,9);
    }
}
