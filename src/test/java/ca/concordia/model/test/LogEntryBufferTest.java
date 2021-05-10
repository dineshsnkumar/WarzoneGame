package ca.concordia.model.test;

import ca.concordia.model.*;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Observer Pattern Test
 * To test logEntryBuffer
 */
public class LogEntryBufferTest {
    /**
     * This test checks if the class can be instantiated or not
     */
    @Test
    public void LogBufferTest(){
        LogEntryBuffer l_logEntryBuffer = new LogEntryBuffer(null);
        assertNotNull(l_logEntryBuffer);

    }

}
