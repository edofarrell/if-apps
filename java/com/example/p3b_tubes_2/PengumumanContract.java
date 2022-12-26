package com.example.p3b_tubes_2;

public interface PengumumanContract {

    interface Model{
        interface GetOnSuccessListener{
            void OnSuccessGet(PengumumanList pengumumanList);
            void OnErrorGet();
        }
    }

    interface View {
        void update(PengumumanList pengumumanList);
    }
}
