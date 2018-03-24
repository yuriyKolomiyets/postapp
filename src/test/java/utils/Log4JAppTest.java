package utils;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Log4JAppTest {

    @Test
    public void getLogger() throws IOException {

        Logger LOGGER = Log4JApp.getLogger(Log4JApp.class);
        LOGGER.info("lalala");

        String res = cat("lod4j.txt");
        assertTrue(res.contains("lalala"));
        assertThat(res, notNullValue());
    }

    public static String cat(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        StringBuilder sb = new StringBuilder();
        try (Reader reader = new FileReader(path)) {
            int count;
            char[] buf = new char[1024];
            while ((count = reader.read(buf)) != -1) {
                sb.append(buf, 0, count);
            }
        }
        return sb.toString();
    }


}