package com.dipartimento.demowebapplications.model;

import lombok.*;

import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Restaurant {
    private String name;
    private String description;
    private String location;
    private List<Dish> dishes;
}
