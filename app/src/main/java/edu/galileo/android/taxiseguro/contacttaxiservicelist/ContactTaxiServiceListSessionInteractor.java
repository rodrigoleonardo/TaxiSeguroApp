package edu.galileo.android.taxiseguro.contacttaxiservicelist;

/**
 * Created by rodrigo on 14/06/16.
 * aquí es donde comentaba que es uno de los
 casos donde podemos tener un poco de debate sobre qué va en cada clase, reconozco que
 esta es una interfaz y entonces yo voy a separarlo de esta forma; voy a poner aquí los métodos
 para manejar la sesión "SignOff" "getCurrentUserEmail" y "ChangeConnectionStatus" indicando cómo
 va a cambiar si es "online" o no y voy a dejar otro interactuador para los eventos de la
 lista
 */
public interface ContactTaxiServiceListSessionInteractor {
    void signOff();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean online);
}