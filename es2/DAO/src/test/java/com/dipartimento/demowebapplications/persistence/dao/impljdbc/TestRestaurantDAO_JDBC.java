package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Restaurant;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.RestaurantDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestRestaurantDAO_JDBC {
    @BeforeEach
    public void init() {
        DBManager.getInstance().deleteDataBase();
        DBManager.getInstance().createDataBase();
    }


    @Test
    public void findAllShouldReturnAllRestaurants() {
        List<String> restaurantsName = new ArrayList<>(
                List.of("La Bella Tavola", "Green Garden Bistro", "Ocean Breeze")
        );

        List<Restaurant> restaurants = DBManager.getInstance().getRestaurantDAO().findAll();
        assertEquals(3, restaurants.size());

        for (String restaurantName : restaurantsName) {
            boolean found = false;

            for (Restaurant restaurant : restaurants) {
                if (restaurant.getName().equals(restaurantName)) {
                    found = true;
                    break;
                }
            }

            assertTrue(found);
        }
    }


    @Test
    public void findByPrimaryKeyShouldReturnCorrectRestaurant() {
        Restaurant restaurant = DBManager.getInstance().getRestaurantDAO().findByPrimaryKey("Ocean Breeze");

        assertNotNull(restaurant);
        assertEquals("Ocean Breeze", restaurant.getName());
    }


    @Test
    public void updateShouldWorks() {
        RestaurantDAO dao = DBManager.getInstance().getRestaurantDAO();
        Restaurant restaurant = new Restaurant();
        String restaurantName = "La Bella Tavola";

        restaurant.setName(restaurantName);
        restaurant.setDescription("description");
        restaurant.setLocation("location");

        dao.update(restaurantName, restaurant);

        assertEquals(restaurant.getDescription(), dao.findByPrimaryKey(restaurantName).getDescription());
        assertEquals(restaurant.getLocation(), dao.findByPrimaryKey(restaurantName).getLocation());
    }


    @Test
    public void removeShouldWorks() {
        RestaurantDAO dao = DBManager.getInstance().getRestaurantDAO();

        dao.delete(dao.findByPrimaryKey("Green Garden Bistro"));
        assertNull(dao.findByPrimaryKey("Green Garden Bistro"));
    }


    @Test
    public void createShouldWorks() {
        RestaurantDAO dao = DBManager.getInstance().getRestaurantDAO();

        String newRestaurantName = "Pizzaland";
        Restaurant restaurant = new Restaurant(newRestaurantName, "pizzeria", "rende", null);

        dao.create(restaurant);
        assertNotNull(dao.findByPrimaryKey(newRestaurantName));
    }
}
