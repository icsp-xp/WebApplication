package com.dipartimento.demowebapplications.persistence.dao;

import com.dipartimento.demowebapplications.model.Restaurant;

import java.util.List;

public interface RestaurantDAO {
    List<Restaurant> findAll();
    Restaurant findByPrimaryKey(String name);
    void create(Restaurant restaurant);
    Restaurant update(String name, Restaurant restaurant);
    void delete(Restaurant restaurant);
    List<Restaurant> findAllRestaurantsByDishName(String dishName);
    boolean existsByName(String name);
}
