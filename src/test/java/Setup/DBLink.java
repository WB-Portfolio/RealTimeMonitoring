package Setup;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

public class DBLink {

        private static final InfluxDB INFLXUDB = InfluxDBFactory.connect(Streams.readers().getProperty("DbURL"), "root", "root");
        private static final String DATABASE = Streams.readers().getProperty("DbName");

        static {
                INFLXUDB.setDatabase(DATABASE);
        }

        public static void send(final Point point) {
                INFLXUDB.write(point);
        }

}
