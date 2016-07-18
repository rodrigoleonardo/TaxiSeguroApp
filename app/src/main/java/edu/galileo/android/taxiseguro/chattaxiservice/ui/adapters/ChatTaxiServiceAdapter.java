package edu.galileo.android.taxiseguro.chattaxiservice.ui.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.taxiseguro.R;
import edu.galileo.android.taxiseguro.entities.ChatTaxiServiceMessage;

/**
 * Created by rodrigo on 18/06/16.
 */
public class ChatTaxiServiceAdapter extends RecyclerView.Adapter<ChatTaxiServiceAdapter.ViewHolder>{
    //Para obtener y cambiar el color del tema
    private Context context;
    private List<ChatTaxiServiceMessage> chatTaxiServiceMessages;

    public ChatTaxiServiceAdapter(Context context, List<ChatTaxiServiceMessage> chatTaxiServiceMessages) {
        this.context = context;
        this.chatTaxiServiceMessages = chatTaxiServiceMessages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //viewHOlder a partir de la vista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_chat  , parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatTaxiServiceMessage chatTaxiServiceMessage = chatTaxiServiceMessages.get(position);

        String msg = chatTaxiServiceMessage.getMsg();
        holder.txtMessage.setText(msg);

        int color = fetchColor(R.attr.colorPrimary);
        int gravity = Gravity.LEFT;
        if(!chatTaxiServiceMessage.isSentByMe()){
            color = fetchColor(R.attr.colorAccent);
            gravity = Gravity.RIGHT;
        }

        holder.txtMessage.setBackgroundColor(color);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.txtMessage.getLayoutParams();
        params.gravity = gravity;
        holder.txtMessage.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return chatTaxiServiceMessages.size();
    }
    public  void add(ChatTaxiServiceMessage msg){
        if(!chatTaxiServiceMessages.contains(msg)){
            chatTaxiServiceMessages.add(msg);
            notifyDataSetChanged();
        }
    }
    //Obtiene el color a partir del identificador que se le envie, para cada uni de los mensajes
    private int fetchColor(int color){
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{color});
        int returnColor = a.getColor(0, 0);
        a.recycle();
        return returnColor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.txtMessage)
        TextView txtMessage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
