package ru.sfedu.Aisova;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServiceClientTest {

    @Test
    public void main() throws IOException {
        ServiceClient client = new ServiceClient();
        client.logBasicSystemInfo();
        System.out.println("main");
        System.out.println(Constants.TEST_CONST);
        System.out.println("Good_test");
        //fail("bad");
    }
}