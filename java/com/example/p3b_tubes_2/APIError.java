package com.example.p3b_tubes_2;

import java.util.List;

public class APIError {
    String errcode;
    List<String> field;
    String message;
    List<String> reason;

    public String getErrcode() {
        return errcode;
    }

    public List<String> getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getReason() {
        return reason;
    }
}
