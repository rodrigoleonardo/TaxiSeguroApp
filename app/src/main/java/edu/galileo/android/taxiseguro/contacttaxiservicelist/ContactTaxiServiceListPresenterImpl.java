package edu.galileo.android.taxiseguro.contacttaxiservicelist;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.taxiseguro.contacttaxiservicelist.events.ContactTaxiServiceListEvent;
import edu.galileo.android.taxiseguro.contacttaxiservicelist.ui.ContactTaxiServiceListView;
import edu.galileo.android.taxiseguro.entities.User;
import edu.galileo.android.taxiseguro.lib.EventBus;
import edu.galileo.android.taxiseguro.lib.GreenRobotEventBus;

/**
 * Created by rodrigo on 15/06/16.
 */
public class ContactTaxiServiceListPresenterImpl implements ContactTaxiServiceListPresenter {
    //para detectar eventos
    private EventBus eventBus;
    //Una vista para comunicar con la actividad
    private ContactTaxiServiceListView view;
    //interactuador
    private ContactTaxiServiceListInteractor listInteractor;
    private ContactTaxiServiceListSessionInteractor sessionInteractor;

    public ContactTaxiServiceListPresenterImpl(ContactTaxiServiceListView view) {
        this.view = view;
        eventBus = GreenRobotEventBus.getInstance();
        this.listInteractor = new ContactTaxiServiceListInteractorImpl();
        this.sessionInteractor = new ContactTaxiServiceListSessionInteractorImpl();
    }

    @Override
    public void onPause() {
        sessionInteractor.changeConnectionStatus(User.OFFLINE);
        listInteractor.unsubscribe();
    }

    @Override
    public void onResume() {
        sessionInteractor.changeConnectionStatus(User.ONLINE);
        listInteractor.subscribe();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        //para n tener un memory leak o algun problema con tod0 lo que se ha instanciado
        listInteractor.destroyListener();
        view = null;
    }

    @Override
    public void signOff() {
        sessionInteractor.changeConnectionStatus(User.OFFLINE);
        listInteractor.unsubscribe();
        listInteractor.destroyListener();
        sessionInteractor.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return sessionInteractor.getCurrentUserEmail();
    }

    @Override
    public void removeContact(String email) {
        listInteractor.removeContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ContactTaxiServiceListEvent event) {
        //aqui van los posibles eventos que tengamos
        User user = event.getUser();
        switch (event.getEventType()){
            case ContactTaxiServiceListEvent.onContactAdded:
                onContactAdded(user);
                break;
            case ContactTaxiServiceListEvent.onContactChanged:
                onContactChanged(user);
                break;
            case ContactTaxiServiceListEvent.onContactRemoved:
                onContactRemoved(user);
                break;
        }
    }
    private void onContactAdded(User user){
        if(view != null){
            view.onContactAdded(user);
        }
    }

    private void onContactChanged(User user){
        if(view != null){
            view.onContactChanged(user);
        }
    }

    private void onContactRemoved(User user){
        if(view != null){
            view.onContactRemoved(user);
        }
    }
}
