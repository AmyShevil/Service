package ru.sfedu.Aisova;

public class Constants {
    static public final String SPLIT =",";
    static public final String LIST_START_SYMBOL ="[";
    static public final String LIST_END_SYMBOL ="]";
    static public final String PEOPLE_START_SYMBOL ="{";
    static public final String PEOPLE_END_SYMBOL ="}";
    public static final String CONFIG_PATH = "config.path";

    public static final String PATH_CSV = "csv_path";
    public static final String FILE_EXTENSION_CSV = "csv";

    public static final String PATH_XML = "xml_path";
    public static final String FILE_EXTENSION_XML = "xml";

    public static final String DB_PostgreSQL_USED = "db_PostgreSQL_used";
    public static final String DB_H2_USED = "db_H2_used";
    public static final String DB_Heroku_USED = "db_Heroku_used";

    public static final String DB_PostgreSQL_DRIVER = "db_PostgreSQL_driver";
    public static final String DB_PostgreSQL_URL = "db_PostgreSQL_url";
    public static final String DB_PostgreSQL_USER = "db_PostgreSQL_user";
    public static final String DB_PostgreSQL_PASS = "db_PostgreSQL_pass";

    public static final String DB_Heroku_DRIVER = "db_Heroku_driver";
    public static final String DB_Heroku_URL = "db_Heroku_url";
    public static final String DB_Heroku_USER = "db_Heroku_user";
    public static final String DB_Heroku_PASS = "db_Heroku_pass";

    public static final String DB_H2_DRIVER = "db_H2_driver";
    public static final String DB_H2_URL = "db_H2_url";
    public static final String DB_H2_USER = "db_H2_user";
    public static final String DB_H2_PASS = "db_H2_pass";


    public static final String DB_INSERT = "INSERT INTO %s (%s) VALUES (%s)";
    public static final String DB_INSERT_ORDER = "INSERT INTO \"order\" (%s) VALUES (%s)";

    public static final String DB_SELECT = "SELECT * FROM %s WHERE id=%d";
    public static final String DB_SELECT_ORDER = "SELECT * FROM \"order\" WHERE id=%d";
    public static final String DB_SELECT_ORDER_HISTORY = "SELECT * FROM \"order\" WHERE id_customer=%d";
    public static final String DB_SELECT_LIST_SERVICE = "SELECT DISTINCT id_service FROM %s WHERE id_master=%d ORDER BY id_service";
    public static final String DB_SELECT_LIST_MASTER = "SELECT DISTINCT id_master FROM %s WHERE id_salon=%d ORDER BY id_master";
    public static final String DB_SELECT_LIST_ORDER_ITEM = "SELECT DISTINCT id_item FROM %s WHERE id_order=%d ORDER BY id_item";

    public static final String DB_DELETE = "DELETE FROM %s WHERE id=%d";
    public static final String DB_DELETE_ORDER = "DELETE FROM \"order\" WHERE id=%d";
    public static final String DB_DELETE_LIST_SERVICE = "DELETE FROM %s WHERE id_master=%d";
    public static final String DB_DELETE_LIST_MASTER = "DELETE FROM %s WHERE id_salon=%d";
    public static final String DB_DELETE_LIST_ORDER_ITEM = "DELETE FROM %s WHERE id_order=%d";
    public static final String DROP_TABLES ="DROP TABLE Service, NewCustomer, RegularCustomer, listService, listMaster, listItem, OrderItem, Master, Salon, \"order\"";

    public static final String DB_UPDATE_SERVICE = "UPDATE Service SET name='%s', price='%s', description='%s' WHERE id=%d";
    public static final String DB_UPDATE_NEW_CUSTOMER = "UPDATE NewCustomer SET firstname='%s', lastname='%s', phone='%s', email='%s', discount='%s' WHERE id=%d";
    public static final String DB_UPDATE_REGULAR_CUSTOMER = "UPDATE RegularCustomer SET firstname='%s', lastname='%s', phone='%s', email='%s', numberoforders='%s' WHERE id=%d";
    public static final String DB_UPDATE_MASTER = "UPDATE Master SET firstName='%s', lastName='%s', position='%s', phone='%s', salary='%s' WHERE id=%d";
    public static final String DB_UPDATE_SALON = "UPDATE Salon SET address='%s' WHERE id=%d";
    public static final String DB_UPDATE_ORDER_ITEM = "UPDATE OrderItem SET service='%s', cost='%s', quantity='%s' WHERE id=%d";
    public static final String DB_UPDATE_ORDER = "UPDATE \"order\" SET created='%s', cost='%s', status='%s', id_customer='%s', lastUpdated='%s', completed='%s' WHERE id=%d";
    public static final String DB_UPDATE_LIST_SERVICE = "UPDATE listService SET id_service='%s' WHERE id_master=%d";
    public static final String DB_UPDATE_LIST_MASTER = "UPDATE listMaster SET id_master='%s' WHERE id_salon=%d";
    public static final String DB_UPDATE_LIST_ORDER_ITEM = "UPDATE listItem SET id_item='%s' WHERE id_order=%d";

