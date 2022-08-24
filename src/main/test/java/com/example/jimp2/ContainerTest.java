package com.example.jimp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContainerTest {




    @Test
    public void ContainerTestNotAddingIfAlreadyAdded(){  // sprawdzenie czy nie może zostać dodany omyłkowo zły wierzchołek

        //Given
        Container testCont = new Container();
        int expected = 1; // liczba wierzchołków

        //When
        testCont.addNode(0);
        testCont.addNode(0);
        testCont.addNode(0);

        //Then
        Assertions.assertEquals(testCont.getN(), expected);

    }
    @Test
    public void ContainerTestConnctions() {

        //Given
        Container container = new Container();
        int expectedCost1 = 12;
        int expectedCost2 = 13;
        int expectedCost3 = 24;
        int expectedCost4 = 34;


        //When
        container.addNode(1);
        container.addNode(2);
        container.addNode(3);
        container.addNode(4);

        container.addEgde(1,2,12.0);
        container.addEgde(1,3,13.0);
        container.addEgde(2,4,24.0);
        container.addEgde(4,3,34.0);


        //Then
        System.out.println("cost from [1] --> [2] expected: " + expectedCost1 +" and got: "+container.getCost(1,2));
        Assertions.assertEquals(expectedCost1, container.getCost(1,2));
        System.out.println("cost from [2] --> [1] expected: " + expectedCost1 +" and got: "+container.getCost(2,1));
        Assertions.assertEquals(expectedCost1, container.getCost(2,1));  // w obu kierunkach

        Assertions.assertEquals(expectedCost2, container.getCost(1,3));
        Assertions.assertEquals(expectedCost2, container.getCost(3,1));
        Assertions.assertEquals(expectedCost3, container.getCost(2,4));
        Assertions.assertEquals(expectedCost4, container.getCost(3,4));


    }

}
