package Setup;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

public class DBLink {

        private static final InfluxDB INFLXUDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
        private static final String DATABASE = "Selenium";

        static {
                INFLXUDB.setDatabase(DATABASE);
        }

        public static void send(final Point point) {
                INFLXUDB.write(point);
        }

}