    public static final String CREATE_SERVICE ="create table Service(\n" +
            "id serial Primary key,\n" +
            "name varchar(255),\n" +
            "price double precision,\n" +
            "description varchar(255)\n" +
            ")";
    public static final String CREATE_NEW_CUSTOMER = "create table NewCustomer(\n" +
            "id serial Primary key,\n" +
            "firstName varchar(255),\n" +
            "lastName varchar(255),\n" +
            "phone varchar(255),\n" +
            "email varchar(255),\n" +
            "discount integer\n" +
            ")";
    public static final String CREATE_REGULAR_CUSTOMER = "create table RegularCustomer(\n" +
            "id serial Primary key,\n" +
            "firstName varchar(255),\n" +
            "lastName varchar(255),\n" +
            "phone varchar(255),\n" +
            "email varchar(255),\n" +
            "numberOfOrders integer\n" +
            ")";
    public static final String CREATE_ORDER_ITEM ="create table OrderItem(\n" +
            "id serial Primary key,\n" +
            "service integer,\n" +
            "cost double precision,\n" +
            "quantity integer\n" +
            ")";
    public static final String CREATE_MASTER ="create table Master(\n" +
            "id serial Primary key,\n" +
            "firstName varchar(255),\n" +
            "lastName varchar(255),\n" +
            "position varchar(255),\n" +
            "phone varchar(255),\n" +
            "salary double precision\n" +
            ")";
    public static final String CREATE_LIST_SERVICE ="create table listService(\n" +
            "id_master integer,\n" +
            "id_service integer\n" +
            ") ";
    public static final String CREATE_SALON ="create table Salon(\n" +
            "id serial Primary key,\n" +
            "address varchar(255)\n" +
            ")";
    public static final String CREATE_LIST_MASTER ="create table listMaster(\n" +
            "id_salon integer,\n" +
            "id_master integer\n" +
            ") ";
    public static final String CREATE_ORDER = "create table \"order\"(\n" +
            "id serial Primary key,\n" +
            "created varchar(255),\n" +
            "cost double precision,\n" +
            "status varchar(255),\n" +
            "id_customer integer,\n" +
            "lastUpdated varchar(255),\n" +
            "completed varchar(255)\n" +
            ")";
    public static final String CREATE_LIST_ITEM ="create table listItem(\n" +
            "id_order integer,\n" +
            "id_item integer\n" +
            ")";

    public static final String ID ="id";

    public static final String SERVICE_NAME ="name";
    public static final String SERVICE_PRICE ="price";
    public static final String SERVICE_DESCRIPTION ="description";
    public static final String SERVICE_FIELDS = SERVICE_NAME+","+SERVICE_PRICE+","+SERVICE_DESCRIPTION;
    public static final String SERVICE_INSERT_FORMAT ="'%s','%s','%s'";

    public static final String CUSTOMER_FIRST_NAME ="firstName";
    public static final String CUSTOMER_LAST_NAME ="lastName";
    public static final String CUSTOMER_PHONE ="phone";
    public static final String CUSTOMER_EMAIL ="email";
    public static final String NEW_CUSTOMER_DISCOUNT ="discount";
    public static final String REGULAR_CUSTOMER_NUMBER ="numberOfOrders";

    public static final String NEW_CUSTOMER_FIELDS = CUSTOMER_FIRST_NAME+","+CUSTOMER_LAST_NAME+","+CUSTOMER_PHONE+","+CUSTOMER_EMAIL+","+NEW_CUSTOMER_DISCOUNT;
    public static final String REGULAR_CUSTOMER_FIELDS = CUSTOMER_FIRST_NAME+","+CUSTOMER_LAST_NAME+","+CUSTOMER_PHONE+","+CUSTOMER_EMAIL+","+REGULAR_CUSTOMER_NUMBER;
    public static final String CUSTOMER_INSERT_FORMAT ="'%s','%s','%s','%s','%s'";

    public static final String ORDER_ITEM_SERVICE ="service";
    public static final String ORDER_ITEM_COST ="cost";
    public static final String ORDER_ITEM_QUANTITY ="quantity";
    public static final String ORDER_ITEM_FIELDS = ORDER_ITEM_SERVICE+","+ORDER_ITEM_COST+","+ORDER_ITEM_QUANTITY;
    public static final String ORDER_ITEM_INSERT_FORMAT ="'%s','%s','%s'";

