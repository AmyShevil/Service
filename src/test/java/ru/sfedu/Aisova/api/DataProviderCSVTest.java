package ru.sfedu.Aisova.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.Aisova.TestBase;
import ru.sfedu.Aisova.bean.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataProviderCSVTest extends TestBase {

    public DataProviderCSVTest(){

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    @Test
    void insertUserSuccess() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertUserSuccess");
        List<User> listUser = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        User user1 = createUser(1, "Name1");
        User user2 = createUser(2, "Name2");
        User user3 = createUser(3, "Name3");
        listUser.add(user1);
        listUser.add(user2);
        listUser.add(user3);
        instance.insertUser(listUser);
        assertEquals(user1, instance.getUserById(1));
    }

    @Test
    void insertUserFail() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        System.out.println("insertUserFail");
        List<User> listUser = new ArrayList<>();
        DataProviderCSV instance = new DataProviderCSV();
        User user1 = createUser(1, "Name1");
        User user2 = createUser(2, "Name2");
        User user3 = createUser(3, "Name3");
        listUser.add(user1);
        listUser.add(user2);
        listUser.add(user3);
        instance.insertUser(listUser);
        assertNull(instance.getUserById(4));
    }

    @Test
    public void testGetByIdUser() throws IOException {
        System.out.println("testGetByIdUser");
        DataProviderCSV instance = new DataProviderCSV();
        System.out.println(instance.getUserById(2));
    }
}