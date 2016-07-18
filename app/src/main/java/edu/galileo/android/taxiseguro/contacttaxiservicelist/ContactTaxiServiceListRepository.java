package edu.galileo.android.taxiseguro.contacttaxiservicelist;

/**
 * Created by rodrigo on 14/06/16.
 * Entonces, con esto termino los contratos o lo mínimo que deberían implementar
 las clases, esa es mi expectativa. El siguiente paso es ya implementarlos en cosas concretas.
 Tod0 esto es la parte abstracta. Vamos a agregar, para terminar aquí un poco de la estructura
 */
public interface ContactTaxiServiceListRepository {
    void signOff();
    String getCurrentUserEmail();
    void destroyListener();
    void removeContact(String email);
    void subscribeToContactListEvent();
    void unsubscribeToContactListEvent();
    void changeConnectionStatus(boolean online);
}
