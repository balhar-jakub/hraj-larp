package cz.hrajlarp.utils;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 2.4.13
 * Time: 16:15
 */
public class FileUtilsTest {
    @Test
    public void testGetFileType() throws Exception {
        String result = FileUtils.getFileType("soubor.jpg");
        assertEquals("jpg", result);
    }

    @Test
    public void testGetFileType2() throws Exception {
        String result = FileUtils.getFileType("soubor.png");
        assertEquals("png", result);
    }

    @Test
    // TODO upravit FileUtils, aby vrátili prázdno
    public void testGetFileType3() throws Exception {
        String result = FileUtils.getFileType("soubor");
        assertEquals("", result);
    }
}
