package edu.galileo.android.taxiseguro.lib;

/**
 * Created by rodrigo on 13/06/
 *
 * Entonces, acompañando a esta "interface" "EventBus", vamos a hacer una clase "GreenRobotEventBus"
 que va a definir todas estas llamadas en base a la librería que acabo de implementar. Entonces,
 esta implementa "EventBus" y por lo tanto, requiero de sobrecargar los métodos, me interesan16.
 */
public class GreenRobotEventBus implements EventBus{
    //esta no es la clase que defin'i yo, es la clase real
    org.greenrobot.eventbus.EventBus eventBus;

    private static class SingletonHolder{
        private static final GreenRobotEventBus INSTANCE = new GreenRobotEventBus();
    }
    /*"GetInstance". Este método al ser estático, entonces me va a permitir obtener únicamente
    una instancia de esta clase que estoy escribiendo, "SingletonHolder.INSTANCE". Listo. De esta
    forma voy a tener únicamente una instancia en toda mi aplicación.*/
    public static GreenRobotEventBus getInstance(){
        return SingletonHolder.INSTANCE;
    }
    /* Ahora, para construirlo,
    vamos a hacer aquí un constructor, Lo que quiero es que aquí la inicialice como "EventBus" de "GreenRobot.getDefault".
    Entonces tengo el bus por defecto en el constructor a la hora de llamar a "GetINSTANCE", voy a
    obtener la instancia que me interesa, es decir, este constructor lo tengo que llamar una sola
    vez y esa llamada está aquí en el momento en que se asigna la instancia, noten que es
    final, con eso me garantizo que se asigna una sola vez*/
    public GreenRobotEventBus() {
        this.eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
    /*Esto pareciera, en primera instancia, ser un exceso de trabajo, pero
    con ésto estoy garantizándome que en el momento en que las cosas cambien de la librería o
    que por alguna razón quiera utilizar alguna otra, mi código va a sufrir cambios menores.
    Porque únicamente voy a tener qué venir a cambiar esta implementacion. La "interface" que define
    cómo trabajo "EventBus" se queda exactamente igual. Con ésto, yo ya tengo lista la librería
    de "EventBus" y puedo empezar a trabajarla.*/
}
