package com.dipartimento.demowebapplications.persistence.proxy;

import com.dipartimento.demowebapplications.model.Dish;
import com.dipartimento.demowebapplications.model.Restaurant;
import com.dipartimento.demowebapplications.persistence.DBManager;

import java.util.List;


public class RestaurantProxy extends Restaurant {

    public RestaurantProxy() {}


    public RestaurantProxy(String name, String description, String location, List<Dish> dishes) {
        super(name, description, location, dishes);
    }



    public List<Dish> getAssociatedDishes() {
        if (getDishes() == null || getDishes().isEmpty()) {
            setDishes(
                    DBManager.getInstance().getDishDAO().findAllDishesByRestaurantName(getName())
            );
        }

        return getDishes();
    }
}
