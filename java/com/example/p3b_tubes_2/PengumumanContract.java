package com.example.p3b_tubes_2;

import java.util.ArrayList;

public interface PengumumanContract {

    interface Model {
        interface GetOnSuccessListener {
            void OnSuccessGet(PengumumanList pengumumanList);

            void OnErrorGet(String msg);
        }

        interface GetTagOnSuccessListener {
            void GetTagOnSuccess(ArrayList<TagList.Tag> listTag);

            void GetTagOnError(String msg);
        }

        interface GetDetailOnSuccessListener {
            void GetDetailOnSuccess(PengumumanList.Pengumuman pengumuman);

            void GetDetailOnError(String msg);
        }

        interface AddOnSuccessListener {
            void AddOnSuccess(PengumumanList.Pengumuman pengumuman);

            void AddOnError(String msg);
        }

        interface DeleteOnSuccessListener {
            void deleteOnSuccess(PengumumanList.Pengumuman deleted);

            void deleteOnError(String msg);
        }

        interface AddTagOnSuccessListener {
            void AddTagOnSuccess();

            void AddTagOnError(String msg);
        }
    }

    interface View {
        void updatePengumumanList(PengumumanList pengumumanList);

        void updateListTag(ArrayList<TagList.Tag> listTag);

        void openDetail(PengumumanList.Pengumuman pengumuman);

        void closeAddPage();

        void showErrorAddPengumuman(String msg);
    }
}
