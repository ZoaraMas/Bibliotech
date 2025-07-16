package com.enums;

public enum TypeSpecial {
    DIMANCHE("dimanche"),
    FERRIE("ferrie");

    private final String value;

    TypeSpecial(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TypeSpecial fromValue(String value) {
        for (TypeSpecial etat : TypeSpecial.values()) {
            if (etat.value.equals(value)) {
                return etat;
            }
        }
        throw new IllegalArgumentException("Valeur d'état non valide : " + value);
    }
}
