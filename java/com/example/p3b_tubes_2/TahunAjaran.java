package com.example.p3b_tubes_2;

import java.util.ArrayList;

public class TahunAjaran {
    private TahunAjar activeYear;
    private ArrayList<TahunAjar> listAcademicYears;

    public TahunAjaran(TahunAjar activeYear, ArrayList<TahunAjar> listAcademicYears){
        this.activeYear = activeYear;
        this.listAcademicYears = listAcademicYears;
    }

    public TahunAjar getActiveYear() {
        return activeYear;
    }

    public ArrayList<TahunAjar> getListAcademicYears() {
        return listAcademicYears;
    }

    public class TahunAjar{
        private String tahun;
        private String semester;

        public TahunAjar(String tahun, String semester){
            this.tahun = tahun;
            this.semester = semester;
        }
    }
}
