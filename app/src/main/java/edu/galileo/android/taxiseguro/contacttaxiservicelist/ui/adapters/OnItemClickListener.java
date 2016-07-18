package edu.galileo.android.taxiseguro.contacttaxiservicelist.ui.adapters;

import edu.galileo.android.taxiseguro.entities.User;

/**
 * Created by rodrigo on 15/06/16.
 * Para el manejo de click
 */
public interface OnItemClickListener {
    //click simple para ir a ver los,chats
    void onItemClick(User user);
    //Click largo para eliminar usuarios
    void onItemLongClick(User user);
}
