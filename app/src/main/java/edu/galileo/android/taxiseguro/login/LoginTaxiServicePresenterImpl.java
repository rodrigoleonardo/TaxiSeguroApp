package edu.galileo.android.taxiseguro.login;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.taxiseguro.lib.EventBus;
import edu.galileo.android.taxiseguro.lib.GreenRobotEventBus;
import edu.galileo.android.taxiseguro.login.events.LoginTaxiServiceEvent;
import edu.galileo.android.taxiseguro.login.ui.LoginTaxiServiceView;

/**
 * Created by rodrigo on 12/06/16.
 */
public class LoginTaxiServicePresenterImpl implements LoginTaxiServicePresenter {
    private LoginTaxiServiceView loginTaxiServiceView;
    private LoginTaxiServiceInteractor loginTaxiServiceInteractor;
    private EventBus eventBus;

    public LoginTaxiServicePresenterImpl(LoginTaxiServiceView loginTaxiServiceView) {
        this.loginTaxiServiceView = loginTaxiServiceView;
        this.loginTaxiServiceInteractor = new LoginTaxiServiceInteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        //eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        /*Entonces, "onDestroy"
        lo que va a hacer es asignarle "null" a la vista de tal forma que no tenga un "memory leak".
        Nada más.*/
        loginTaxiServiceView = null;
        //eventBus.unregister(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void checkForAutenticatedUser() {
        /*El que va a revisar si está autenticado el usuario o no, requiere de la vista.
        Entonces, vamos a revisar de que esta vista exista, es decir, sea diferente de "null"
        y si acaso lo es, entonces vamos a llamar a los métodos que deshabilitan los "inputs"
        y además que muestran el "progress bar". Aquí lo que estoy haciendo es revisando si ya existe una sesión
        de un usuario autenticado.
        Y si tod0 ésto lo pude hacer,*/
        if(loginTaxiServiceView != null){
            loginTaxiServiceView.disableInput();
            loginTaxiServiceView.showProgress();
        }
        /*además voy a llamar al interactuador
        y aquí le voy a decir "check session".
        Con ésto estoy revisando la sesión del usuario.*/
        loginTaxiServiceInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if(loginTaxiServiceView != null){
            loginTaxiServiceView.disableInput();
            loginTaxiServiceView.showProgress();
        }
        loginTaxiServiceInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        if(loginTaxiServiceView != null){
            loginTaxiServiceView.disableInput();
            loginTaxiServiceView.showProgress();
        }
        loginTaxiServiceInteractor.doSignUp(email, password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginTaxiServiceEvent event) {
        switch(event.getEventType()){
            case LoginTaxiServiceEvent.onSignInSucces:
                onSignInSuccess();
                break;
            case LoginTaxiServiceEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginTaxiServiceEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginTaxiServiceEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginTaxiServiceEvent.onFailedToRecoverSession:
                //si la session no existe, hay que implementar
                onFailedToRecoverSession();
                break;
        }
    }
//para el caso de que no exista, una session activa se presenta el siguiente mensaje y proceso
    private void onFailedToRecoverSession(){
        if(loginTaxiServiceView != null){
            loginTaxiServiceView.hideProgress();
            loginTaxiServiceView.enableInputs();
        }
        //Log.e("LoginTaxiServicePresenterImpl", "onFailedToRecoverSession");
    }

    /*Eventualmente el presentador va a recibir información del interactuador
    y entonces va a ser necesario reportar a la vista
    cuando se tuvo éxito o no con tod0 lo que estoy tratando de hacer.
    Entonces, tengo dos casos de
    posibles eventos, "SignIn" y "SignUp".
    Y tengo para cada uno un caso de éxito y un caso de no éxito en donde hubo un error.
    Entoces, vamos a implementar métodos privados para ésto.*/
    private void onSignInSuccess(){
        if(loginTaxiServiceView != null){
            loginTaxiServiceView.navigateToMainScreen();
        }
    }

    private void onSignUpSuccess(){
        if(loginTaxiServiceView != null){
            loginTaxiServiceView.newUserSuccess();
        }
    }
    /*Para los mensajes de error*/
    private void onSignInError(String error){
        if(loginTaxiServiceView != null){
            loginTaxiServiceView.hideProgress();
            loginTaxiServiceView.enableInputs();
            loginTaxiServiceView.loginError(error);
        }
    }

    private void onSignUpError(String error){
        if(loginTaxiServiceView != null){
            loginTaxiServiceView.hideProgress();
            loginTaxiServiceView.enableInputs();
            loginTaxiServiceView.newUserError(error);
        }
    }
}
