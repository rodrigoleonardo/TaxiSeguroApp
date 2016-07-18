package edu.galileo.android.taxiseguro.chattaxiservice;

/**
 * Created by rodrigo on 18/06/16.
 */
public class ChatTaxiServiceInteractorImpl implements ChatTaxiServiceInteractor {
    ChatTaxiServiceRepository repository;

    public ChatTaxiServiceInteractorImpl() {
        this.repository = new ChatTaxiServiceRepositoryImpl();
    }

    @Override
    public void sendMessage(String msg) {
        repository.sendMessage(msg);
    }

    @Override
    public void setRecipient(String recipient) {
        repository.setRecipient(recipient);
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }

    @Override
    public void destroyListener() {
        repository.destroyListener();
    }
}
