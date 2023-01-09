package com.example.p3b_tubes_2;

public interface LoginContract {
    void onSuccessLogin();

    void onFailedLogin();

    interface View {
        void updateLoginView(boolean login);
    }
}
