package com.example.p3b_tubes_2;

import com.example.p3b_tubes_2.Model.MataKuliahList;
import com.example.p3b_tubes_2.Model.TahunAjaran;

import java.util.ArrayList;

public interface FRSContract {

    interface Model {
        interface GetOnSuccessListener {
            void OnSuccessGet(TahunAjaran tahunAjar);

            void OnErrorGet();
        }

        interface GetDetailOnSuccessListener {
            void OnSuccessGetDetail(ArrayList<MataKuliahList.MataKuliah> listMataKuliah);

            void OnErrorGetDetail();
        }

        interface GetSearchMataKuliahOnSuccessListener {
            void OnSuccessGetSearchMataKuliah(ArrayList<MataKuliahList.MataKuliah> listMataKuliah);

            void OnErrorGetSearchMataKuliah();
        }

        interface GetMataKuliahEnrolmentOnSuccessListener {
            void OnSuccessGetMataKuliahEnrolment(ArrayList<MataKuliahList.MataKuliah> listNamaMatkul);

            void OnErrorGetMataKuliahEnrolment();
        }

        interface EnrolStudentOnSuccessListener {
            void OnSuccessEnrolStudent();

            void OnErrorEnrolStudent(String nama, String kode);
        }
    }

    interface View {
        void update(TahunAjaran tahunAjaran);

        void openDetail(TahunAjaran.TahunAjar tahunAjar);

        void updateSearch(ArrayList<MataKuliahList.MataKuliah> listMataKuliah);

        void addToSelectedMataKuliah(MataKuliahList.MataKuliah matkul);

        void updateMataKuliahEnrolment(ArrayList<MataKuliahList.MataKuliah> listNamaMatkul);

        void showErrorMataKuliahEnrol(String nama, String kode);

        void showSuccessMataKuliahEnrol();
    }
}
