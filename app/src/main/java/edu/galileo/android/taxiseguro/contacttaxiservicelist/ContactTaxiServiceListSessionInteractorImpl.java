package edu.galileo.android.taxiseguro.contacttaxiservicelist;

/**
 * Created by rodrigo on 15/06/16.
 */
public class ContactTaxiServiceListSessionInteractorImpl implements ContactTaxiServiceListSessionInteractor {
    ContactTaxiServiceListRepository repository;

    public ContactTaxiServiceListSessionInteractorImpl() {
        repository = new ContactTaxiServiceListRepositoryImpl();
    }

    @Override
    public void signOff() {
        repository.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return repository.getCurrentUserEmail();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        repository.changeConnectionStatus(online);
    }
}
