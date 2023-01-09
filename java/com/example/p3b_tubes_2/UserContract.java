package com.example.p3b_tubes_2;

import com.example.p3b_tubes_2.Model.User;

import java.util.List;

public interface UserContract {

    interface Model {
        interface GetOnSucessListener {
            void onSuccessGet(List<User> data);

            void onErrorGet(String msg);
        }

        interface GetOnGetCurrentUserListener {
            void onSuccessGetCurrentUser(User user);

            void onErrorGetCurrentUser(String msg);
        }
    }

    interface View {
        interface Profile {
            void updateProfile(User user);
        }

        void update(List<User> data);

        void selectUser(User user);
    }
}
