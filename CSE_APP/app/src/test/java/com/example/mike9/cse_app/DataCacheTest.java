package com.example.mike9.cse_app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataCacheTest {
    @Test
    public void DataCache_AccessAndSet(){
        DataCache.updateName("testName");
        assertEquals(DataCache.getName(), "testName");
    }
}