    public static final String ORDER_CREATED ="created";
    public static final String ORDER_ITEM ="listItem";
    public static final String ORDER_COST ="cost";
    public static final String ORDER_STATUS ="status";
    public static final String ORDER_CUSTOMER ="id_customer";
    public static final String ORDER_UPDATE ="lastUpdated";
    public static final String ORDER_COMPLETED ="completed";
    public static final String ORDER_FIELDS = ORDER_CREATED+","+ORDER_COST +","+ ORDER_STATUS+","+ORDER_CUSTOMER+","+ORDER_UPDATE+","+ORDER_COMPLETED;
    public static final String ORDER_INSERT_FORMAT ="'%s','%s','%s','%s','%s','%s'";

    public static final String MASTER_FIRST_NAME ="firstName";
    public static final String MASTER_LAST_NAME ="lastName";
    public static final String MASTER_POSITION ="position";
    public static final String MASTER_PHONE ="phone";
    public static final String MASTER_SALARY ="salary";
    public static final String MASTER_LIST_SERVICE ="listService";
    public static final String MASTER_FIELDS=MASTER_FIRST_NAME+","+MASTER_LAST_NAME+","+MASTER_POSITION+","+MASTER_PHONE+","+MASTER_SALARY;
    public static final String MASTER_INSERT_FORMAT="'%s','%s','%s','%s','%s'";

    public static final String SALON_ADDRESS ="address";
    public static final String SALON_LIST_MASTER ="listMaster";
    public static final String SALON_FIELDS = SALON_ADDRESS;
    public static final String SALON_INSERT_FORMAT ="'%s'";

    public static final String ID_SERVICE ="id_service";
    public static final String ID_MASTER ="id_master";
    public static final String LIST_SERVICE_FIELDS=ID_MASTER+","+ID_SERVICE;
    public static final String LIST_FORMAT ="'%s','%s'";

    public static final String ID_SALON ="id_salon";
    public static final String LIST_MASTER_FIELDS=ID_SALON+","+ID_MASTER;

    public static final String ID_ORDER ="id_order";
    public static final String ID_ITEM ="id_item";
    public static final String LIST_ORDER_ITEM_FIELDS=ID_ORDER+","+ID_ITEM;

    public static final String WRITE_SUCCESS = "Write success";
    public static final String WRITE_ERROR = "Write error";
    public static final String STH_NULL = "Something is null";
    public static final String READ_SUCCESS = "Read success";
    public static final String READ_ERROR = "Read error";
    public static final String NULL_VALUE = "Null value. No entry added";
    public static final String NOT_FOUND = " not found";
    public static final String ERROR_GET_ID = "ERROR GET BY ID";

    public static final String SERVICE_CREATED = "Service created";
    public static final String SERVICE_NOT_CREATED = "Service not created";
    public static final String SERVICE_ID = "Service with id = ";
    public static final String SERVICE_EDITED = "Service edited";
    public static final String SERVICE_NOT_EDITED = "Service not edited";
    public static final String SERVICE_DELETED = "Service deleted";
    public static final String SERVICE_NOT_DELETED = "Service not deleted";
    public static final String SERVICE_RECEIVED = "Service received";
    public static final String SERVICE_NOT_RECEIVED = "Service not received";

    public static final String NEW_CUSTOMER_CREATED = "New customer created";
    public static final String NEW_CUSTOMER_NOT_CREATED = "New customer not created";
    public static final String NEW_CUSTOMER_ID = "New customer with id = ";
    public static final String NEW_CUSTOMER_EDITED = "New customer edited";
    public static final String NEW_CUSTOMER_NOT_EDITED = "New customer not edited";
    public static final String NEW_CUSTOMER_DELETED = "New customer deleted";
    public static final String NEW_CUSTOMER_NOT_DELETED = "New customer not deleted";
    public static final String NEW_CUSTOMER_RECEIVED = "New customer received";
    public static final String NEW_CUSTOMER_NOT_RECEIVED = "New customer not received";

    public static final String REGULAR_CUSTOMER_CREATED = "Regular customer created";
    public static final String REGULAR_CUSTOMER_NOT_CREATED = "Regular customer not created";
    public static final String REGULAR_CUSTOMER_ID = "Regular customer with id = ";
    public static final String REGULAR_CUSTOMER_EDITED = "Regular customer edited";
    public static final String REGULAR_CUSTOMER_NOT_EDITED = "Regular customer not edited";
    public static final String REGULAR_CUSTOMER_DELETED = "Regular customer deleted";
    public static final String REGULAR_CUSTOMER_NOT_DELETED = "Regular customer not deleted";
    public static final String REGULAR_CUSTOMER_RECEIVED = "Regular customer received";
    public static final String REGULAR_CUSTOMER_NOT_RECEIVED = "Regular customer not received";

