package edu.galileo.android.taxiseguro.chattaxiservice.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.galileo.android.taxiseguro.R;
import edu.galileo.android.taxiseguro.chattaxiservice.ui.adapters.ChatTaxiServiceAdapter;
import edu.galileo.android.taxiseguro.chattaxiservice.ChatTaxiServicePresenter;
import edu.galileo.android.taxiseguro.chattaxiservice.ChatTaxiServicePresenterImpl;
import edu.galileo.android.taxiseguro.domain.AvatarHelper;
import edu.galileo.android.taxiseguro.entities.ChatTaxiServiceMessage;
import edu.galileo.android.taxiseguro.lib.GlideImageLoader;
import edu.galileo.android.taxiseguro.lib.ImageLoader;

public class ChatTaxiServiceActivity extends AppCompatActivity implements ChatTaxiServiceView {

    @Bind(R.id.imgAvatar)
    CircleImageView imgAvatar;
    @Bind(R.id.txtUser)
    TextView txtUser;
    @Bind(R.id.txtStatus)
    TextView txtStatus;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.messageRecyclerView)
    RecyclerView messageRecyclerView;
    @Bind(R.id.editTxtMessage)
    EditText editTxtMessage;


    private ChatTaxiServiceAdapter adapter;
    //muestra conte ido del ToolBar
    private ChatTaxiServicePresenter presenter;



    //este se llama y se inicia en ContactTaxiServiceListActivity.java
    public final static String EMAIL_KEY = "email";
    public final static String ONLINE_KEY = "online";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();
        presenter = new ChatTaxiServicePresenterImpl(this);//this, es la vista lo que quiere decir que voy a implementar la vista, es decir la clase ChatTaxiServiceView.
        presenter.onCreate();//sobrecargo estos m'etdos
        setupToolbar(getIntent());

        //para que muestre la flecha de bkack en el toolbar se realiza de esta forma ya que lo que se muetra nomes un toolbar
        //pero para que funcina hay que agregar un meta-data en el archivo manifest
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupAdapter() {
        /*ChatTaxiServiceMessage msg1 = new ChatTaxiServiceMessage();
        ChatTaxiServiceMessage msg2 = new ChatTaxiServiceMessage();
        ChatTaxiServiceMessage msg3 = new ChatTaxiServiceMessage();
        ChatTaxiServiceMessage msg4 = new ChatTaxiServiceMessage();

        msg1.setMsg("hole");
        msg2.setMsg("Cómo,estas?");
        msg3.setMsg("Si yo estoy muy bien, y espero que tu tambien seas om te encuentres de maravilla pero cuidate lorem ison DOlor, tre tistes tigres");
        msg4.setMsg("Comen trigo en u trigal pero los tres son muy comelones?");

        msg1.setSentByMe(true);
        msg2.setSentByMe(false);
        msg3.setSentByMe(true);
        msg4.setSentByMe(false);

        adapter = new ChatTaxiServiceAdapter(this, Arrays.asList(new ChatTaxiServiceMessage[]{msg1, msg2, msg3, msg4}));*/

        adapter = new ChatTaxiServiceAdapter(this, new ArrayList<ChatTaxiServiceMessage>());
    }

    private void setupRecyclerView() {
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerView.setAdapter(adapter);
    }

    private void setupToolbar(Intent intent) {
        String recipient = intent.getStringExtra(EMAIL_KEY);
        presenter.setChatRecipient(recipient);

        boolean online = intent.getBooleanExtra(ONLINE_KEY, false);

        //aqu'ies donde puedo colocar todos los valores necesarios para que los campos de mi holder tenga algo
        String status= online ? "disponible":"ocupado";
        int color = online ? Color.GREEN : Color.RED;

        txtUser.setText(recipient);
        txtStatus.setText(status);
        txtStatus.setTextColor(color);
        //carga la imagen
        ImageLoader imageLoader = new GlideImageLoader(getApplicationContext());
        imageLoader.load(imgAvatar, AvatarHelper.getAvatarUrl(recipient));

        setSupportActionBar(toolbar);
    }
    //sobrecargo estos m'etdos

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onMessageReceived(ChatTaxiServiceMessage msg) {
        adapter.add(msg);
        messageRecyclerView.scrollToPosition(adapter.getItemCount() -1);//para que presente en la pare de abajo, tavez los mensajes
    }

    @OnClick(R.id.btnSendMessage)
    public void sendMessage(){
        presenter.sendMessage(editTxtMessage.getText().toString());
        editTxtMessage.setText("");
    }
}
