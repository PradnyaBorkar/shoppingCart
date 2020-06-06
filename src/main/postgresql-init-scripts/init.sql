CREATE USER shoppingCartUser_rw WITH PASSWORD 'shoppingCart123' ;
CREATE USER shoppingCartUser WITH PASSWORD 'shoppingCart123';

CREATE DATABASE shoppingCart;

GRANT ALL ON DATABASE shoppingCart TO  shoppingCartUser_rw;
GRANT CONNECT ON DATABASE shoppingCart TO shoppingCartUser;