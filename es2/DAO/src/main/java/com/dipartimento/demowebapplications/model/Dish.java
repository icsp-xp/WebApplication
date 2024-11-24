package com.dipartimento.demowebapplications.model;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Dish {
    private String name;
    private String ingredients;
    private List<Restaurant> restaurants;
}
