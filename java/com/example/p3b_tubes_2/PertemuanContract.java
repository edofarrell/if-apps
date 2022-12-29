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

        interface GetOnSuccessListener{
            void onSuccessGetDibuat(PertemuanList pertemuanList);
            void onErrorGetDibuat();
        }

        interface GetPartisipanSuccessListener {
            void onSuccessGetPartisipanDibuat(PertemuanList.Pertemuan pertemuan);
            void onErrorGetPartisipanDibuat();
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
        interface PertemuanDibuat{
            void updatePertemuanDibuat(PertemuanList pertemuanList);
            void openDetailPertemuanDibuat(PertemuanList.Pertemuan pertemuan);
        }

        interface PertemuanDiundang{
            void updatePertemuanDiundang(PertemuanList pertemuanList);
            void openDetailPertemuanDiundang(PertemuanList.Pertemuan pertemuan);
        }

       void update(PertemuanList pertemuanList);
    }
}
