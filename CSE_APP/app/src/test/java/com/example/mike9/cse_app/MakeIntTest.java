package com.example.mike9.cse_app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MakeIntTest {

    @Test
    public void properMakeInt100(){
        assertEquals("100",MatchesActivity.makeInt("100.00000"));
    }

    @Test
    public void properMakeInt0(){
        assertEquals("0",MatchesActivity.makeInt("0.0001"));
    }

    @Test
    public void properMakeInt66(){
        assertEquals("66",MatchesActivity.makeInt("66.6666"));
    }
}
