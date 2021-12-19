package com.gamersnation.gamersnationApplication;

import com.gamersnation.gamersnationApplication.ApplicationMVC.ApplicationModel;
import com.gamersnation.gamersnationApplication.player.Player;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JavaFXApplicationTests {

    @Test //Test for merge sort algoritme
    void MergeSort(){
        Player p1 = new Player("01","test1", 50, "Gold I", true, 6,
                10, true, "Jungle");
        Player p2 = new Player("02","test2", 50, "Gold I", true, 6,
                10, true, "Jungle");
        Player p3 = new Player("03","test3", 50, "Gold I", true, 6,
                10, true, "Jungle");
        Player p4 = new Player("04","test4", 50, "Gold I", true, 6,
                10, true, "Jungle");
        Player p5 = new Player("05","test5", 50, "Gold I", true, 6,
                10, true, "Jungle");

        p1.setMatchPercent(98); p2.setMatchPercent(23); p3.setMatchPercent(55);
        p4.setMatchPercent(87); p5.setMatchPercent(19);
        ArrayList<Player> testList = new ArrayList<Player>();
        ArrayList<Player> expectedList = new ArrayList<Player>();
        testList.add(p1); testList.add(p2); testList.add(p3); testList.add(p4); testList.add(p5);
        expectedList.add(0, p1); expectedList.add(1, p4); expectedList.add(2,p3);
        expectedList.add(3, p2); expectedList.add(4, p5);
        ApplicationModel.mergeSort(testList);
        assertEquals(expectedList, testList);
    }


    @Test //Test af match udregning
    void calculator(){
        Player p1 = new Player("01","test1", 50, "Gold I", true, 6,
                10, true, "Jungle");
        Player p2 = new Player("02","test2", 50, "Gold I", true, 6,
                10, true, "Jungle");

        double result = ApplicationModel.matchPlayers(p1,p2);
        assertEquals(100.00, result);
    }

}
