package com.dipartimento.demowebapplications.persistence.proxy;

import com.dipartimento.demowebapplications.model.Dish;
import com.dipartimento.demowebapplications.model.Restaurant;
import com.dipartimento.demowebapplications.persistence.DBManager;

import java.util.List;


public class DishProxy extends Dish {

    public DishProxy() {}


    public DishProxy(String name, String ingredients, List<Restaurant> restaurants) {
        super(name, ingredients, restaurants);
    }


    public List<Restaurant> getAssociatedRestaurants() {
        if (this.getRestaurants() == null || this.getRestaurants().isEmpty()) {
            this.setRestaurants(
                    DBManager.getInstance().getRestaurantDAO().findAllRestaurantsByDishName(this.getName())
            );
        }

        return this.getRestaurants();
    }
}
