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

        interface DeleteOnSuccessListener{
            void onSuccessDelete(String hasil);
            void onErrorDelete();
        }

        interface AddParticipantsPertemuanOnSuccessListener{
            void onSuccessAddParticipants(String hasil);
            void onErrorAddParticipants();
        }

        interface DeleteParticipantsPertemuanOnSuccessListener{
            void onSuccessDeleteParticipants(String hasil);
            void onErrorDeleteParticipants();
        }

        void getPertemuanList();
    }

    interface View {
        void update(String id);
    }
}
