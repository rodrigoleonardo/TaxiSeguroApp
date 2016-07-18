package edu.galileo.android.taxiseguro.addtaxiservice;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.taxiseguro.addtaxiservice.events.AddTaxiServiceEvent;
import edu.galileo.android.taxiseguro.addtaxiservice.ui.AddTaxiServiceView;
import edu.galileo.android.taxiseguro.lib.EventBus;
import edu.galileo.android.taxiseguro.lib.GreenRobotEventBus;

/**
 * Created by rodrigo on 17/06/16.
 */
public class AddTaxiServicePresenterImpl implements AddTaxiServicePresenter {
    private EventBus eventBus;
    private AddTaxiServiceView view;
    private AddTaxiServiceInteractor interactor;

    public AddTaxiServicePresenterImpl(AddTaxiServiceView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new AddTaxiServiceInteractorImpl();
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void addContact(String email) {
        if(view != null){
            view.hideInput();
            view.showProgress();
        }
        interactor.execute(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddTaxiServiceEvent event) {
        if(view != null){
            view.hideProgress();
            view.showInput();
            if(event.isError()){
                view.contactNotAdded();
            }else{
                view.contactAdded();
            }
        }
    }
}
