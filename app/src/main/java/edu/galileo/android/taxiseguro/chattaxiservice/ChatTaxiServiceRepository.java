package edu.galileo.android.taxiseguro.chattaxiservice;

/**
 * Created by rodrigo on 18/06/16.
 */
public interface ChatTaxiServiceRepository {
    void sendMessage(String msg);
    void setRecipient(String recipient);

    void subscribe();
    void unsubscribe();
    void destroyListener();
    void changeConnectionStatus(boolean online);
}
