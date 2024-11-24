package com.dipartimento.demowebapplications.persistence.dao;

import com.dipartimento.demowebapplications.model.Dish;
import com.dipartimento.demowebapplications.model.Restaurant;

import java.util.List;


public interface DishDAO {
    List<Dish> findAll();
    Dish findByPrimaryKey(String name);
    Dish update(String name, Dish dish);
    void create(Dish dish);
    void delete(Dish dish);
    void assignDishToRestaurant(Dish dish, Restaurant restaurant);
    boolean existsByName(String name);
    List<Dish> findAllDishesByRestaurantName(String restaurantName);
}
