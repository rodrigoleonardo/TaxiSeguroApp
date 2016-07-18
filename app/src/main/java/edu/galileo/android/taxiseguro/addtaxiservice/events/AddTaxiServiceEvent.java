package edu.galileo.android.taxiseguro.addtaxiservice.events;

/**
 * Created by rodrigo on 16/06/16.
 */
public class AddTaxiServiceEvent {
    boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
