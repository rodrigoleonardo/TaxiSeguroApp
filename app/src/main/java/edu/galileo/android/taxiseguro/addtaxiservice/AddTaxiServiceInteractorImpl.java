package edu.galileo.android.taxiseguro.addtaxiservice;

/**
 * Created by rodrigo on 17/06/16.
 *
 */
public class AddTaxiServiceInteractorImpl implements AddTaxiServiceInteractor {
    AddTaxiServiceRepository repository;

    public AddTaxiServiceInteractorImpl() {
        repository = new AddTaxiServiceRepositoryImpl();
    }

    @Override
    public void execute(String email) {
        repository.addContact(email);
    }
}
