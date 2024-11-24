package com.dipartimento.demowebapplications.persistence.dao.implJDBC;

import com.dipartimento.demowebapplications.model.Restaurant;
import com.dipartimento.demowebapplications.persistence.dao.RestaurantDAO;
import com.dipartimento.demowebapplications.persistence.proxy.RestaurantProxy;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RestaurantDAO_JDBC implements RestaurantDAO {

    private Connection connection;



    public RestaurantDAO_JDBC(Connection connection) {
        this.connection = connection;
    }



    @Override
    public List<Restaurant> findAll() {
        String findAllQuery = "SELECT * FROM restaurant";

        try {
            Statement findAllStatement = connection.createStatement();
            ResultSet resultSet = findAllStatement.executeQuery(findAllQuery);

            List<Restaurant> restaurants = new ArrayList<>();

            while (resultSet.next()) {
                Restaurant restaurant = new RestaurantProxy();

                restaurant.setLocation(resultSet.getString("location"));
                restaurant.setName(resultSet.getString("name"));
                restaurant.setDescription(resultSet.getString("description"));

                restaurants.add(restaurant);
            }

            return restaurants;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Restaurant findByPrimaryKey(String name) {
        String findByPrimaryKeyQuery = "SELECT * FROM restaurant WHERE name = ?";

        try {
            PreparedStatement findByPrimaryKeyStatment = connection.prepareStatement(findByPrimaryKeyQuery);
            findByPrimaryKeyStatment.setString(1, name);
            ResultSet resultSet = findByPrimaryKeyStatment.executeQuery();

            Restaurant restaurant = null;

            if (resultSet.next()) {
                restaurant = new RestaurantProxy(
                        resultSet.getString("name"), resultSet.getString("description"),
                        resultSet.getString("location"), null
                );
            }

            return restaurant;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void create(@NotNull Restaurant restaurant) {
        String createRestaurantQuery = "INSERT INTO restaurant (name, description, location) VALUES (?, ?, ?)";

        try {
            PreparedStatement createRestaurantStatement = connection.prepareStatement(createRestaurantQuery);
            createRestaurantStatement.setString(1, restaurant.getName());
            createRestaurantStatement.setString(2, restaurant.getDescription());
            createRestaurantStatement.setString(3, restaurant.getLocation());
            createRestaurantStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public Restaurant update(String name, @NotNull Restaurant restaurant) {
        String updateRestaurantQuery = "UPDATE restaurant SET name = ?, description = ?, location = ? WHERE name = ?";

        try {
            PreparedStatement updateRestaurantStatement = connection.prepareStatement(updateRestaurantQuery);
            updateRestaurantStatement.setString(1, restaurant.getName());
            updateRestaurantStatement.setString(2, restaurant.getDescription());
            updateRestaurantStatement.setString(3, restaurant.getLocation());
            updateRestaurantStatement.setString(4, name);
            updateRestaurantStatement.executeUpdate();

            return new RestaurantProxy(
                    restaurant.getName(), restaurant.getDescription(), restaurant.getLocation(), null
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(@NotNull Restaurant restaurant) {
        String deleteRestaurantQuery = "DELETE FROM restaurant WHERE name = ?";
        String removeRestaurantDishQueryAssociation = "DELETE FROM restaurant_dish WHERE restaurant_name = ?";

        try {
            PreparedStatement updateRestaurantDishStatement = connection.prepareStatement(removeRestaurantDishQueryAssociation);
            updateRestaurantDishStatement.setString(1, restaurant.getName());
            updateRestaurantDishStatement.executeUpdate();

            PreparedStatement deleteRestaurantStatement = connection.prepareStatement(deleteRestaurantQuery);
            deleteRestaurantStatement.setString(1, restaurant.getName());
            deleteRestaurantStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Restaurant> findAllRestaurantsByDishName(String dishName) {
        String findAllRestaurantByDishNameQuery = "SELECT name, description, location " +
                "FROM restaurant " +
                "JOIN restaurant_dish ON restaurant_dish.dish_name = ? and restaurant.name = restaurant_dish.restaurant_name";

        try {
            PreparedStatement findAllRestaurantByDishNameStatement = connection.prepareStatement(
                    findAllRestaurantByDishNameQuery
            );
            findAllRestaurantByDishNameStatement.setString(1, dishName);
            ResultSet resultSet = findAllRestaurantByDishNameStatement.executeQuery();

            List<Restaurant> restaurants = new ArrayList<>();

            while (resultSet.next()) {
                Restaurant restaurant = new RestaurantProxy(
                        resultSet.getString("name"), resultSet.getString("description"),
                        resultSet.getString("location"), null
                );

                restaurants.add(restaurant);
            }

            return restaurants;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean existsByName(String name) {
        String restaurantExistsQuery = "SELECT EXISTS (SELECT 1 FROM restaurant WHERE name = ?)";

        try {
            PreparedStatement restaurantExistsStatement = connection.prepareStatement(restaurantExistsQuery);
            restaurantExistsStatement.setString(1, name);
            ResultSet resultSet = restaurantExistsStatement.executeQuery();

            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
