package edu.galileo.android.taxiseguro.contacttaxiservicelist;

import edu.galileo.android.taxiseguro.contacttaxiservicelist.events.ContactTaxiServiceListEvent;

/**
 * Created by rodrigo on 14/06/16.
 * el
 presentador va a tener varios eventos ante los que va a reaccionar. Va a tener un evento
 "OnCreate", un evento "OnDestroy" para hacer cierta configuración, pero además, como
 voy a estar trabajando con "Firebase" y va a tener una conexión en tiempo real, no
 voy a querer esa conexión abierta siempre, entonces vamos a crear un evento de pausa
 y un evento de resumen y vamos a ponerlos arriba para que esté un poco más ordenado.
 Luego vamos a tener una acción de cerrar sesión, vamos a tener una acción de borrar
 un contacto y le vamos a enviar sólo el "email" del contacto que estamos borrando, vamos a
 tener nuestro método para manejar los eventos recibidos, en este caso es un "ContactTaxiServiceListEvent"
 que todavía no existe, vamos a crearlo y dejarlo vacío. Debe haberlo creado de una
 vez en un paquete propio pero por el momento, no me interesa. Y bueno, es posible que yo
 necesite en algún momento obtener la dirección de correo del usuario que tiene una sesión iniciada
 en concreto, lo voy a querer para ponerlo aquí, entonces vamos a poner un método "getCurrentUserEmail"
 y listo.
 */
public interface ContactTaxiServiceListPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void onEventMainThread(ContactTaxiServiceListEvent event);
}
