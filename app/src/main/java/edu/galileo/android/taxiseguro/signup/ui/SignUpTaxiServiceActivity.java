package edu.galileo.android.taxiseguro.signup.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import edu.galileo.android.taxiseguro.login.ui.LoginTaxiServiceView;

public class SignUpTaxiServiceActivity extends AppCompatActivity implements LoginTaxiServiceView {
    @Bind(R.id.editTxtEmail)
    EditText inputEmail;
    @Bind(R.id.editTxtPassword)
    EditText inputPassword;
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
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setTitle(R.string.signup_title);
        loginTaxiServicePresenter = new LoginTaxiServicePresenterImpl(this);
        loginTaxiServicePresenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        loginTaxiServicePresenter.onDestroy();
        super.onDestroy();
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
    }

    @Override
    protected void onPause() {
        loginTaxiServicePresenter.onPause();
        super.onPause();
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
        loginTaxiServicePresenter.registerNewUser(inputEmail.getText().toString(),
                                        inputPassword.getText().toString());
    }

    @Override
    public void handleSignIn() {
        /*try{
            loginTaxiServicePresenter.validateLogin(inputEmail.getText().toString(),
                    inputPassword.getText().toString());
        }catch(UnsupportedOperationException uex){
            System.out.println("Operation is not valid in SignUpTaxiServiceActivity");
        }*/

        throw new UnsupportedOperationException("Operation is not valid in SignUpTaxiServiceActivity");

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
        throw new UnsupportedOperationException("Operation is not valid in SignUpTaxiServiceActivity");
    }

    @Override
    public void newUserSuccess() {
        //creamos el elemento mensaje de el boton signup
        Snackbar.make(container, R.string.login_notice_message_signup, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup), error);
        inputPassword.setError(msgError);
    }
    private void setInputs(boolean enabled){
        inputEmail.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        btnSignUp.setEnabled(enabled);
    }
}
