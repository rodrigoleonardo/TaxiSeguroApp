package edu.galileo.android.taxiseguro.login;

/**
 * Created by rodrigo on 11/06/16.
 *
 * Este es el inetractuador que va a estar trabajando los casos de uso
 */
public interface LoginTaxiServiceInteractor {
    void checkSession();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);
    //Vamos a crear repositorio nuevo
}