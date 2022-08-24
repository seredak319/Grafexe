package com.example.jimp2;

import java.util.HashMap;

public class Container {

    // Ważna informacja, wierzchołki liczone są od 1;

    public HashMap<Integer,HashMap> Graph;
    private int n;

    public Container(){
        Graph = new HashMap<>();
        n = 0;
    }

    public void initNodes(int RowTimesCol){
        for(int i =1; i<= RowTimesCol; i++){
            this.addNode(i);
        }
    }

    public void addNode(int LineNum){
        if(!Graph.containsKey(LineNum)) {
            Graph.put(LineNum, new HashMap<Integer, Double>());
            n++;
        }
    }

    public void addEgde(int whichNode, int toNode, double cost){
            try {
                if (!Graph.get(toNode).containsKey(toNode)) {
                    if (Graph.containsKey(whichNode))
                        Graph.get(whichNode).put(toNode, cost);
                    if (Graph.containsKey(toNode))
                        Graph.get(toNode).put(whichNode, cost);
                }
            }catch (NullPointerException e){
                System.out.println("Nie da się dodać krawędzi z ["+whichNode+"] do ["+toNode+"] o wadze: ["+cost+"]");
                System.exit(1);
            }
    }

    public double getCost(int whichNode, int toNode){
        if(Graph.containsKey(whichNode))
          if(Graph.get(whichNode).containsKey(toNode))
            return (double) Graph.get(whichNode).get(toNode);

            return -1;
    }


    public void showAll(){
        for(Integer i : Graph.keySet()){
            System.out.print("Node: " + i +" " );
            for(Object j : Graph.get(i).keySet()){
                System.out.print("-->: " +j + " cost: " + Graph.get(i).get(j) + "\t");

            }
            System.out.println("");
        }
    }

    public int getN(){
        return n;
    }

    public int howManyConnectionsFromThisNode(int Node){
        return Graph.get(Node).keySet().size();
    }

}
