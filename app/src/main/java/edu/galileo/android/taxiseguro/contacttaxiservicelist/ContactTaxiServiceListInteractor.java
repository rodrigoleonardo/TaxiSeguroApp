package edu.galileo.android.taxiseguro.contacttaxiservicelist;

/**
 * Created by rodrigo on 14/06/16.
 */
public interface ContactTaxiServiceListInteractor {
    /*y este interactuador va a tener básicamente
        dos acciones "subscribe" y "unsubscribe" para eventos de los contactos. Además, en algún
        caso es posible que yo quiera destruir el "listener" y vamos a agregarle un método
        más para borrar los contactos. Todos éstos tienen qué ver con los eventos,
        tod0 está relacionado con la lista de contactos. Sin embargo, además de eso, yo necesito acciones
        para la sesión, entonces vamos a separarlo y aquí es donde comentaba que es uno de los
        casos donde podemos tener un poco de debate sobre qué va en cada clase, */
    void subscribe();
    void unsubscribe();
    void destroyListener();
    void removeContact(String email);
}
