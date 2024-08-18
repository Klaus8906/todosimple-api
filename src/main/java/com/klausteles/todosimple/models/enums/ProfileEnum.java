package com.klausteles.todosimple.models.enums;

import java.util.Objects;

public enum ProfileEnum {
    
    ADMIN(1, "ROLE_ADMIN"),
    USER(1, "ROLE_USER");

    ProfileEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
    private Integer code;
    private String description;

    public Integer getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public static ProfileEnum toEnum(Integer code) {

        if (Objects.isNull(code)) {
            return null;
        }

        for (ProfileEnum x : ProfileEnum.values()) {
            if( code == x.getCode()) {
                return x;
            }
        }

        throw new IllegalArgumentException("Código inválido: "+ code);
    }
}
