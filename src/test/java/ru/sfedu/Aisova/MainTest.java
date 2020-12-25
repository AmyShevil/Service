package ru.sfedu.Aisova;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class MainTest {

    @Test
    public void main() throws IOException {
        Main client = new Main();
        client.logBasicSystemInfo();
    }
}