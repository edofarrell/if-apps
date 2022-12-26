package com.example.p3b_tubes_2;

import java.util.ArrayList;

public interface PertemuanContract {
    
    interface Model {

        interface AddOnSuccessListener {
            void onSuccessAdd(PertemuanList.Pertemuan pertemuan);
            void onErrorAdd();
        }

        interface ChangeOnSuccessListener{
            void onSuccessChange();
            void onErrorChange();
        }

        interface GetOnSuccessListener{
            void onSuccessGet(PertemuanList pertemuanList);
            void onErrorGet();
        }

        interface GetPartisipanSuccessListener{
            void onSuccessGetPartisipan(PertemuanList.Pertemuan pertemuan);
            void onErrorGetPartisipan();
        }

        void getPertemuanList();
    }

    interface View {
        void update(PertemuanList pertemuanList);
        void openDetail(PertemuanList.Pertemuan pertemuan);
    }
}
