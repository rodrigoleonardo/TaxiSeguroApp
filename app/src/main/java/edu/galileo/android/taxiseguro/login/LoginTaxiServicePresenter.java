package edu.galileo.android.taxiseguro.login;

import edu.galileo.android.taxiseguro.login.events.LoginTaxiServiceEvent;

/**
 * Created by rodrigo on 11/06/16.
 */
public interface LoginTaxiServicePresenter {
    /*Registro al presentador al bus para escuhar por eventos*/
    void onCreate();
    /*Recuerden que el presentador va a estar vinculado a la vista
    para poder acceder a todos estos métodos que describimos.
    Entonces, por lo mismo
    como la vista va a ser una actividad es posible que tenga un "memory leak"
    para evitarlo a la hora de destruir la vista
    voy a destruir también la variable que asigné al presentador.
    Entonces, vamos a asignar aquí un método "onDestroy" para realizar eso.
    Luego tengo tres acciones.
    Éstas son: revisar si el usuario ya fue autenticado,
    si existe
    una sesión abierta,
    luego revisar si es un
    inicio de sesión válido en base a "email" y a "password",
    o bien, registrar un nuevo usuario
    de la misma forma con "email" y "password".
    Listo.    */
    void onDestroy();
    void onPause();
    void onResume();
    void checkForAutenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);

    //Eso es tod0 lo que necesito en mi presentador. */
    /*Este m'etodo está
    asociado con nuestra librería, entonces, lo estoy agregando en la "interface" de tal
    forma que cuando ya no esté asociado con la librería, no tenga un problema y pueda mantener
    el mismo nombre. Y si lo quiero modificar, lo modifico desde la "interface"*/
    void onEventMainThread(LoginTaxiServiceEvent event);
}
