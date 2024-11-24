package com.dipartimento.demowebapplications.persistence.dao.implJDBC;

import com.dipartimento.demowebapplications.model.Dish;
import com.dipartimento.demowebapplications.model.Restaurant;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.DishDAO;
import com.dipartimento.demowebapplications.persistence.proxy.DishProxy;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DishDAO_JDBC implements DishDAO {

    private Connection connection;



    public DishDAO_JDBC(Connection connection) {
        this.connection = connection;
    }



    @Override
    public List<Dish> findAll() {
        String findAllQuery = "SELECT * FROM dish";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllQuery);

            List<Dish> dishes = new ArrayList<>();

            while (resultSet.next()) {
                Dish dish = new DishProxy(
                        resultSet.getString("name"), resultSet.getString("ingredients"), null
                );

                dishes.add(dish);
            }

            return dishes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Dish findByPrimaryKey(String name) {
        String findByNameQuery = "SELECT * FROM dish WHERE name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(findByNameQuery);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            Dish dish = null;

            if (resultSet.next()) {
                dish = new DishProxy(
                        resultSet.getString("name"), resultSet.getString("ingredients"), null
                );
            }

            return dish;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Dish update(String name, @NotNull Dish dish) {
        String updateDishQuery = "UPDATE dish SET name = ?, ingredients = ? WHERE name = ?";

        try {
            PreparedStatement updateDishStatement = connection.prepareStatement(updateDishQuery);
            updateDishStatement.setString(1, dish.getName());
            updateDishStatement.setString(2, dish.getIngredients());
            updateDishStatement.setString(3, name);
            updateDishStatement.executeUpdate();

            return new DishProxy(dish.getName(), dish.getIngredients(), null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void create(@NotNull Dish dish) {
        String addDishQuery = "INSERT INTO dish (name, ingredients) VALUES (?, ?)";

        try {
            PreparedStatement addDishStatement = connection.prepareStatement(addDishQuery);
            addDishStatement.setString(1, dish.getName());
            addDishStatement.setString(2, dish.getIngredients());
            addDishStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void assignDishToRestaurant(@NotNull Dish dish, @NotNull Restaurant restaurant) {
        if (!existsByName(dish.getName()) || !DBManager.getInstance().getRestaurantDAO().existsByName(restaurant.getName())) {
            System.err.println("The dish or the restaurant (or both) do not exist");
            return;
        }

        String updateRestaurantDishQuery = "INSERT INTO restaurant_dish (restaurant_name, dish_name) VALUES (?, ?)";

        try {
            PreparedStatement updateRestaurantDishStatement = connection.prepareStatement(updateRestaurantDishQuery);
            updateRestaurantDishStatement.setString(1, restaurant.getName());
            updateRestaurantDishStatement.setString(2, dish.getName());
            updateRestaurantDishStatement.executeUpdate();

            restaurant.getDishes().add(dish);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean existsByName(String name) {
        String dishExistsQuery = "SELECT EXISTS (SELECT 1 FROM dish WHERE name = ?)";

        try {
            PreparedStatement dishExistsStatement = connection.prepareStatement(dishExistsQuery);
            dishExistsStatement.setString(1, name);
            ResultSet resultSet = dishExistsStatement.executeQuery();

            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Dish> findAllDishesByRestaurantName(String restaurantName) {
        String findAllDishesByrestaurantNameQuery = "SELECT name, ingredients " +
                "FROM dish " +
                "JOIN restaurant_dish ON " +
                "restaurant_dish.restaurant_name = ? and dish.name = restaurant_dish.dish_name";

        try {
            PreparedStatement findAllDishesByRestaurantNameStatment = connection.prepareStatement(
                    findAllDishesByrestaurantNameQuery
            );
            findAllDishesByRestaurantNameStatment.setString(1, restaurantName);
            ResultSet resultSet = findAllDishesByRestaurantNameStatment.executeQuery();

            ArrayList<Dish> dishes = new ArrayList<>();

            while (resultSet.next()) {
                Dish dish = new DishProxy(
                        resultSet.getString("name"), resultSet.getString("ingredients"), null
                );
                dishes.add(dish);
            }

            return dishes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(@NotNull Dish dish) {
        String deleteDishQuery = "DELETE FROM dish WHERE name = ?";
        String deleteRestaurantDishAssociationQuery = "DELETE FROM restaurant_dish WHERE dish_name = ?";

        try {
            PreparedStatement updateRestaurantDishStatement = connection.prepareStatement(deleteRestaurantDishAssociationQuery);
            updateRestaurantDishStatement.setString(1, dish.getName());
            updateRestaurantDishStatement.executeUpdate();

            PreparedStatement deleteDishStatement = connection.prepareStatement(deleteDishQuery);
            deleteDishStatement.setString(1, dish.getName());
            deleteDishStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
