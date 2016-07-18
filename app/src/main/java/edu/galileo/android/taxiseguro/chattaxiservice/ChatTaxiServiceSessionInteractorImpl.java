package edu.galileo.android.taxiseguro.chattaxiservice;

/**
 * Created by rodrigo on 18/06/16.
 */
public class ChatTaxiServiceSessionInteractorImpl implements ChatTaxiServiceSessionInteractor {
    ChatTaxiServiceRepository repository;

    public ChatTaxiServiceSessionInteractorImpl() {
        this.repository = new ChatTaxiServiceRepositoryImpl();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        repository.changeConnectionStatus(online);
    }
}
