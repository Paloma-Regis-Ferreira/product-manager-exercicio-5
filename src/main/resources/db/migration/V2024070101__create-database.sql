CREATE TABLE products (
    id bigint not null auto_increment,
    product_name varchar(60) not null,
    product_description varchar(60),
    price double not null,
    primary key (id)
);