package edu.galileo.android.taxiseguro.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.taxiseguro.R;
import edu.galileo.android.taxiseguro.contacttaxiservicelist.ui.ContactTaxiServiceListActivity;
import edu.galileo.android.taxiseguro.login.LoginTaxiServicePresenter;
import edu.galileo.android.taxiseguro.login.LoginTaxiServicePresenterImpl;
import edu.galileo.android.taxiseguro.signup.ui.SignUpTaxiServiceActivity;

public class LoginTaxiServiceActivity extends AppCompatActivity implements LoginTaxiServiceView {
    @Bind(R.id.editTxtEmail)
    EditText inputEmail;
    @Bind(R.id.editTxtPassword)
    EditText inputPassword;
    @Bind(R.id.btnSignin)
    Button btnSignIn;
    @Bind(R.id.btnSignup)
    Button btnSignUp;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layoutMainContainer)
    RelativeLayout container;
    //presentador, hay que inicializarlo en una clase que lo implemente
    private LoginTaxiServicePresenter loginTaxiServicePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        /*Por último, la vista ahora sí me permite
        inicializar el presentador con un "NewLoginPresenterImplementation" y éste sí recibe un parámetro. Recibe la
        vista. Entonces le envío "this". Con ésto, estoy completando las llamadas en cascada
        y ahora puedo proceder a probarlo. */
        loginTaxiServicePresenter = new LoginTaxiServicePresenterImpl(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        /*lo que tendría qué hacer aquí es revisar al inicio si ya
        tiene un usuario autenticado. Esto lo que va a provocar es que desde el inicio estén
        mostrándose el diálogo del "progress bar" y eventualmente, pues para resolverlo, tengo
        qué implementar estos métodos en el repositorio. Con ésto tenemos lista nuestra estructura
        y pasamos ahora a implementar en el repositorio, tod0 lo necesario.
    */
        loginTaxiServicePresenter.onResume();
        //Aqui inicia con el progressbar activo
        loginTaxiServicePresenter.checkForAutenticatedUser();
    }

    @Override
    protected void onPause() {
        loginTaxiServicePresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        loginTaxiServicePresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInput() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
    @OnClick(R.id.btnSignup)
    @Override
    public void handleSignUp() {
        startActivity(new Intent(this, SignUpTaxiServiceActivity.class));
    }
    @OnClick(R.id.btnSignin)
    @Override
    public void handleSignIn() {
        loginTaxiServicePresenter.validateLogin(inputEmail.getText().toString(),
                                    inputPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        Intent intent = new Intent(this, ContactTaxiServiceListActivity.class);
        //flag para borra del historial y q ue muestre la pantalla principal
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        inputPassword.setError(msgError);
    }

    @Override
    public void newUserSuccess() {
        //creamos el elemento mensaje de el boton signup
        //Snackbar.make(container, R.string.login_notice_message_signup, Snackbar.LENGTH_SHORT).show();
        throw new UnsupportedOperationException("Operation is not valid in loginActivity");
    }

    @Override
    public void newUserError(String error) {
        /*inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup), error);
        inputPassword.setError(msgError);*/
        throw new UnsupportedOperationException("Operation is not valid in loginActivity");
    }
    private void setInputs(boolean enabled){
        inputEmail.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        btnSignIn.setEnabled(enabled);
        btnSignUp.setEnabled(enabled);
    }
    /*@OnClick(R.id.btnSignin)
    public void handleSignin() {
        Log.e("AndroidChat", inputEmail.getText().toString());
    }
    @OnClick(R.id.btnSignup)
    public void handleSignup() {
    }*/
}
