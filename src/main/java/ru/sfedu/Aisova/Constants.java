package ru.sfedu.Aisova;

public class Constants {
    public static final String LAUNCH_APP ="Launching the application...";
    public static final String OPERATING_SYS ="Operating System: ";
    public static final String OS_NAME = "os.name";
    public static final String OS_VERSION ="os.version";
    public static final String JRE ="JRE: ";
    public static final String JAVA_VERSION ="java.version";
    public static final String JAVA_LAUNCH ="Java Launched From: ";
    public static final String JAVA_HOME ="java.home";
    public static final String CLASS_PATH ="Class Path: ";
    public static final String JAVA_CLASS ="java.class.path";
    public static final String LIBRARY_PATH ="Library Path: ";
    public static final String JAVA_LIBRARY ="java.library.path";
    public static final String USER_HOME_DIRECTORY ="User Home Directory: ";
    public static final String USER_HOME ="user.home";
    public static final String USER_WORKING_DIRECTORY ="User Working Directory: ";
    public static final String USER_DIR ="user.dir";
    public static final String TEST_INFO ="Test INFO logging.";
    public static final String START_APP ="LogClient: starting application.........";

    public static final String PROB = " ";
    public static final String SPLIT =",";
    public static final String LIST_START_SYMBOL ="[";
    public static final String LIST_END_SYMBOL ="]";
    public static final String PEOPLE_START_SYMBOL ="{";
    public static final String PEOPLE_END_SYMBOL ="}";
    public static final String CONFIG_PATH = "config.path";
    public static final String PROPERTIES = "src/main/resources/enviroment.properties";

    public static final String PATH_CSV = "csv_path";
    public static final String FILE_EXTENSION_CSV = "csv";

    public static final String PATH_XML = "xml_path";
    public static final String FILE_EXTENSION_XML = "xml";

    public static final String DB_PostgreSQL_USED = "db_PostgreSQL_used";
    public static final String DB_Heroku_USED = "db_Heroku_used";

    public static final String DB_PostgreSQL_DRIVER = "db_PostgreSQL_driver";
    public static final String DB_PostgreSQL_URL = "db_PostgreSQL_url";
    public static final String DB_PostgreSQL_USER = "db_PostgreSQL_user";
    public static final String DB_PostgreSQL_PASS = "db_PostgreSQL_pass";

    public static final String DB_Heroku_DRIVER = "db_Heroku_driver";
    public static final String DB_Heroku_URL = "db_Heroku_url";
    public static final String DB_Heroku_USER = "db_Heroku_user";
    public static final String DB_Heroku_PASS = "db_Heroku_pass";


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
    public static final String DB_UPDATE_ORDER = "UPDATE \"order\" SET created='%s', status='%s', id_customer='%s' WHERE id=%d";
    public static final String DB_UPDATE_LIST_SERVICE = "UPDATE listService SET id_service='%s' WHERE id_master=%d";
    public static final String DB_UPDATE_LIST_MASTER = "UPDATE listMaster SET id_master='%s' WHERE id_salon=%d";
    public static final String DB_UPDATE_LIST_ORDER_ITEM = "UPDATE listItem SET id_item='%s' WHERE id_order=%d";
    public static final String DB_UPDATE_STATUS = "UPDATE \"order\" SET status='%s' WHERE id=%d";


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
            "status varchar(255),\n" +
            "id_customer integer\n" +
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
    public static final String ORDER_STATUS ="status";
    public static final String ORDER_CUSTOMER ="id_customer";
    public static final String ORDER_FIELDS = ORDER_CREATED+","+ ORDER_STATUS+","+ORDER_CUSTOMER;
    public static final String ORDER_INSERT_FORMAT ="'%s','%s','%s'";

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

    public static final String LOAD_DROP ="Loading method delete all tables ...";
    public static final String LOAD_DROP_COMPLETE ="Method delete all tables was loaded";

    public static final String LOAD_CREATE ="Loading method create all tables ...";
    public static final String LOAD_CREATE_COMPLETE ="Method create all tables was loaded";

    public static final String LOAD_CREATE_SERVICE ="Loading method create service ...";
    public static final String LOAD_CREATE_SERVICE_COMPLETE ="Method create service was loaded";
    public static final String LOAD_EDIT_SERVICE ="Loading method edit service ...";
    public static final String LOAD_EDIT_SERVICE_COMPLETE ="Method edit service was loaded";
    public static final String LOAD_DELETE_SERVICE ="Loading method delete service ...";
    public static final String LOAD_DELETE_SERVICE_COMPLETE ="Method delete service was loaded";
    public static final String LOAD_GET_SERVICE ="Loading method get service by id ...";
    public static final String LOAD_GET_SERVICE_COMPLETE ="Method get service by id was loaded";

