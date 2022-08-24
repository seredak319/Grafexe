package com.example.jimp2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ReaderTest {

    private static GraphGenerator graphGenerator;
    private static Reader reader;

    @BeforeAll
    public static void init(){
        graphGenerator = new GraphGenerator(4,5,0,1,"test",1.0);
        reader = new Reader("test");
    }

    @Test
    public void ReaderTestCompareGraphgeneratorAndReader(){   // porównanie konetenerów, kosztów przejść i ilości przechowywanych wierzchołków
        graphGenerator.graphGen();
        graphGenerator.saveToFile();
        reader.readFromFile();

        Assertions.assertEquals(reader.container.getCost(1,2),graphGenerator.container.getCost(1,2));
        Assertions.assertEquals(reader.container.getCost(4,5),graphGenerator.container.getCost(4,5));
        Assertions.assertEquals(reader.container.getCost(9,8),graphGenerator.container.getCost(8,9));
        Assertions.assertEquals(reader.container.getCost(11,12),graphGenerator.container.getCost(11,12));

        Assertions.assertEquals(reader.container.howManyConnectionsFromThisNode(1),graphGenerator.container.howManyConnectionsFromThisNode(1));
        Assertions.assertEquals(reader.container.howManyConnectionsFromThisNode(3),graphGenerator.container.howManyConnectionsFromThisNode(3));
        Assertions.assertEquals(reader.container.howManyConnectionsFromThisNode(4),graphGenerator.container.howManyConnectionsFromThisNode(4));
        Assertions.assertEquals(reader.container.howManyConnectionsFromThisNode(5),graphGenerator.container.howManyConnectionsFromThisNode(5));
        Assertions.assertEquals(reader.container.howManyConnectionsFromThisNode(6),graphGenerator.container.howManyConnectionsFromThisNode(6));
        Assertions.assertEquals(reader.container.howManyConnectionsFromThisNode(7),graphGenerator.container.howManyConnectionsFromThisNode(7));
        Assertions.assertEquals(reader.container.howManyConnectionsFromThisNode(20),graphGenerator.container.howManyConnectionsFromThisNode(20));
    }

    @AfterAll
    public static void DeleteFile(){
        File file = new File("data/test.txt");
        file.delete();
    }
}