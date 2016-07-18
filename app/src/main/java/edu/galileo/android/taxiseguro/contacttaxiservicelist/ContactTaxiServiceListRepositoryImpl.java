package edu.galileo.android.taxiseguro.contacttaxiservicelist;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import edu.galileo.android.taxiseguro.contacttaxiservicelist.events.ContactTaxiServiceListEvent;
import edu.galileo.android.taxiseguro.domain.FireBaseHelper;
import edu.galileo.android.taxiseguro.entities.User;
import edu.galileo.android.taxiseguro.lib.EventBus;
import edu.galileo.android.taxiseguro.lib.GreenRobotEventBus;

/**
 * Created by rodrigo on 15/06/16.
 */
public class ContactTaxiServiceListRepositoryImpl implements ContactTaxiServiceListRepository {
    private EventBus eventBus;
    private FireBaseHelper helper;
    private ChildEventListener contactEventListener;

    public ContactTaxiServiceListRepositoryImpl() {
        this.helper = FireBaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void signOff() {
        helper.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return helper.getAuthUserEmail();
    }

    @Override
    public void destroyListener() {
        contactEventListener = null;
    }

    @Override
    public void removeContact(String email) {
        String currentUserEmail = helper.getAuthUserEmail();
        helper.getOneContactReference(currentUserEmail, email).removeValue();
        helper.getOneContactReference(email, currentUserEmail).removeValue();
    }

    @Override
    public void subscribeToContactListEvent() {
        if(contactEventListener == null) {
            contactEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactTaxiServiceListEvent.onContactAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactTaxiServiceListEvent.onContactChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleContact(dataSnapshot, ContactTaxiServiceListEvent.onContactRemoved);
                }



                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            };
        }
        //la  subscripcion
        helper.getMyContactsReference().addChildEventListener(contactEventListener);
    }

    //los items que se muetran bajo el usuario principal logeado
    private void handleContact(DataSnapshot dataSnapshot, int type) {
        String email = dataSnapshot.getKey();
        email = email.replace("_", ".");
        boolean online = ((Boolean)dataSnapshot.getValue()).booleanValue();
        User user=new User();
        user.setEmail(email);
        user.setOnline(online);
        post(type, user);
    }


    private void post(int type, User user) {
        ContactTaxiServiceListEvent event = new ContactTaxiServiceListEvent();
        event.setEventType(type);
        event.setUser(user);
        eventBus.post(event);
    }

    @Override
    public void unsubscribeToContactListEvent() {
        if(contactEventListener != null){
            //listado de contactos correspondientes a mi usuario
            helper.getMyContactsReference().removeEventListener(contactEventListener);
        }
    }

    @Override
    public void changeConnectionStatus(boolean online) {

    }
}
