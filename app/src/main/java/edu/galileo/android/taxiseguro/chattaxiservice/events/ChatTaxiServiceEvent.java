package edu.galileo.android.taxiseguro.chattaxiservice.events;

import edu.galileo.android.taxiseguro.entities.ChatTaxiServiceMessage;

/**
 * Created by rodrigo on 18/06/16.
 */
public class ChatTaxiServiceEvent {
    ChatTaxiServiceMessage message;

    public ChatTaxiServiceMessage getMesssage() {
        return  message;
    }

    public void setMessage(ChatTaxiServiceMessage message) {
        this.message = message;
    }
}
