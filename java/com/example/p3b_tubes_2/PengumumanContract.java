package com.example.p3b_tubes_2;

import java.util.ArrayList;

public interface PengumumanContract {

    interface Model{
        interface GetOnSuccessListener{
            void OnSuccessGet(PengumumanList pengumumanList);
            void OnErrorGet();
        }

        interface GetTagOnSuccessListener{
            void GetTagOnSuccess(ArrayList<TagList.Tag> listTag);
            void GetTagOnError();
        }

        interface GetDetailOnSuccessListener{
            void GetDetailOnSuccess(PengumumanList.Pengumuman pengumuman);
            void GetDetailOnError();
        }
    }

    interface View {
        void update(PengumumanList pengumumanList);
        void updateListTag(ArrayList<TagList.Tag> listTag);
        void openDetail(PengumumanList.Pengumuman pengumuman);
    }
}
