package edu.galileo.android.taxiseguro.chattaxiservice;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import edu.galileo.android.taxiseguro.chattaxiservice.events.ChatTaxiServiceEvent;
import edu.galileo.android.taxiseguro.domain.FireBaseHelper;
import edu.galileo.android.taxiseguro.entities.ChatTaxiServiceMessage;
import edu.galileo.android.taxiseguro.lib.EventBus;
import edu.galileo.android.taxiseguro.lib.GreenRobotEventBus;

/**
 * Created by rodrigo on 18/06/16.
 */
public class ChatTaxiServiceRepositoryImpl implements ChatTaxiServiceRepository {
    private String recipient;
    private EventBus eventBus;
    private FireBaseHelper helper;
    private ChildEventListener chatEventListener;


    public ChatTaxiServiceRepositoryImpl() {
        this.helper = FireBaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void sendMessage(String msg) {
        ChatTaxiServiceMessage chatTaxiServiceMessage = new ChatTaxiServiceMessage();
        chatTaxiServiceMessage.setSender(helper.getAuthUserEmail());
        chatTaxiServiceMessage.setMsg(msg);

        Firebase chatsReference = helper.getChatsReference(recipient);
        chatsReference.push().setValue(chatTaxiServiceMessage);
    }

    @Override
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public void subscribe() {
        if(chatEventListener == null){
            chatEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatTaxiServiceMessage chatTaxiServiceMessage = dataSnapshot.getValue(ChatTaxiServiceMessage.class);
                    String msgSender = chatTaxiServiceMessage.getSender();

                    chatTaxiServiceMessage.setSentByMe(msgSender.equals(helper.getAuthUserEmail()));

                    ChatTaxiServiceEvent chatTaxiServiceEvent = new ChatTaxiServiceEvent();
                    chatTaxiServiceEvent.setMessage(chatTaxiServiceMessage);
                    eventBus.post(chatTaxiServiceEvent);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            };
        }
        helper.getChatsReference(recipient).addChildEventListener(chatEventListener);
    }

    @Override
    public void unsubscribe() {
        if(chatEventListener != null){
            helper.getChatsReference(recipient).removeEventListener(chatEventListener);
        }
    }

    @Override
    public void destroyListener() {
        chatEventListener = null;
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        helper.changeUserConnectionStatus(online);
    }
}
