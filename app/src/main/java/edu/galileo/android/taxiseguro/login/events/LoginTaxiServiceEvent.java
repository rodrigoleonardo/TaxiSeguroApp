package edu.galileo.android.taxiseguro.login.events;

/**
 * Created by rodrigo on 13/06/16.
 */
public class LoginTaxiServiceEvent {
    //constantes para todos los casos de error o exito
    public final static int onSignInError = 0;
    public final static int onSignUpError = 1;
    public final static int onSignInSucces = 2;
    public final static int onSignUpSuccess = 3;
    public final static int onFailedToRecoverSession = 4;

    private int eventType;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
