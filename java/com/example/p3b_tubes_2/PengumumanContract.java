package com.example.p3b_tubes_2;

import java.util.ArrayList;

public interface PengumumanContract {

    interface Model{
        interface GetOnSuccessListener{
            void OnSuccessGet();
            void OnErrorGet();
        }

        interface GetTagOnSuccessListener{
            void GetTagOnSuccess(ArrayList<TagList.Tag> listTag);
            void GetTagOnError();
        }
    }

    interface View {
        void updateListTag(ArrayList<TagList.Tag> listTag);
    }
}
