package com.example.p3b_tubes_2;

import java.util.ArrayList;

public interface FRSContract {

   interface Model{
       interface GetOnSuccessListener{
           void OnSuccessGet(TahunAjaran tahunAjar);
           void OnErrorGet();
       }

       interface GetDetailOnSuccessListener{
           void OnSuccessGetDetail(ArrayList<MataKuliahList.MataKuliah> listMataKuliah);
           void OnErrorGetDetail();
       }

       interface GetSearchMataKuliahOnSuccessListener{
           void OnSuccessGetSearchMataKuliah(ArrayList<MataKuliahList.MataKuliah> listMataKuliah);
           void OnErrorGetSearchMataKuliah();
       }
   }

    interface View{
        void update(TahunAjaran tahunAjaran);
        void openDetail(TahunAjaran.TahunAjar tahunAjar);
        void updateSearch(ArrayList<MataKuliahList.MataKuliah> listMataKuliah);
        void addToSelectedMataKuliah(MataKuliahList.MataKuliah matkul);
    }
}
