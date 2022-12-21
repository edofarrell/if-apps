package com.example.p3b_tubes_2;

import java.util.ArrayList;

public class PengumumanPresenter {

    private PengumumanContract.View ui;
    private MainPresenter mainPresenter;

    public PengumumanPresenter(PengumumanContract.View ui, MainPresenter mainPresenter) {
        this.ui = ui;
        this.mainPresenter = mainPresenter;
    }
}
