package com.example.p3b_tubes_2;


import com.example.p3b_tubes_2.Model.InviteList;
import com.example.p3b_tubes_2.Model.PertemuanList;
import com.example.p3b_tubes_2.Model.TimeslotList;
import com.example.p3b_tubes_2.Model.User;

public interface PertemuanContract {

    interface Model {

        interface AddOnSuccessListener {
            void onSuccessAdd(PertemuanList.Pertemuan pertemuan);

            void onErrorAdd(String msg);
        }

        interface ChangeOnSuccessListener {
            void onSuccessChange();

            void onErrorChange(String msg);
        }

        interface GetOnSuccessListener {
            void onSuccessGetDibuat(PertemuanList pertemuanList);

            void onErrorGetDibuat(String msg);
        }

        interface GetPartisipanSuccessListener {
            void onSuccessGetPartisipanDibuat(PertemuanList.Pertemuan pertemuan);

            void onErrorGetPartisipanDibuat(String msg);
        }

        interface DeleteOnSuccessListener {
            void onSuccessDelete();

            void onErrorDelete(String msg);
        }

        interface AddParticipantsPertemuanOnSuccessListener {
            void onSuccessAddParticipants(User[] users);

            void onErrorAddParticipants(String msg);
        }

        interface DeleteParticipantsPertemuanOnSuccessListener {
            void onSuccessDeleteParticipants();

            void onErrorDeleteParticipants(String msg);
        }

        interface GetTimeSlotOnSuccessListener {
            void onSuccessGetTimeSlot(TimeslotList timeslotList);

            void onErrorGetTimeSlot(String msg);
        }

        interface AddTimeSlotOnSuccessListener {
            void onSuccessAddTimeSlot();

            void onErrorAddTimeSlot(String msg);
        }

        interface GetInvitesOnSuccessListener {
            void onSuccessGetInvites(InviteList invites);

            void onErrorGetInvites(String msg);
        }

        interface ChangeInvitesOnSuccessListener {
            void onSuccessChangeInvites();

            void onErrorChangeInvites(String msg);
        }
    }

    interface View {
        interface PertemuanDibuat {
            void updatePertemuanDibuat(PertemuanList pertemuanList);

            void openDetailPertemuanDibuat(PertemuanList.Pertemuan pertemuan);

            void addSelectedUserOnTambahPertemuan(User[] users);

            void updateTimeSlot(TimeslotList timeslotList);

            void openAddPartisipan(String idPertemuan);

            void closeAddPage();

            void showErrorAddPertemuan(String msg);

            void showErrorAddTimeslot(String msg);
        }

        interface PertemuanDiundang {
            void updatePertemuanDiundang(InviteList listInvites);

            void openDetailPertemuanDiundang(InviteList.Invites invites);

            void closeDetail();

            void showErrorAcceptInvite(String msg);
        }

        void updateDibuat(PertemuanList pertemuanList);

        void updateDiundang(InviteList listInvites);

        void updateTimeSlot(TimeslotList timeslotList);
    }
}
