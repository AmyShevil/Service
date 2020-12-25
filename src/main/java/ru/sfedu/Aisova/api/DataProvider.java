package ru.sfedu.Aisova.api;

import ru.sfedu.Aisova.model.*;
import java.util.List;
import java.util.Optional;

/**
 * The interface Data provider
 */
public interface DataProvider {

    /**
     * Добавить новую услугу, которую предоставляет данная компания
     *
     * @param name значение name
     * @param price значение price
     * @param description значение description
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean createService (String name, Double price, String description) throws Exception;

    /**
     * Внесение изменений в существующую услугу.
     * Например, изменить стоимость или описание
     *
     * @param id значение id
     * @param name значение name
     * @param price значение price
     * @param description значение description
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean editService (long id, String name, Double price, String description) throws Exception;

    /**
     * Удаление услуг, которые уже не предоставляются в данной компании
     *
     * @param id значение id
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean deleteService (long id) throws Exception;

    /**
     * Получение услуги по id
     *
     * @param id значение id
     * @return услуга
     * @throws NullPointerException когда входные переменные равны null
     */
    Service getServiceById(long id) throws Exception;

    /**
     * Создание нового пользователя
     *
     * @param firstName значение first name
     * @param lastName значение last name
     * @param phone значение phone
     * @param email значение email
     * @param discount значение discount
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean createNewCustomer(String firstName, String lastName, String phone, String email, Integer discount) throws Exception;

    /**
     * Редактирование нового пользователя
     *
     * @param id значение id
     * @param firstName значение first name
     * @param lastName значение last name
     * @param phone значение phone
     * @param email значение email
     * @param discount значение discount
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean editNewCustomer(long id, String firstName, String lastName, String phone, String email, Integer discount) throws Exception;

    /**
     * Удаление нового пользователя
     *
     * @param id значение id
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean deleteNewCustomer(long id) throws Exception;

    /**
     * Получение нового пользователя по id
     *
     * @param id значение id
     * @return новый пользователь
     * @throws NullPointerException когда входные переменные равны null
     */
    Optional<NewCustomer> getNewCustomerById(long id) throws Exception;

    /**
     * Создание постоянного пользователя
     *
     * @param firstName значение first name
     * @param lastName значение last name
     * @param phone значение phone
     * @param email значение email
     * @param countOfOrder значение discount
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean createRegularCustomer(String firstName, String lastName, String phone, String email, Integer countOfOrder) throws Exception;

    /**
     * Редактирование постоянного пользователя
     *
     * @param id значение id
     * @param firstName значение first name
     * @param lastName значение last name
     * @param phone значение phone
     * @param email значение email
     * @param countOfOrder значение discount
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean editRegularCustomer(long id, String firstName, String lastName, String phone, String email, Integer countOfOrder) throws Exception;

    /**
     * Удаление постоянного пользователя
     *
     * @param id значение id
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean deleteRegularCustomer(long id) throws Exception;

    /**
     * Получение постоянного пользователя по id
     *
     * @param id значение id
     * @return постоянный пользователь
     * @throws NullPointerException когда входные переменные равны null
     */
    Optional<RegularCustomer> getRegularCustomerById(long id) throws Exception;

    /**
     * Позволяет добавить нового мастера в салон
     *
     * @param firstName значение first name
     * @param lastName значение last name
     * @param position значение position
     * @param phone значение phone
     * @param salary значение salary
     * @param listService значение list service
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean createMaster (String firstName, String lastName, String position, String phone, Double salary, List<Service> listService, boolean needCreateMaster) throws Exception;

    /**
     * Позволяет внести изменения в данные мастера
     *
     * @param id значение id
     * @param firstName значение first name
     * @param lastName значение last name
     * @param position значение position
     * @param phone значение phone
     * @param salary значение salary
     * @param listService значение list service
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean editMaster (long id, String firstName, String lastName, String position, String phone, Double salary, List<Service> listService, boolean needEditMaster) throws Exception;

    /**
     * Удаление мастера, который уже не предоставляет услуги
     *
     * @param id значение id
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean deleteMaster (long id, boolean needDeleteMaster) throws Exception;

    /**
     * Получение мастера по id
     *
     * @param id значение id
     * @return мастер
     */
    Master getMasterById(long id) throws Exception;

