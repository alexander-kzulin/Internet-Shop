insert into products(id, price, title)
values (1, 450.0, 'Cheese'),
       (2, 45.0, 'Beer'),
       (3, 68.0, 'Milk'),
       (4, 12.0, 'Bread'),
       (5, 18.0, 'Tomato');
ALTER SEQUENCE product_seq RESTART WITH 6;
