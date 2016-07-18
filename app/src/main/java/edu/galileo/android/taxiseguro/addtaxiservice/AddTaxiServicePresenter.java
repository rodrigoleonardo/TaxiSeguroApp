package edu.galileo.android.taxiseguro.addtaxiservice;

import edu.galileo.android.taxiseguro.addtaxiservice.events.AddTaxiServiceEvent;

/**
 * Created by rodrigo on 16/06/16.
 */
public interface AddTaxiServicePresenter {
    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddTaxiServiceEvent event);
}
