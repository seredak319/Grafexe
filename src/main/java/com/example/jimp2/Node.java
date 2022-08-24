package com.example.jimp2;

public class Node {
    private final int number;
    private final double cost;

    public Node(int number, double cost) {
        this.number = number;
        this.cost = cost;
    }

    public int getNumber() {
        return number;
    }

    public double getCost() {
        return cost;
    }
}
