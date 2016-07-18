package edu.galileo.android.taxiseguro.login.ui;

/**
 * Created by rodrigo on 11/06/16.
 */
public interface LoginTaxiServiceView {
    void enableInputs();
    void disableInput();
    void showProgress();
    void hideProgress();

    void handleSignUp();
    void handleSignIn();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String error);
}
