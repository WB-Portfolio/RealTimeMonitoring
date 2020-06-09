package Setup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Streams {

        public static Properties readers() {

                Properties config = new Properties();
                FileInputStream fis = null;
                try {
                        fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/Configuration.properties");
                } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                }
                try {
                        config.load(fis);
                } catch (
                        IOException e) {
                        e.printStackTrace();
                }
                return config;
        }

}
