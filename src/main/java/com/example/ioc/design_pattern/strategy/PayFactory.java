package com.example.ioc.design_pattern.strategy;

import java.util.HashMap;
import java.util.Map;

public class PayFactory {
    private static final Map<Scene, PayFacade> PAY_FACADE = new HashMap<>();

    public static void register(Scene scene, PayFacade payFacade) {
        PAY_FACADE.put(scene, payFacade);
    }

    public static PayFacade get(Scene scene) {
        return PAY_FACADE.get(scene);
    }
}