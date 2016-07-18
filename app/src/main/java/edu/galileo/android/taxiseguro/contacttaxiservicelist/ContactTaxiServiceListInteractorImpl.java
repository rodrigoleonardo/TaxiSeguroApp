package edu.galileo.android.taxiseguro.contacttaxiservicelist;

/**
 * Created by rodrigo on 15/06/16.
 */
public class ContactTaxiServiceListInteractorImpl implements ContactTaxiServiceListInteractor {
    ContactTaxiServiceListRepository repository;

    public ContactTaxiServiceListInteractorImpl() {
        repository = new ContactTaxiServiceListRepositoryImpl();
    }

    @Override
    public void subscribe() {
        repository.subscribeToContactListEvent();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribeToContactListEvent();
    }

    @Override
    public void destroyListener() {
        repository.destroyListener();
    }

    @Override
    public void removeContact(String email) {
        repository.removeContact(email);
    }
}