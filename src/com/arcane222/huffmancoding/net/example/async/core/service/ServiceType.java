package com.arcane222.huffmancoding.net.example.async.core.service;

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
