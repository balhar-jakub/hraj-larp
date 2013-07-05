package cz.hrajlarp.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 2.4.13
 * Time: 16:15
 */
public class HashStringTest {

	@Test
    public void testDigest() throws Exception {
    	String result = new HashString().digest("asd");
        assertEquals("688787d8ff144c502c7f5cffaafe2cc588d86079f9de88304c26b0cb99ce91c6", result);
    }
}
