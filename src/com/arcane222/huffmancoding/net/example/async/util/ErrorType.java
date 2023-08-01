package com.arcane222.huffmancoding.net.example.async.util;

public enum ErrorType {
    CreateServerInstanceError("CreateServerInstanceError"),
    AcceptConnectionError("AcceptConnectionError"),
    AcceptTaskCompleteError("AcceptTaskCompleteError"),
    AcceptTaskFailError("AcceptTaskFailError"),
    StartServerError("StartServerError"),
    ReadDataError("ReadDataError"),
    TerminateServerError("TerminateServerError"),
    UnknownError("UnknownError");


    private final String error;

    ErrorType(String error) {
        this.error = error;
    }

    public String getValue() {
        return error;
    }
}
