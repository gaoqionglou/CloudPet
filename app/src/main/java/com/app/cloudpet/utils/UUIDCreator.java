package com.app.cloudpet.utils;

import java.util.UUID;

public class UUIDCreator {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
