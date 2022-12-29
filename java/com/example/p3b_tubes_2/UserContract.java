package com.example.p3b_tubes_2;

import java.util.List;

public interface UserContract {

    interface Model {
        interface GetOnSucessListener {
            void onSuccessGet(List<User> data);
            void onErrorGet();
        }
    }

    interface View {
        void update(List<User> data);
    }
}
