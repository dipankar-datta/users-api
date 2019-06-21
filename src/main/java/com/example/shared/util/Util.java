package com.example.shared.util;

import java.util.UUID;

public class Util {

    public static String generateUID() {
        return UUID.randomUUID().toString();
    }
}
