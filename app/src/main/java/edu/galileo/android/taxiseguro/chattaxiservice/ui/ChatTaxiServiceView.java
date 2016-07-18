package edu.galileo.android.taxiseguro.chattaxiservice.ui;

import edu.galileo.android.taxiseguro.entities.ChatTaxiServiceMessage;

/**
 * Created by rodrigo on 18/06/16.
 */
public interface ChatTaxiServiceView {
    void onMessageReceived(ChatTaxiServiceMessage msg);
}
