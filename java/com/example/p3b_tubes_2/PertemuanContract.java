package com.example.p3b_tubes_2;


import java.util.List;

public interface PertemuanContract {

    interface Model {

        interface AddOnSuccessListener {
            void onSuccessAdd(PertemuanList.Pertemuan pertemuan);

            void onErrorAdd();
        }

        interface ChangeOnSuccessListener {
            void onSuccessChange();

            void onErrorChange();
        }

        interface GetOnSuccessListener {
            void onSuccessGetDibuat(PertemuanList pertemuanList);

            void onErrorGetDibuat();
        }

        interface GetPartisipanSuccessListener {
            void onSuccessGetPartisipanDibuat(PertemuanList.Pertemuan pertemuan);

            void onErrorGetPartisipanDibuat();
        }

        interface DeleteOnSuccessListener {
            void onSuccessDelete();

            void onErrorDelete();
        }

        interface AddParticipantsPertemuanOnSuccessListener {
            void onSuccessAddParticipants(User[] users);

            void onErrorAddParticipants();
        }

        interface DeleteParticipantsPertemuanOnSuccessListener {
            void onSuccessDeleteParticipants();

            void onErrorDeleteParticipants();
        }

        interface GetTimeSlotOnSuccessListener {
            void onSuccessGetTimeSlot(List<TimeSlot> timeSlot);

            void onErrorGetTimeSlot();
        }

        interface AddTimeSlotOnSuccessListener {
            void onSuccessAddTimeSlot(List<TimeSlot> timeSlot);

            void onErrorAddTimeSlot();
        }

        interface GetInvitesOnSuccessListener{
            void onSuccessGetInvites(APIPertemuanGetInvites invites);
            void onErrorGetInvites();
        }
    }

    interface View {
        interface PertemuanDibuat {
            void updatePertemuanDibuat(PertemuanList pertemuanList);

            void openDetailPertemuanDibuat(PertemuanList.Pertemuan pertemuan);

            void addSelectedUserOnTambahPertemuan(User[] users);

            void updateTimeSlot(List<TimeSlot> timeSlot);

            void openAddPartisipan(String idPertemuan);
        }

        interface PertemuanDiundang {
            void updatePertemuanDiundang(APIPertemuanGetInvites listInvites);

            void openDetailPertemuanDiundang(APIPertemuanGetInvites.Invites invites);
        }

        void updateDibuat(PertemuanList pertemuanList);

        void updateDiundang(APIPertemuanGetInvites listInvites);

        void updateTimeSlot(List<TimeSlot> timeSlot);
    }
}
