create table if not exists Person
(
    id           identity,
    username     varchar(50) not null,
    password     varchar(50) not null,
    full_Name    varchar(50) not null,
    street       varchar(50) not null,
    city         varchar(50) not null,
    state        varchar(50) not null,
    zip          varchar(50) not null,
    phone_Number varchar(50) not null
);
create table Taco_Order
(
    id              identity,
    delivery_name   varchar(50) not null,
    delivery_street varchar(50) not null,
    delivery_city   varchar(50) not null,
    delivery_state  varchar(2)  not null,
    delivery_zip    varchar(10) not null,
    cc_number       varchar(16) not null,
    cc_expiration   varchar(5)  not null,
    cc_cvv          varchar(3)  not null,
    taco_ids        array
);
create table Taco
(
    id             identity,
    slug           varchar(4)  not null,
    name           varchar(50) not null,
    created_at     timestamp   not null,
    ingredient_ids array
);
create table if not exists Ingredient_Ref
(
    ingredient varchar(4) not null,
    taco       bigint     not null,
    taco_key   bigint     not null
);
create table Ingredient
(
    id   identity,
    slug varchar(4)  not null,
    name varchar(25) not null,
    type varchar(10) not null
);
alter table Taco
    add foreign key (taco_order) references Taco_Order (id);
alter table Ingredient_Ref
    add foreign key (ingredient) references Ingredient (id);
alter table Taco_Order
    add foreign key (person) references Person (id);