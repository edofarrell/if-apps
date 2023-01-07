package com.example.p3b_tubes_2.Model;

import java.util.List;

public class APIError {

    class Reason{
        String id;
    }

    String errcode;
    List<String> field;
    String message;
    Reason[] reason;

    public String getErrcode() {
        return errcode;
    }

    public List<String> getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
