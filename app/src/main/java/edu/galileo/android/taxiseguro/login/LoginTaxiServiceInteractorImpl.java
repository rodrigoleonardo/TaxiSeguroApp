package edu.galileo.android.taxiseguro.login;

/**
 * Created by rodrigo on 13/06/16.
 * Implementaci'on de LoginTaxiServiceInteractor
 */
public class LoginTaxiServiceInteractorImpl implements LoginTaxiServiceInteractor {
    private LoginTaxiServiceRepository loginTaxiServiceRepository;

    public LoginTaxiServiceInteractorImpl() {
        /*instanciar al "LoginTaxiServiceRepository" con un "NewLoginRepositoryImplementation". Esto complica las cosas para el "testing".
        Más adelante vamos a resolver con inyección de dependencias. Por el momento, instanciémoslo
        de esta forma. De la misma manera, voy a dirigirme al presentador*/
        this.loginTaxiServiceRepository = new LoginTaxiServiceRepositoryImpl();
    }

    @Override
    public void checkSession() {
        loginTaxiServiceRepository.checkSession();
    }

    @Override
    public void doSignUp(String email, String password) {
        loginTaxiServiceRepository.signUp(email, password);
    }

    @Override
    public void doSignIn(String email, String password) {
        loginTaxiServiceRepository.signIn(email, password);
    }
}
