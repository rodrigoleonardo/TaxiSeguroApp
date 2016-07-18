package edu.galileo.android.taxiseguro.lib;

/**
 * Created by rodrigo on 13/06/16.
 *
 * Esta interface es con el objeto de:
 *
 *  La idea de construir
 esta "interface" es que no importa si yo quiero usar otra librería DIferente de "EventBus" en este caso, luego, o si por alguna
 razón cambia la implementación, voy a tener este envoltorio, el cual va a implementar
 mi "app" y entonces disminuyo la cantidad de problemas que van a ocurrir cuando la librería cambie.
 *
 */
public interface EventBus {
    /*"Con EventBus". Básicamente nos interesan tres cosas. Nos interesa que nos podamos registrar
    al bus a través de un objeto,*/
    void register(Object subscriber);
    /*nos interesa que nos podamos desregistrar o hacer el proceso
    inverso al registro, igual con un objeto que se llame Suscriptor, va a ser el presentador,
    pero como vamos a usar el presentador para "LogIn", para "ContactList" y todos los demas
    "features", entonces lo manejamos como un objeto.*/
    void unregister(Object subscriber);
    /*Y vamos a querer también algo que
    pueda publicar un evento. Luego en el presentador vamos a consumir ese evento. Por el momento
    nos interesa únicamente publicarlo*/
    void post(Object event);
}
