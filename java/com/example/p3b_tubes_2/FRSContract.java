package com.example.p3b_tubes_2;

import java.util.ArrayList;

public interface FRSContract {

   interface Model{
       interface GetOnSuccessListener{
           void OnSuccessGet(ArrayList<TahunAjaran.TahunAjar> tahunAjar);
           void OnErrorGet();
       }
   }

    interface View{
        void update(ArrayList<TahunAjaran.TahunAjar> tahunAjaran);
    }
}
