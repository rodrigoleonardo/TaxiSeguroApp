package edu.galileo.android.taxiseguro.lib;

import android.widget.ImageView;

/**
 * Created by rodrigo on 15/06/16.
 */
public interface ImageLoader {
    //carga una imagen de acuerdo a un url
    void load(ImageView imgAvatar, String url);
}
