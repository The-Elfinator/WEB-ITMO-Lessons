package ru.itmo.wp.lesson5.model.database;

import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtils {
    public static DataSource getDataSource() {
        return DataSourceHolder.DATA_SOURCE;
    }

    private static class DataSourceHolder {
        public static final DataSource DATA_SOURCE;

        static {
            MariaDbDataSource dataSource = new MariaDbDataSource();

            try {
                dataSource.setUrl("jdbc:mariadb://wp.codeforces.com:88/u00?useUnicode=yes&characterEncoding=UTF-8");
                dataSource.setUser("u00");
                dataSource.setPassword("p021701");
            } catch (SQLException e) {
                throw new RuntimeException("Can't setup db data source.", e);
            }

            try (Connection connection = dataSource.getConnection()) {
                if (connection == null) {
                    throw new RuntimeException("Can't create testing db connection.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Can't create testing db connection.", e);
            }

            DATA_SOURCE = dataSource;
        }
    }
}
