package com.appledeveloperacademy.MC2Server.repository;

public enum LogType {
    DIET,
    HEALTH,
    MEMO,
    SNACK,
    WATER;

    public String tableNameResolver() {
        switch (this) {
            case DIET:
                return "DietLog";
            case MEMO:
                return "MemoLog";
            case WATER:
                return "WaterLog";
            case HEALTH:
                return "HealthLog";
            case SNACK:
                return "SnackLog";
            default:
                return null;
        }
    }
}
