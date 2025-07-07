-- TABLE: CATEGORIES
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

INSERT INTO categories (name, description) VALUES
 ('Entradas', 'Platos que se sirven antes de la comida principal'),
 ('Postres', 'Platos que se sirven después de la comida principal'),
 ('Bebidas', 'Bebidas que se sirven en la mesa'),
 ('Platos Principales', 'Platos que se sirven en la mesa');

-- TABLE: RESTAURANTS
CREATE TABLE restaurants (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    owner_id BIGINT NOT NULL,
    phone VARCHAR(13),
    logo_url VARCHAR(255),
    nit VARCHAR(50) UNIQUE
);

-- TABLE: DISHES
CREATE TABLE dishes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category_id INTEGER NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    restaurant_id INTEGER NOT NULL,
    image_url VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_dishes_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT fk_dishes_restaurant
        FOREIGN KEY (restaurant_id)
        REFERENCES restaurants(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- TABLE: ORDERS
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    date TIMESTAMP NOT NULL DEFAULT now(),
    status VARCHAR(50) NOT NULL,
    chef_id BIGINT,
    restaurant_id INTEGER NOT NULL,

    CONSTRAINT fk_orders_restaurant
        FOREIGN KEY (restaurant_id)
        REFERENCES restaurants(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- TABLE: ORDERS_DISHES
CREATE TABLE orders_dishes (
    order_id INTEGER NOT NULL,
    dish_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,

    PRIMARY KEY (order_id, dish_id),

    CONSTRAINT fk_orders_dishes_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_orders_dishes_dish
        FOREIGN KEY (dish_id)
        REFERENCES dishes(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);