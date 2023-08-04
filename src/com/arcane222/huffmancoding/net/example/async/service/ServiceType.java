package com.arcane222.huffmancoding.net.example.async.service;

import com.arcane222.huffmancoding.net.example.async.exception.OperationFailException;

public enum ServiceType {
    SERVER("SERVER"),
    CLIENT("CLIENT");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