    /**
     * Создание салона
     *
     * @param address значение address
     * @param listMaster значение list master
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean createSalon (String address, List<Master> listMaster) throws Exception;

    /**
     * Редактирование салона
     *
     * @param id значение id
     * @param address значение address
     * @param listMaster значение list master
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean editSalon (long id, String address, List<Master> listMaster) throws Exception;

    /**
     * Удаление салона
     *
     * @param id значение id
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean deleteSalon (long id) throws Exception;

    /**
     * Получение салона по id
     *
     * @param id значение id
     * @return салон
     * @throws NullPointerException когда входные переменные равны null
     */
    Salon getSalonById(long id) throws Exception;

    /**
     * Создание строки заказа
     *
     * @param service значение service
     * @param quantity значение quantity
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean createOrderItem (Service service, Integer quantity) throws Exception;

    /**
     * Редактирование строки заказа
     *
     * @param id значение id
     * @param service значение service
     * @param cost значение cost
     * @param quantity значение quantity
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean editOrderItem (long id, Service service, Double cost, Integer quantity) throws Exception;

    /**
     * Удаление строки заказа
     *
     * @param id значение id
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean deleteOrderItem (long id) throws Exception;

    /**
     * Получение строки заказа по id
     *
     * @param id значение id
     * @return строка заказа
     * @throws NullPointerException когда входные переменные равны null
     */
    OrderItem getOrderItemById(long id) throws Exception;

    /**
     * Создание заказа на оказание услуги и заполнение необходимой для заказа информации.
     * Подразумевает под собой онлайн-запись на услугу
     *
     * @param created значение created
     * @param item значение item
     * @param status значение status
     * @param customerId значение customer id
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean createOrder (String created, List<OrderItem> item, String status, long customerId) throws Exception;

    /**
     * Возможность вносить изменения в текущий заказ
     *
     * @param id значение id
     * @param created значение created
     * @param item значение item
     * @param status значение status
     * @param customerId значение customer id
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean editOrder (long id, String created, List<OrderItem> item, String status, long customerId) throws Exception;

    /**
     * Отменить заказ, если он был создан по ошибке, либо клиент больше не нуждается в оказании услуги
     *
     * @param id значение id
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean deleteOrder (long id) throws Exception;

    /**
     * Получение заказа по id
     *
     * @param id значение id
     * @return заказ
     * @throws NullPointerException когда входные переменные равны null
     */
    Order getOrderById(long id) throws Exception;

    /**
     * Рассчитать полную стоимость созданного заказа на оказание услуги с учетом использованных материалов
     * 
     * @param orderId значение orderId
     * @return стоимость заказа
     * @throws NullPointerException когда входные переменные равны null
     */
    Double calculateOrderValue(long orderId) throws Exception;

    /**
     * Позволяет просмотреть историю заказов клиента. Текущие заказы, отмененные заказы, выполненные заказы.
     * Каждый заказ включает в себя номер заказа, дату, сумму, статус
     *
     * @param customerId значение customerId
     * @return список заказов
     * @throws NullPointerException когда входные переменные равны null
     */
    List<Order> viewOrderHistory(long customerId) throws Exception;

    /**
     * Получить список текущих заказов.
     * Каждый заказ включает в себя номер заказа, дату, сумму, статус
     *
     * @param customerId значение customerId
     * @param status статус заказа
     * @return список текущих заказов
     * @throws NullPointerException когда входные переменные равны null
     */
    List<Order> getListOfCurrentOrders(long customerId, String status) throws Exception;

    /**
     * Просмотр отчета по клиентам.
     * Позволяет просмотреть количество заказов, которые совершил данный клиент
     *
     * @param customerId значение customerId
     * @return количество заказов клиента
     * @throws NullPointerException когда входные переменные равны null
     */
    StringBuffer createCustomerReport(long customerId) throws Exception;

    /**
     * Включает в себя функции просмотра и управления мастерами, которые относятся к определенному салону
     *
     * @param salonId значение salonId
     * @return список мастеров
     * @throws NullPointerException когда входные переменные равны null
     */
    List<Master> changeTheLisOfMaster(long salonId) throws Exception;

    /**
     * Позволяет назначить услуги, которые будет предоставлять мастер
     * 
     * @param service значение service
     * @param masterId значение masterId
     * @return true или false
     * @throws NullPointerException когда входные переменные равны null
     */
    boolean assignService(List<Service> service, long masterId) throws Exception;

    /**
     * Создание отчета о работе мастера на данном сервисе.
     * Какие услуги предоставляет
     * 
     * @param masterId значение masterId
     * @return какие услуги предоставляет мастер
     * @throws NullPointerException когда входные переменные равны null
     */
    StringBuffer createMasterReport(long masterId, boolean needMasterReport) throws Exception;

}
