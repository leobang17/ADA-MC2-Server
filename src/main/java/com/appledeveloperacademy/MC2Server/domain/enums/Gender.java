package com.appledeveloperacademy.MC2Server.domain.enums;

public enum Gender {
    MALE, FEMALE;

    public static Gender parseGender(String gender) {
        switch (gender) {
            case "MALE":
                return Gender.MALE;
            case  "FEMALE":
                return Gender.FEMALE;
            default:
                throw new RuntimeException("Nothing matches with gender");
        }
    }
}