    public static final String LOAD_CREATE_NEW_CUSTOMER ="Loading method create new customer ...";
    public static final String LOAD_CREATE_NEW_CUSTOMER_COMPLETE ="Method create new customer was loaded";
    public static final String LOAD_EDIT_NEW_CUSTOMER ="Loading method edit new customer ...";
    public static final String LOAD_EDIT_NEW_CUSTOMER_COMPLETE ="Method edit new customer was loaded";
    public static final String LOAD_DELETE_NEW_CUSTOMER ="Loading method delete new customer ...";
    public static final String LOAD_DELETE_NEW_CUSTOMER_COMPLETE ="Method delete new customer was loaded";
    public static final String LOAD_GET_NEW_CUSTOMER ="Loading method get new customer by id ...";
    public static final String LOAD_GET_NEW_CUSTOMER_COMPLETE ="Method get new customer by id was loaded";

    public static final String LOAD_CREATE_REGULAR_CUSTOMER ="Loading method create regular customer ...";
    public static final String LOAD_CREATE_REGULAR_CUSTOMER_COMPLETE ="Method create regular customer was loaded";
    public static final String LOAD_EDIT_REGULAR_CUSTOMER ="Loading method edit regular customer ...";
    public static final String LOAD_EDIT_REGULAR_CUSTOMER_COMPLETE ="Method edit regular customer was loaded";
    public static final String LOAD_DELETE_REGULAR_CUSTOMER ="Loading method delete regular customer ...";
    public static final String LOAD_DELETE_REGULAR_CUSTOMER_COMPLETE ="Method delete regular customer was loaded";
    public static final String LOAD_GET_REGULAR_CUSTOMER ="Loading method get regular customer by id ...";
    public static final String LOAD_GET_REGULAR_CUSTOMER_COMPLETE ="Method get regular customer by id was loaded";

    public static final String LOAD_CREATE_MASTER ="Loading method create master ...";
    public static final String LOAD_CREATE_MASTER_COMPLETE ="Method create master was loaded";
    public static final String LOAD_EDIT_MASTER ="Loading method edit master ...";
    public static final String LOAD_EDIT_MASTER_COMPLETE ="Method edit master was loaded";
    public static final String LOAD_DELETE_MASTER ="Loading method delete master ...";
    public static final String LOAD_DELETE_MASTER_COMPLETE ="Method delete master was loaded";
    public static final String LOAD_GET_MASTER ="Loading method get master by id ...";
    public static final String LOAD_GET_MASTER_COMPLETE ="Method get master by id was loaded";

    public static final String LOAD_CREATE_LIST_SERVICE ="Loading method create list service ...";
    public static final String LOAD_CREATE_LIST_SERVICE_COMPLETE ="Method create list service was loaded";
    public static final String LOAD_EDIT_LIST_SERVICE ="Loading method edit list service ...";
    public static final String LOAD_EDIT_LIST_SERVICE_COMPLETE ="Method edit list service was loaded";
    public static final String LOAD_DELETE_LIST_SERVICE ="Loading method delete list service ...";
    public static final String LOAD_DELETE_LIST_SERVICE_COMPLETE ="Method delete list service was loaded";
    public static final String LOAD_GET_LIST_SERVICE ="Loading method get list service by id ...";
    public static final String LOAD_GET_LIST_SERVICE_COMPLETE ="Method get list service by id was loaded";

    public static final String LOAD_CREATE_SALON ="Loading method create salon ...";
    public static final String LOAD_CREATE_SALON_COMPLETE ="Method create salon was loaded";
    public static final String LOAD_EDIT_SALON ="Loading method edit salon ...";
    public static final String LOAD_EDIT_SALON_COMPLETE ="Method edit salon was loaded";
    public static final String LOAD_DELETE_SALON ="Loading method delete salon ...";
    public static final String LOAD_DELETE_SALON_COMPLETE ="Method delete salon was loaded";
    public static final String LOAD_GET_SALON ="Loading method get salon by id ...";
    public static final String LOAD_GET_SALON_COMPLETE ="Method get salon by id was loaded";

    public static final String LOAD_CREATE_LIST_MASTER ="Loading method create list master ...";
    public static final String LOAD_CREATE_LIST_MASTER_COMPLETE ="Method create list master was loaded";
    public static final String LOAD_EDIT_LIST_MASTER ="Loading method edit list master ...";
    public static final String LOAD_EDIT_LIST_MASTER_COMPLETE ="Method edit list master was loaded";
    public static final String LOAD_DELETE_LIST_MASTER ="Loading method delete list master ...";
    public static final String LOAD_DELETE_LIST_MASTER_COMPLETE ="Method delete list master was loaded";
    public static final String LOAD_GET_LIST_MASTER ="Loading method get list master by id ...";
    public static final String LOAD_GET_LIST_MASTER_COMPLETE ="Method get list master by id was loaded";

