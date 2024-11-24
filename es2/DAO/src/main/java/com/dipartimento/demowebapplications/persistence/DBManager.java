package com.dipartimento.demowebapplications.persistence;

import com.dipartimento.demowebapplications.model.Dish;
import com.dipartimento.demowebapplications.persistence.dao.DishDAO;
import com.dipartimento.demowebapplications.persistence.dao.RestaurantDAO;
import com.dipartimento.demowebapplications.persistence.dao.implJDBC.DishDAO_JDBC;
import com.dipartimento.demowebapplications.persistence.dao.implJDBC.RestaurantDAO_JDBC;
import com.dipartimento.demowebapplications.persistence.proxy.DishProxy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;


public class DBManager {
    private static DBManager instance = null;
    private Connection connection = null;
    private DishDAO_JDBC dishDAO_JDBC = null;
    private RestaurantDAO_JDBC restaurantDAO_JDBC = null;



    private DBManager() {}


    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }

        return instance;
    }



    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "1234"
                );
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return connection;
    }


    public DishDAO getDishDAO() {
        if (dishDAO_JDBC == null)
            dishDAO_JDBC = new DishDAO_JDBC(getConnection());

        return dishDAO_JDBC;
    }


    public RestaurantDAO getRestaurantDAO() {
        if (restaurantDAO_JDBC == null)
            restaurantDAO_JDBC = new RestaurantDAO_JDBC(getConnection());

        return restaurantDAO_JDBC;
    }


    public void deleteDataBase() {
        String deleteQuery = "do $$ declare\n" +
                "    r record;\n" +
                "begin\n" +
                "    for r in (select tablename from pg_tables where schemaname = 'public') loop\n" +
                "            execute 'drop table if exists ' || quote_ident(r.tablename) || ' cascade';\n" +
                "        end loop;\n" +
                "end $$;";

        try {
            Statement deletaStatement = getConnection().createStatement();
            deletaStatement.execute(deleteQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void createDataBase() {
        StringBuilder createDatabaseQuery = new StringBuilder();

        try {
            for (String line : Files.readAllLines(Path.of("db/initDB.sql"))) {
                createDatabaseQuery.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Statement createDataBaseStatement = getConnection().createStatement();
            createDataBaseStatement.execute(createDatabaseQuery.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        getInstance().deleteDataBase();
        getInstance().createDataBase();
    }
}
