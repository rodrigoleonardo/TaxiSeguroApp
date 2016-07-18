package edu.galileo.android.taxiseguro.login;

/**
 * Created by rodrigo on 11/06/16.
     Ésta es la interfaz que va a tener...
     "repository"
     que va a tener interacción con el "backend". En nuestro caso el "backend" es
     "Firebase", entonces ésta va a ser la única clase que va a estar enterada que estamos usando "Firebase".
     Y aquí vamos a tener un "email",
     y un "password" también.
     Luego, un "signIn".
     Noten que los métodos los voy a ir llamando en cascada
     y vamos a hacer también un "checkSesion".
     Ok.
     Con ésto tengo listas todas mis interfaces.
     Lo que corresponde ahora es crear las implementaciones
     de estas interfaces para poderlas utilizar.
     Esa va a ser nuestra siguiente parte de trabajo en el siguiente video.
 */
public interface LoginTaxiServiceRepository {
    void signUp(String email, String password);
    void signIn(String email, String password);
    void checkSession();
}