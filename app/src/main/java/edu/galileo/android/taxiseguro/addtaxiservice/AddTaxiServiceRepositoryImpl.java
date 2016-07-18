package edu.galileo.android.taxiseguro.addtaxiservice;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import edu.galileo.android.taxiseguro.addtaxiservice.events.AddTaxiServiceEvent;
import edu.galileo.android.taxiseguro.domain.FireBaseHelper;
import edu.galileo.android.taxiseguro.entities.User;
import edu.galileo.android.taxiseguro.lib.EventBus;
import edu.galileo.android.taxiseguro.lib.GreenRobotEventBus;

/**
 * Created by rodrigo on 17/06/16.
 */
public class AddTaxiServiceRepositoryImpl implements AddTaxiServiceRepository {
    private EventBus eventBus;
    private FireBaseHelper helper;

    public AddTaxiServiceRepositoryImpl() {
        this.helper = FireBaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void addContact(final String email) {
        final String key = email.replace(".", "_");
        Firebase userReference = helper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {//porque me interesa un unico evento
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user != null){//si este usuario existe y est'a online
                    Firebase myContactReference = helper.getMyContactsReference();
                    myContactReference.child(key).setValue(user.isOnline());

                    String currentUserKey = helper.getAuthUserEmail();
                    currentUserKey = currentUserKey.replace(".", "_");//permite utilizar el email en multiples lugares de firebase
                    Firebase reverseContactReference = helper.getContactsReference(email);
                    reverseContactReference.child(currentUserKey).setValue(User.ONLINE);

                    postSuccess();
                }else{
                    postError();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                postError();
            }
        });
    }

    private void postSuccess() {
        post(false);
    }

    private void postError() {
        post(true);
    }

    private void post(boolean error){
        AddTaxiServiceEvent event = new AddTaxiServiceEvent();
        event.setError(error);
        eventBus.post(event);
    }
}
