package com.example.p3b_tubes_2;

public interface LoginContract {
    void onSuccessLogin(String token);
    void onFailedLogin();

    interface View{
        void updateLoginView(boolean login);
    }
}
