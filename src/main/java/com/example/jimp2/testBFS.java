package com.example.jimp2;

public class testBFS {
    public static void main(String [] args){
        Reader reader = new Reader("zupa");
        reader.readFromFile();

        BFS bfs = new BFS(reader.container, reader.getRowNum(), reader.getColNum());
        bfs.doBFS();
     //   bfs.showPathBFS(5,100);




    }
}
