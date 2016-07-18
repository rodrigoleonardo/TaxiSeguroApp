package edu.galileo.android.taxiseguro.login;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

import edu.galileo.android.taxiseguro.domain.FireBaseHelper;
import edu.galileo.android.taxiseguro.entities.User;
import edu.galileo.android.taxiseguro.lib.EventBus;
import edu.galileo.android.taxiseguro.lib.GreenRobotEventBus;
import edu.galileo.android.taxiseguro.login.events.LoginTaxiServiceEvent;

/**
 * Created by rodrigo on 13/06/16.
 */
public class LoginTaxiServiceRepositoryImpl implements LoginTaxiServiceRepository {
    private FireBaseHelper helper;
    private Firebase dataReference;
    private Firebase myUserReference;

    public LoginTaxiServiceRepositoryImpl() {
        this.helper = FireBaseHelper.getInstance();
        this.dataReference = helper.getDataReference();
        /*Noten que inicialmente, la primera vez cuando yo no tengo una sesión ésto va a ser nulo
        porque no tengo un correo asociado. Eventualmente vamos a necesitar hacer otra asignación.
        Entonces, vamos a trabajar primero en el inicio de sesión, que es el método "signIn"*/
        this.myUserReference = helper.getMyUserReference();
    }

    @Override
    public void signUp(final String email, final String password) {
        //Log.e("LoginTaxiServiceRepositoryImpl", "signUp");
        dataReference.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>(){
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                postEvent(LoginTaxiServiceEvent.onSignUpSuccess);
                signIn(email, password);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                postEvent(LoginTaxiServiceEvent.onSignUpError, firebaseError.getMessage());
            }
        });
    }

    @Override
    public void signIn(String email, String password) {
        //Log.e("LoginTaxiServiceRepositoryImpl", "signIn");
        //postEvent(LoginTaxiServiceEvent.onSignInSucces);
        dataReference.authWithPassword(email, password, new Firebase.AuthResultHandler(){
            //en caso de que si se autentica
            @Override
            public void onAuthenticated(AuthData authData) {
                initSignIn();
                //postEvent(LoginTaxiServiceEvent.onSignInSucces);
            }
            //en caso de que no se autentica
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                postEvent(LoginTaxiServiceEvent.onSignInError, firebaseError.getMessage());
            }
        });
    }

    @Override
    public void checkSession() {
        if(dataReference.getAuth() != null){
            initSignIn();
        }else{
            postEvent(LoginTaxiServiceEvent.onFailedToRecoverSession);
        }
    }

    private void initSignIn(){
        /*¿Qué vamos a hacer si acaso sí hay una autenticación? Vamos a ir a traer la referencia del usuario
        sólo para garantizarnos de que es una referencia fresca. Es esto que tenemos aquí a la hora
        de iniciar sesión. La diferencia principal es que no tengo un usuario y contraseña,
        sino estoy utilizando la referencia y con esa referencia voy a ir a traer al usuario
        de la base de datos y si está creado, entonces, listo, eso es todo. Si no, entonces lo agrego.
        Como tengo aquí mi código repetido, vamos a hacer una función que nos permita encapsular
        eso*/
            myUserReference = helper.getMyUserReference();
            myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User currentUser = dataSnapshot.getValue(User.class);

                    if(currentUser == null){
                        registerNewUser();
                    }
                    helper.changeUserConnectionStatus(User.ONLINE);
                    postEvent(LoginTaxiServiceEvent.onSignInSucces);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
    }

    private void registerNewUser(){
        String email = helper.getAuthUserEmail();
        if(email != null){
            User currentUser = new User();
            currentUser.setEmail(email);
            myUserReference.setValue(currentUser);;
        }
    }


    //va conectado con LoginTaxiServiceEvent
    private void postEvent(int type, String errorMessage){
        //crear un evento nuevo
        LoginTaxiServiceEvent loginTaxiServiceEvent = new LoginTaxiServiceEvent();
        loginTaxiServiceEvent.setEventType(type);
        if(errorMessage != null){
            loginTaxiServiceEvent.setErrorMessage(errorMessage);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginTaxiServiceEvent);
    }
    //sino en cualquier caso se genere un mensaje de error
    private void postEvent(int type){
        postEvent(type, null);
    }

    /*Ahora me corresponde recibirlos estos m'etodos. Para recibirlos, voy a dirigirme al presentador
     y vamos a agregarle otro método. A éste le vamos a llamar "onEventMainThread"*/
}
