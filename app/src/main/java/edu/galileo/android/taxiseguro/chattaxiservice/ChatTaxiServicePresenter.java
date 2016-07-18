package edu.galileo.android.taxiseguro.chattaxiservice;

import edu.galileo.android.taxiseguro.chattaxiservice.events.ChatTaxiServiceEvent;

/**
 * Created by rodrigo on 18/06/16.
 */
public interface ChatTaxiServicePresenter {
    //registro de desregistro del listener y chat
    void onPause();
    void onResume();
    //Para trabajar co  EventBus
    void onCreate();
    //Para evitar elMemory Leak de la vista
    void onDestroy();
    //los mensajes van hacia un mismo destinatario
    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    //Para recivir eventos
    void onEventMainThread(ChatTaxiServiceEvent event);
}
