CREATE TABLE restaurant (
	name varchar NOT NULL,
	description varchar(200) NULL,
	location varchar NULL,
	CONSTRAINT restaurant_pk PRIMARY KEY (name)
);


CREATE TABLE dish (
	name varchar NOT NULL,
	ingredients varchar(200) NULL,
	CONSTRAINT dish_pk PRIMARY KEY (name)
);


CREATE TABLE restaurant_dish (
	restaurant_name varchar NOT NULL,
	dish_name varchar NOT NULL,
	CONSTRAINT restaurant_dish_unique UNIQUE (restaurant_name, dish_name),
	CONSTRAINT restaurant_dish_restaurant_fk FOREIGN KEY (restaurant_name) REFERENCES public.restaurant(name),
	CONSTRAINT restaurant_dish_dish_fk FOREIGN KEY (dish_name) REFERENCES public.dish(name)
);


INSERT INTO restaurant (name, description, location)
VALUES 
    ('La Bella Tavola', 'Ristorante italiano che offre piatti tradizionali e pizza', 'Roma, Italia'),
    ('Green Garden Bistro', 'Cucina vegetariana e vegana con ingredienti biologici', 'Portland, USA'),
    ('Ocean Breeze', 'Pesce fresco e vista sul mare in un''atmosfera rilassante', 'Sydney, Australia');


INSERT INTO dish (name, ingredients)
VALUES 
    ('Ceviche di Gamberi', 'Gamberi, lime, cipolla rossa, coriandolo, peperoncino, pomodorini'),
    ('Ratatouille', 'Melanzane, zucchine, peperoni, pomodori, cipolla, aglio, olio d''oliva'),
    ('Lasagna alla Bolognese', 'Pasta all''uovo, ragù di carne, besciamella, parmigiano, mozzarella'),
    ('Risotto ai Funghi', 'Riso, funghi porcini, brodo vegetale, cipolla, burro, parmigiano'),
    ('Sushi Nigiri', 'Riso per sushi, pesce crudo (tonno, salmone, ecc.), wasabi');


INSERT INTO restaurant_dish (restaurant_name, dish_name)
VALUES 
    ('La Bella Tavola', 'Lasagna alla Bolognese'),
    ('La Bella Tavola', 'Risotto ai Funghi'),
    ('Green Garden Bistro', 'Ratatouille'),
    ('Ocean Breeze', 'Ceviche di Gamberi'),
    ('Ocean Breeze', 'Sushi Nigiri');
