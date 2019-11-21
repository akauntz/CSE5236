package com.example.mike9.cse_app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatchCalcTest {

    @Test
    public void properMatchPercent100(){
        assertEquals(100,MatchCalc.getPercent(7,7),.05);
    }

    @Test
    public void properMatchPercent67(){
        assertEquals((2/3)*100,MatchCalc.getPercent(5,2), .05);
    }

    @Test
    public void properMatchPercent0(){
        assertEquals(0,MatchCalc.getPercent(2,5),.05);
    }
}
