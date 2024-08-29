package com.dbs.repositories.contollers;

import java.util.Random;

public class TestCasesUtilities {
    public static int createRanNumber() {
        Random r = new Random();
        int low = 10;
        int high = 100000;
        return r.nextInt(high - low) + low;
    }

    public static String createURLWithPort(final int port, final String service) {
        return "http://localhost:" + port + "/api/v1/" + service;
    }

}
