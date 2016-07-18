package edu.galileo.android.taxiseguro.chattaxiservice;

/**
 * Created by rodrigo on 18/06/16.
 *
 * Esta clase se encarga de los mensajes y trabajan directamente con el listener
 */
public interface ChatTaxiServiceInteractor {
    void sendMessage(String msg);
    void setRecipient(String recipient);

    void subscribe();
    void unsubscribe();
    void destroyListener();
}
