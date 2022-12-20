package com.example.p3b_tubes_2;

public class MataKuliah {
    private String id;//uuidv4
    private String name;//length:1-256
    private String code;//length:5-15
    private int semester;//range:1-8

    public MataKuliah(String id, String name, String code, int semester){
        this.id = id;
        this.name = name;
        this.code = code;
        this.semester = semester;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getSemester() {
        return semester;
    }
}
