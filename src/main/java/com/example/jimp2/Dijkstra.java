package com.example.jimp2;

import java.util.*;

class ComparatorNode implements Comparator<Node> {
    public int compare(Node o1, Node o2) {
        return Double.compare(o1.getCost(), o2.getCost());
    }
}

public class Dijkstra {
    private final Container container;
    private final int rowNum;
    private final int colNum;
    private final ArrayList<Integer> visited;
    public HashMap<Integer, Integer> path;
    public HashMap<Integer,Double> cost;
    private final PriorityQueue<Node> queue;

    public Dijkstra(Container container, int rowNum, int colNum) {
        this.container = container;
        this.rowNum = rowNum;
        this.colNum = colNum;
        queue = new PriorityQueue<>(new ComparatorNode());
        visited = new ArrayList<>();
        path = new HashMap<>(); // z --> do
        cost = new HashMap<>();
    }

    public void initDijkstra(){
        for(int i =1; i<=rowNum*colNum; i++){
            cost.put(i,Double.POSITIVE_INFINITY);
        }
    }

    public boolean doDijkstra(int start, int finish)  {
        visited.add(start);
        cost.put(start,0.0);
        queue.add(new Node(start,0.0));
        while (!queue.isEmpty()) {
            Node gotNode = queue.poll();
            int node = gotNode.getNumber();
            for (Object i : container.Graph.get(node).keySet()) {
                if (!visited.contains(i) && cost.get(i) > ( (Double)container.Graph.get(node).get(i) + cost.get(node) )) {
                    path.remove((Integer) i);
                    path.put((Integer) i,gotNode.getNumber());
                    cost.put((Integer) i,  gotNode.getCost() + (Double) container.Graph.get(node).get(i));
                    queue.add(new Node((Integer) i,cost.get(node) + (Double) container.Graph.get(node).get(i)));
                    visited.add((Integer) i);
                }
            }
        }
        return true;
    }

    public void showPathDij(int from, int to) {
        if (!path.containsKey(to) || to < from) {
            System.out.println("Nie da się odnaleźć takiej drogi");
            return;
        }
        try {
            while (from != to) {
                System.out.println(to + " --> " + path.get(to) + " with total cost: " + cost.get(to));
                to = path.get(to);
            }
        } catch (NullPointerException e) {
            System.out.println("[dijkstra]: Nie ma takiej drogi");
        }
    }

}