    public static final String LOAD_CREATE_ITEM ="Loading method create order item ...";
    public static final String LOAD_CREATE_ITEM_COMPLETE ="Method create order item was loaded";
    public static final String LOAD_EDIT_ITEM ="Loading method edit order item ...";
    public static final String LOAD_EDIT_ITEM_COMPLETE ="Method edit order item was loaded";
    public static final String LOAD_DELETE_ITEM ="Loading method delete order item ...";
    public static final String LOAD_DELETE_ITEM_COMPLETE ="Method delete order item was loaded";
    public static final String LOAD_GET_ITEM ="Loading method get order item by id ...";
    public static final String LOAD_GET_ITEM_COMPLETE ="Method get order item by id was loaded";

    public static final String LOAD_CREATE_ORDER ="Loading method create order ...";
    public static final String LOAD_CREATE_ORDER_COMPLETE ="Method create order was loaded";
    public static final String LOAD_EDIT_ORDER ="Loading method edit order ...";
    public static final String LOAD_EDIT_ORDER_COMPLETE ="Method edit order was loaded";
    public static final String LOAD_DELETE_ORDER ="Loading method delete order ...";
    public static final String LOAD_DELETE_ORDER_COMPLETE ="Method delete order was loaded";
    public static final String LOAD_GET_ORDER ="Loading method get order by id ...";
    public static final String LOAD_GET_ORDER_COMPLETE ="Method get order by id was loaded";

    public static final String LOAD_CREATE_LIST_ITEM ="Loading method create list order item ...";
    public static final String LOAD_CREATE_LIST_ITEM_COMPLETE ="Method create list order item was loaded";
    public static final String LOAD_EDIT_LIST_ITEM ="Loading method edit list order item ...";
    public static final String LOAD_EDIT_LIST_ITEM_COMPLETE ="Method edit list order item was loaded";
    public static final String LOAD_DELETE_LIST_ITEM ="Loading method delete list order item ...";
    public static final String LOAD_DELETE_LIST_ITEM_COMPLETE ="Method delete list order item was loaded";
    public static final String LOAD_GET_LIST_ITEM ="Loading method get list order item by id ...";
    public static final String LOAD_GET_LIST_ITEM_COMPLETE ="Method get list order item by id was loaded";

    public static final String LOAD_CALCULATE ="Loading method calculate order value ...";
    public static final String LOAD_CALCULATE_COMPLETE ="Method calculate order value was loaded";

    public static final String LOAD_VIEW_ORDER ="Loading method view order history ...";
    public static final String LOAD_VIEW_ORDER_COMPLETE ="Method view order history was loaded";

    public static final String LOAD_GET_ORDER_LIST ="Loading method get list og current orders ...";
    public static final String LOAD_GET_ORDER_LIST_COMPLETE ="Method get list og current orders was loaded";

    public static final String LOAD_CUSTOMER_REPORT ="Loading method create customer report ...";
    public static final String LOAD_CUSTOMER_REPORT_COMPLETE ="Method create customer report was loaded";

    public static final String LOAD_CHANGE_MASTER ="Loading method change the list of master ...";
    public static final String LOAD_CHANGE_MASTER_COMPLETE ="Method change the list of master was loaded";

    public static final String LOAD_ASSIGN_SERVICE ="Loading method assign service ...";
    public static final String LOAD_ASSIGN_SERVICE_COMPLETE ="Method assign service was loaded";

    public static final String LOAD_MASTER_REPORT ="Loading method create master report ...";
    public static final String LOAD_MASTER_REPORT_COMPLETE ="Method create master report was loaded";

    public static final String MASTER_COMMAND ="master";
    public static final String NEW_CUSTOMER_COMMAND ="newcustomer";
    public static final String REGULAR_CUSTOMER_COMMAND ="regularcustomer";
    public static final String ORDER_COMMAND ="order";
    public static final String ORDER_ITEM_COMMAND ="orderitem";
    public static final String SALON_COMMAND ="salon";
    public static final String SERVICE_COMMAND ="service";

    public static final String CREATE_COMMAND ="create";
    public static final String DELETE_COMMAND ="delete";
    public static final String GET_BY_ID_COMMAND ="getbyid";
    public static final String GET_ORDER_HISTORY_COMMAND ="history";
    public static final String CALCULATE_COMMAND ="calculate";

    public static final String LINE ="___________________________________________________________________________________";
    public static final String COMMA =", ";
    public static final String READ_FAIL ="Can't read properties";
    public static final String SOURCE_FAIL ="Command should begin from data source type: ";
    public static final String FILE_FAIL ="Command should have a table name in the second place: ";
    public static final String ACTION_FAIL ="Action should take the third place in command, actions: ";
    public static final String BAD_ID ="Bad id format";

    public static final String DELIMETR =",";

}
