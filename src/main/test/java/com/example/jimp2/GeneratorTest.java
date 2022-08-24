package com.example.jimp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GeneratorTest {

    private static GraphGenerator graphGenerator;
    private static Container container;

    @BeforeAll
    public static void Init(){
       graphGenerator = new GraphGenerator(5,6,3,4,"test", 1.0);
       graphGenerator.graphGen();
    }

    private boolean ifInBounds(double x){
        if(x > graphGenerator.getFromBound() && x < graphGenerator.getToBound())
            return true;
        else
            return false;
    }

    @Test
    public void RandomTest(){  // sprawdzenie poprawności losowanych przedziałów
        double result = graphGenerator.getRandomCost();
        System.out.println("wylosowana liczba z przedzialu <3,4> to :" +result);
        Assertions.assertTrue(ifInBounds(result));
    }

    @Test
    public void GraphGenTest(){  // sprawdzenie czy wierzchołki posiadają odpowiednią ilość połączeń (w sytuacji gdy powinny być wszystkie)


        int result = 30;


        int node1 = graphGenerator.container.howManyConnectionsFromThisNode(1);
        int expected1 = 2;
        int node2 = graphGenerator.container.howManyConnectionsFromThisNode(2);
        int expected2 = 3;
        int node8 = graphGenerator.container.howManyConnectionsFromThisNode(8);
        int expected8 = 4;


        Assertions.assertEquals(result,graphGenerator.getRowNum()*graphGenerator.getColNum());
        Assertions.assertEquals(node1, expected1); // wierzchołek 1, powinien mieć dwa połączenia
        Assertions.assertEquals(node2, expected2); // wierzchołek 2, --- trzy połączenia
        Assertions.assertEquals(node8, expected8); // wierzchołek 8, --- cztery połączenia

    }
}
