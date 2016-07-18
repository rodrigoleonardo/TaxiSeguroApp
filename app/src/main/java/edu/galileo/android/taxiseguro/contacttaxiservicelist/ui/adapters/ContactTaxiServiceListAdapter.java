package edu.galileo.android.taxiseguro.contacttaxiservicelist.ui.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.galileo.android.taxiseguro.R;
import edu.galileo.android.taxiseguro.domain.AvatarHelper;
import edu.galileo.android.taxiseguro.entities.User;
import edu.galileo.android.taxiseguro.lib.ImageLoader;

/**
 * Created by rodrigo on 15/06/16.
 */
public class ContactTaxiServiceListAdapter extends RecyclerView.Adapter<ContactTaxiServiceListAdapter.ViewHolder> {
    //guardar datos
    private List<User> contactList;
    //una forma de cargar las imagenes
    private ImageLoader imageLoader;
    //parapoder manejar eventos
    private OnItemClickListener onItemClickListener;

    public ContactTaxiServiceListAdapter(List<User> contactList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.contactList = contactList;
        this.imageLoader = imageLoader;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //viewHOlder a partir de la vista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    //Este metodo permite asignar los valores
        User user = contactList.get(position);
        holder.setClickListener(user, onItemClickListener);
        //aqu'ies donde puedo colocar todos los valores necesarios para que los campos de mi holder tenga algo
        String email = user.getEmail();

        boolean online = user.isOnline();
        String status= online ? "disponible":"ocupado";
        int color = online ? Color.GREEN : Color.RED;

        holder.txtUser.setText(email);
        holder.txtStatus.setText(status);
        holder.txtStatus.setTextColor(color);
        //carga la imagen
        imageLoader.load(holder.imgAvatar, AvatarHelper.getAvatarUrl(email));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void add(User user) {
        if(!contactList.contains(user)){
            contactList.add(user);
            notifyDataSetChanged();
        }
    }

    public void update(User user) {
        if(contactList.contains(user)){
            int index= contactList.indexOf(user);
            contactList.set(index, user);
            notifyDataSetChanged();
        }
    }

    public void remove(User user) {
        if(contactList.contains(user)){
            contactList.remove(user);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @Bind(R.id.txtUser)
        TextView txtUser;
        @Bind(R.id.txtStatus)
        TextView txtStatus;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }
        private void setClickListener(final User user, final OnItemClickListener listener){
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClick(user);
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(user);
                    return false;
                }
            });
        }
    }
}
