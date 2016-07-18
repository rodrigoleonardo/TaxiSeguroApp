package edu.galileo.android.taxiseguro.contacttaxiservicelist.ui;

import edu.galileo.android.taxiseguro.entities.User;

/**
 * Created by rodrigo on 14/06/16.
 */
public interface ContactTaxiServiceListView {
        /*Esperamos que la vista reaccione cuando hay un contacto
        agregado y que reciba este usuario, que también reaccione cuando el contacto es cambiado,
        por ejemplo, cambió su estatus y por último, que reaccione cuando borramos el contacto.*/
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
