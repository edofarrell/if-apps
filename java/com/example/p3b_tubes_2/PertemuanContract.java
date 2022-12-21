package com.example.p3b_tubes_2;

public interface PertemuanContract {
    
    interface Model {

        interface OnFinishedListener {
            void onFinished(String id);

            void onError();
        }

        void getPertemuanList();
    }

    interface View {
        void update(String id);
    }
}
