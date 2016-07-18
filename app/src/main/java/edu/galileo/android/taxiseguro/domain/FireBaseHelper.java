package edu.galileo.android.taxiseguro.domain;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import edu.galileo.android.taxiseguro.entities.User;

/**
 * Created by rodrigo on 11/06/16.
 * Esta clase es con el proposito de
 * La idea es que aquí esté centralizada toda la lógica necesaria para "Firebase".
 */
public class FireBaseHelper {
    private Firebase dataReference;
    private final static String SEPARATOR = "___";
    private final static String CHATS_PATH = "chats";
    private final static String USERS_PATH = "users";
    private final static String CONTACTS_PATH = "contacts";
    //Aqui colocar la referencia hacia Firebase, ejm: https://mibdd.firebaseio.com/
    private final static String FIREBASE_URL = "";

    private static class SingletonHolder {
        private static final FireBaseHelper INSTANCE = new FireBaseHelper();
    }
    public static FireBaseHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }
    public FireBaseHelper(){
        this.dataReference = new Firebase(FIREBASE_URL);
    }
    public Firebase getDataReference(){
        return dataReference;
    }
    public String getAuthUserEmail(){
        AuthData authData = dataReference.getAuth();
        String email = null;
        if(authData != null){
            Map<String, Object> providerData = authData.getProviderData();
            email = providerData.get("email").toString();
        }
        return email;
    }

    //Este es el standar para leer y escribir a los usuarios
    public Firebase getUserReference(String email){
        Firebase userReference = null;
        if(email != null){
            /*Firebase" no permite varios caracteres específicamente en la ruta.
            Uno de ellos es el punto. Entonces, vamos a agregar aquí otra variable que se llame "email Key",
            que lo que va a hacer es reemplazar de la variable "email"
            el punto, y lo vamos a reemplazar por un "underscore".*/
           String emailKey = email.replace(".", "_");
           userReference = dataReference.getRoot().child(USERS_PATH).child(emailKey);
        }
        return userReference;
    }
    /*Es posible que en algún momento yo quiera acceder a la referencia de mi usuario.*/
    public Firebase getMyUserReference(){
        return getUserReference(getAuthUserEmail());
    }
    /*De la misma forma vamos a escribir un método para obtener los contactos
    y obtener mis contactos.
    Esos serían los métodos, noten que es lo mismo.
    Y tengo éste otro método para obtener la referencia de un contacto,
    a partir del correo del usuario y del correo del contacto.
    Por eso tengo primero la referencia del correo principal y luego tengo un "child" de los contactos
    y del correo secundario.*/
    public Firebase getContactsReference(String email){
        return getUserReference(email).child(CONTACTS_PATH);
    }

    public Firebase getMyContactsReference(){
        return getContactsReference(getAuthUserEmail());
    }

    public Firebase getOneContactReference(String mainEmail, String childEmail){
        String childKey = childEmail.replace(".", "_");
        //primero Obtengo una referencia del correo principal y luego del correo secundario
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(childKey);
    }

    /*La referencia para los chats es un poco más compleja porque necesitamos un separador.
    * */
    public Firebase getChatsReference(String receiver){
        String keySender = getAuthUserEmail().replace(".", "_");
        String keyReceiver = receiver.replace(".", "_");

        String keyChat = keySender + SEPARATOR + keyReceiver;
        /*Y además del separador necesito comparar para tenerlo por orden alfabético.*/
        if(keySender.compareTo(keyReceiver) > 0){
            keyChat = keyReceiver + SEPARATOR + keySender;
        }
        return dataReference.getRoot().child(CHATS_PATH).child(keyChat);
    }
    /*Con ésto tengo los métodos necesarios para obtener las referencias de "Firebase".
    Vamos a agregar un par de métodos más para ayudarnos cuando desarrollemos el app.
    Uno de ellos es cambiar el estatus de la conexión de un usuario.
    Voy a recibir un "booleano" que me dice si está "online" u "offline" y voy a verificar que tenga una referencia
    a mi usuario válida. Entonces que la llamada de este método sea diferente de "null".
    Si es así, entonces de nuevo voy a definir un mapa muy similar a como lo hice para obtener el "email",
    con "string", "object" y le vamos a llamar a este "updates". Lo instanciamos como un "hashMap",
    que tiene un "string" y un "object",
    y aquí le vamos a colocar
    "updates", "punto", "put"; como mi "key" es "online"
    coloco ese "string" y luego le coloco el "booleano" que recibí.
    Ahora voy a buscar la referencia a mi usuario
    y la actualizo con este objeto que acabo de construir.
    Una vez tengo eso listo ya estoy en un estatus diferente.
    Pero cuando mi estatus cambie tengo que también notificarle a mis contactos. Entonces vamos a hacer
    otro método que se llame "notifyContactsOfConnectionChange",
    y éste va a recibir un "booleno" también.
    El mismo que recibí. Este método todavía no existe, lo vamos a crear. Está aquí abajo.
    Y en realidad este método me va a servir para cambiar el estatus
    cuando lo necesite pero también para cerrar sesión.
    Entonces vamos a hacer otro más
    que reciba dos "booleanos".
    Un "boolenano" si estoy cerrando sesión y éste es el método que voy a implementar.
    Entonces, cuando únicamente tenga uno
    vamos a llamar,
    cuando tenga únicamente un parámetro vamos a llamar al
    método que tiene dos pero le voy a mandar "false" porque no va a estar cerrando sesión.
    Y este método debería ser público, para que pueda hacer uso de él.
    Entonces, así como tengo éste método; vamos a hacer otro método para cerrar sesión,*/
    public void changeUserConnectionStatus(boolean online){
        if(getMyUserReference() != null){
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("online", online);
            getMyUserReference().updateChildren(updates);
            notifyContactsOfConnectionChange(online);
        }
    }
    public void notifyContactsOfConnectionChange(boolean online){
        notifyContactsOfConnectionChange(online, false);
    }
    public void signOff(){
        /*Ya sé que me voy a ir "offline", entonces le voy a mandar "false" y le voy a mandar "true"*/
        notifyContactsOfConnectionChange(User.OFFLINE, true);
    }
    /*notificar a mis contactos que estoy fuera de linea
    *
    * Lo que voy a hacer es voy a empezar obteniendo mi correo
        a través de una llamada "getAuthUserEmail"
        y voy a obtener mi referencia de los contactos,
        para obtener todos los contactos que tengo. Entonces, le agrego un "listener" para un solo evento
        y voy a instanciar un "valueEventListener".
        Éste tiene dos métodos,
        "onCancelled" no me interesa mucho en este momento, voy a trabajar únicamente "onDataChange",
        y aquí como recibo un "snapshot" voy a hacer un ciclo que recorra todos los contactos.
        Entonces, vamos a hacer un "child", "snapshot", "punto", "getChildren"
        y por cada contacto lo que voy a hacer es,
        voy a verificar cual es el "email"
        y hacemos un "child", "punto", "getKey".
        Recuerden el modelo de datos que tenemos definido. Defino también una referencia.
        Y lo voy a hacer a través de la llamada a "getOneContactReference",
        le tengo que enviar dos "emails".
        El "email" del usuario que está en mis contactos y mi correo.
        De esta forma voy a lograr avisarle a mis contactos que yo estoy "offline".
        Entonces, con eso listo hago "reference",
        "punto", "setValue".
        Y le vamos a avisar
        que estoy "online" o que estoy "offline".
        Si acaso estoy cerrando sesión, después de que le avisé a todos de que ya cerré sesión,
        entonces, voy a hacer un "dataReference", "punto", "unauth".
        Noten que es importante que esto vaya después del aviso,
        de lo contrario no podría avisarle a los contactos que estoy "offline".
        Con ésto he terminado mi clase de ayuda para los métodos de "Firebase".
    * */
    private void notifyContactsOfConnectionChange(final boolean online, final boolean signOff){
        final String myEmail = getAuthUserEmail();
        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*por cada contacto lo que voy a hacer es,
                voy a verificar cual es el "email"*/
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    String email = child.getKey();
                    /*Recuerden el modelo de datos que tenemos definido. Defino también una referencia.
                    Y lo voy a hacer a través de la llamada a "getOneContactReference",
                    le tengo que enviar dos "emails".
                    El "email" del usuario que está en mis contactos y mi correo.
                    De esta forma voy a lograr avisarle a mis contactos que yo estoy "offline".*/
                    Firebase reference = getOneContactReference(email, myEmail);
                    /*Entonces, con eso listo hago "reference",
                    "punto", "setValue".
                    Y le vamos a avisar
                    que estoy "online" o que estoy "offline".*/
                    reference.setValue(online);
                }
                /*Noten que es importante que esto vaya después del aviso,
                   de lo contrario no podría avisarle a los contactos que estoy "offline"

                   Si acaso estoy cerrando sesión, después de que le avisé a todos de que ya cerré sesión,
                    entonces, voy a hacer un "dataReference", "punto", "unauth"
                  */
                if(signOff){
                    dataReference.unauth();
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
