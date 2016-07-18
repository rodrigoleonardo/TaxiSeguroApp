package edu.galileo.android.taxiseguro.addtaxiservice.ui;

/**
 * Created by rodrigo on 16/06/16.
 */
public interface AddTaxiServiceView {
    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();

    void contactAdded();
    void contactNotAdded();
}
