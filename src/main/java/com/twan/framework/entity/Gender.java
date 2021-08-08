package com.twan.framework.entity;

public enum Gender {
    MALE(1), FEMALE(2), NONIDENTIFIED(3);
 
    private int code;
     
    Gender(int code) {
        this.code = code;
    }
 
    public int getCode() {
        return code;
    }
}