    public static final String MASTER_CREATED = "Master created";
    public static final String MASTER_NOT_CREATED = "Master not created";
    public static final String MASTER_ID = "Master with id = ";
    public static final String MASTER_EDITED = "Master edited";
    public static final String MASTER_NOT_EDITED = "Master not edited";
    public static final String MASTER_DELETED = "Master deleted";
    public static final String MASTER_NOT_DELETED = "Master not deleted";
    public static final String MASTER_RECEIVED = "Master received";
    public static final String MASTER_NOT_RECEIVED = "Master not received";

    public static final String SALON_CREATED = "Salon created";
    public static final String SALON_NOT_CREATED = "Salon not created";
    public static final String SALON_ID = "Salon with id = ";
    public static final String SALON_EDITED = "Salon edited";
    public static final String SALON_NOT_EDITED = "Salon not edited";
    public static final String SALON_DELETED = "Salon deleted";
    public static final String SALON_NOT_DELETED = "Salon not deleted";
    public static final String SALON_RECEIVED = "Salon received";
    public static final String SALON_NOT_RECEIVED = "Salon not received";

    public static final String ORDER_ITEM_CREATED = "Order item created";
    public static final String ORDER_ITEM_NOT_CREATED = "Order item not created";
    public static final String ORDER_ITEM_ID = "Order item with id = ";
    public static final String ORDER_ITEM_EDITED = "Order item edited";
    public static final String ORDER_ITEM_NOT_EDITED = "Order item not edited";
    public static final String ORDER_ITEM_DELETED = "Order item deleted";
    public static final String ORDER_ITEM_NOT_DELETED = "Order item not deleted";
    public static final String ORDER_ITEM_RECEIVED = "Order item received";
    public static final String ORDER_ITEM_NOT_RECEIVED = "Order item not received";

    public static final String ORDER_CREATE = "Order created";
    public static final String ORDER_NOT_CREATED = "Order not created";
    public static final String ORDER_ID = "Order with id = ";
    public static final String ORDER_EDITED = "Order edited";
    public static final String ORDER_NOT_EDITED = "Order not edited";
    public static final String ORDER_DELETED = "Order deleted";
    public static final String ORDER_NOT_DELETED = "Order not deleted";
    public static final String ORDER_RECEIVED = "Order received";
    public static final String ORDER_NOT_RECEIVED = "Order not received";

    public static final String COST = "Order cost ";
    public static final String EQL = " = ";
    public static final String CUSTOMER_ID = "Customer with id = ";
    public static final String LIST_CUSTOMER = "Order list customer ";
    public static final String COLON = ":";
    public static final String PROCESSING ="PROCESSING";
    public static final String CURRENT_ORDER = "Current orders list customer ";
    public static final String NOT_CURRENT_ORDER ="The order is not current";
    public static final String NUM_CUSTOMER ="Number of customer ";
    public static final String ORDER_EQL = " orders = ";
    public static final String LIST_MASTER = "Master list in salon ";
    public static final String ASSIGN_SUCCESS ="Service assign success";
    public static final String ASSIGN_FAIL ="Service assign fail";
    public static final String MASTER ="Master ";
    public static final String PROVIDE_SERVICE =" provides the following service: ";

    public static final String SERVICE_LIST = "Service list with id master = ";
    public static final String MASTER_LIST = "Master list with id salon = ";
    public static final String ORDER_ITEM_LIST = "Order item list with id order = ";

    public static final String DB_UNABLE_CONNECT ="Unable to connect to database";
    public static final String TRUE ="true";
    public static final String DB_SUCCESS = "Successful connection to ";
    public static final String HEROKU ="Heroku";
    public static final String POSTGRESQL ="PostgreSql";
    public static final String NOT_SELECTED ="You have not selected any database";

    public static final String LIST_ADD ="List added: ";
    public static final String LIST_NOT_ADD ="List not added: ";
    public static final String LIST_UPDATE ="List updated: ";
    public static final String LIST_NOT_UPDATE ="List not updated: ";
    public static final String LIST_DELETE ="List deleted: ";
    public static final String LIST_NOT_DELETE ="List not deleted: ";
    public static final String LIST_GET ="List received: ";
    public static final String LIST_NOT_GET ="List not received: ";


}
