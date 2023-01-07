package com.example.p3b_tubes_2;


import com.example.p3b_tubes_2.Model.InviteList;
import com.example.p3b_tubes_2.Model.PertemuanList;
import com.example.p3b_tubes_2.Model.TimeSlot;
import com.example.p3b_tubes_2.Model.User;

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
            void onSuccessGetInvites(InviteList invites);
            void onErrorGetInvites();
        }

        interface ChangeInvitesOnSuccessListener{
            void onSuccessChangeInvites();
            void onErrorChangeInvites();
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
            void updatePertemuanDiundang(InviteList listInvites);

            void openDetailPertemuanDiundang(InviteList.Invites invites);

            void closeDetail();
        }

        void updateDibuat(PertemuanList pertemuanList);

        void updateDiundang(InviteList listInvites);

        void updateTimeSlot(List<TimeSlot> timeSlot);
    }
}
