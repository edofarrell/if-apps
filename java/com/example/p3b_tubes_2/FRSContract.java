package com.example.p3b_tubes_2;

import java.util.ArrayList;

public interface FRSContract {

   interface Model{
       interface GetOnSuccessListener{
           void OnSuccessGet(TahunAjaran tahunAjar);
           void OnErrorGet();
       }

       interface GetDetailOnSuccessListener{
           void OnSuccessGetDetail(ArrayList<MataKuliahList.MataKuliah> listMataKuliah, String tahunAjar);
           void OnErrorGetDetail();
       }
   }

    interface View{
        void update(TahunAjaran tahunAjaran);
        void openDetail(String tahunAjar,ArrayList<MataKuliahList.MataKuliah> listMataKuliah);
    }
}
