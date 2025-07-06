package com.enums;

public enum EtatReservationEnum {
    VALIDEE("VALIDEE"),
    REFUSEE("REFUSEE");

    private final String value;

    EtatReservationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EtatReservationEnum fromValue(String value) {
        for (EtatReservationEnum etat : EtatReservationEnum.values()) {
            if (etat.value.equals(value)) {
                return etat;
            }
        }
        throw new IllegalArgumentException("Valeur d'état non valide : " + value);
    }
}
