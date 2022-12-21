package com.example.p3b_tubes_2;

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

        void getPertemuanList();
    }

    interface View {
        void update(String id);
    }
}
