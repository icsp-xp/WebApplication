package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Dish;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.DishDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class TestDishDAO_JDBC {
    @BeforeEach
    public void init() {
        DBManager.getInstance().deleteDataBase();
        DBManager.getInstance().createDataBase();
    }


    @Test
    public void findAllShouldReturnAllDishes() {
        List<String> dishesName = new ArrayList<>(
                List.of(
                        "Ceviche di Gamberi", "Ratatouille",
                        "Lasagna alla Bolognese", "Risotto ai Funghi", "Sushi Nigiri"
                )
        );

        List<Dish> dishes = DBManager.getInstance().getDishDAO().findAll();
        assertEquals(5, dishes.size());

        for (String dishName : dishesName) {
            boolean found = false;

            for (Dish dish : dishes) {
                if (dish.getName().equals(dishName)) {
                    found = true;
                    break;
                }
            }

            assertTrue(found);
        }
    }


    @Test
    public void findByPrimaryKeyShouldReturnCorrectDish() {
        Dish dish = DBManager.getInstance().getDishDAO().findByPrimaryKey("Ceviche di Gamberi");

        assertNotNull(dish);
        assertEquals("Ceviche di Gamberi", dish.getName());
    }


    @Test
    public void updateShouldWorks() {
        DishDAO dao = DBManager.getInstance().getDishDAO();
        Dish dish = new Dish();
        String dishName = "Ratatouille";

        dish.setName(dishName);
        dish.setIngredients("ingredients");

        dao.update(dishName, dish);

        assertEquals(dish.getIngredients(), dao.findByPrimaryKey(dishName).getIngredients());
    }


    @Test
    public void removeShouldWorks() {
        DishDAO dao = DBManager.getInstance().getDishDAO();

        dao.delete(dao.findByPrimaryKey("Ceviche di Gamberi"));
        assertNull(dao.findByPrimaryKey("Ceviche di Gamberi"));
    }


    @Test
    public void createShouldWorks() {
        DishDAO dao = DBManager.getInstance().getDishDAO();

        String newDishName = "Pasta al tonno";
        Dish dish = new Dish(newDishName, "pasta, tonno, olio", null);

        dao.create(dish);
        assertNotNull(dao.findByPrimaryKey(newDishName));
    }
}
