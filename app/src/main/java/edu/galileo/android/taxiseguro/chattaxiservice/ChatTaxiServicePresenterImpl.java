package edu.galileo.android.taxiseguro.chattaxiservice;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.taxiseguro.chattaxiservice.events.ChatTaxiServiceEvent;
import edu.galileo.android.taxiseguro.chattaxiservice.ui.ChatTaxiServiceView;
import edu.galileo.android.taxiseguro.entities.User;
import edu.galileo.android.taxiseguro.lib.EventBus;
import edu.galileo.android.taxiseguro.lib.GreenRobotEventBus;

/**
 * Created by rodrigo on 18/06/16.
 */
public class ChatTaxiServicePresenterImpl implements ChatTaxiServicePresenter {
    private EventBus eventBus;
    private ChatTaxiServiceView view;
    private ChatTaxiServiceInteractor chatTaxiServiceInteractor;
    private ChatTaxiServiceSessionInteractor chatTaxiServiceSessionInteractor;

    public ChatTaxiServicePresenterImpl(ChatTaxiServiceView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.chatTaxiServiceInteractor = new ChatTaxiServiceInteractorImpl();
        this.chatTaxiServiceSessionInteractor = new ChatTaxiServiceSessionInteractorImpl();
    }

    @Override
    public void onPause() {
        chatTaxiServiceInteractor.unsubscribe();
        chatTaxiServiceSessionInteractor.changeConnectionStatus(User.OFFLINE);
    }

    @Override
    public void onResume() {
        chatTaxiServiceInteractor.subscribe();
        chatTaxiServiceSessionInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        chatTaxiServiceInteractor.destroyListener();
        view = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        chatTaxiServiceInteractor.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatTaxiServiceInteractor.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatTaxiServiceEvent event) {
        if(view != null){
            view.onMessageReceived(event.getMesssage());
        }
    }
}
