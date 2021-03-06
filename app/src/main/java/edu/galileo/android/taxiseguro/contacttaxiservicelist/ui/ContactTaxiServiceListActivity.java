package edu.galileo.android.taxiseguro.contacttaxiservicelist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.taxiseguro.R;
import edu.galileo.android.taxiseguro.addtaxiservice.ui.AddTaxiServiceFragment;
import edu.galileo.android.taxiseguro.chattaxiservice.ui.ChatTaxiServiceActivity;
import edu.galileo.android.taxiseguro.contacttaxiservicelist.ContactTaxiServiceListPresenter;
import edu.galileo.android.taxiseguro.contacttaxiservicelist.ContactTaxiServiceListPresenterImpl;
import edu.galileo.android.taxiseguro.contacttaxiservicelist.ui.adapters.ContactTaxiServiceListAdapter;
import edu.galileo.android.taxiseguro.contacttaxiservicelist.ui.adapters.OnItemClickListener;
import edu.galileo.android.taxiseguro.entities.User;
import edu.galileo.android.taxiseguro.lib.GlideImageLoader;
import edu.galileo.android.taxiseguro.lib.ImageLoader;
import edu.galileo.android.taxiseguro.login.ui.LoginTaxiServiceActivity;

public class ContactTaxiServiceListActivity extends AppCompatActivity implements ContactTaxiServiceListView, OnItemClickListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerViewContacts)
    RecyclerView recyclerViewContacts;
    private ContactTaxiServiceListAdapter adapter;
    private ContactTaxiServiceListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();

        presenter = new ContactTaxiServiceListPresenterImpl(this);
        presenter.onCreate();

        setupToolbar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contactlist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //MODIFICADO
        //para que retorne a la pantalla de ingreso y no regrese a la pantalla anterior o actividad anterior
        if(item.getItemId() == R.id.action_logout){
            presenter.signOff();
            Intent intent = new Intent(this, LoginTaxiServiceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(){
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(adapter);
    }

    private void setupAdapter(){
        ImageLoader loader = new GlideImageLoader(this.getApplicationContext());
        /*User user = new User();
        user.setOnline(false);
        user.setEmail("abcd@gmail.com");
        adapter = new ContactTaxiServiceListAdapter(Arrays.asList(new User[]{user}), loader, this);*/
        adapter = new ContactTaxiServiceListAdapter(new ArrayList<User>(), loader, this);
    }

    private void setupToolbar(){
        toolbar.setTitle(presenter.getCurrentUserEmail());
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    /*FloatingActionButton" lo voy a usar únicamente para poder manejar
                el "click". Entonces vamos a agregar aquí un método "OnClick" y voy a llevarme desde
                aquí el "id" hacia acá

                Este es el botoncito rojo en la parte inferior de la pantalla*/
    @OnClick(R.id.fab)
    public void addContact(){
        new AddTaxiServiceFragment().show(getSupportFragmentManager(), getString(R.string.addcontact_message_title));
    }

    @Override
    public void onContactAdded(User user) {
        adapter.add(user);
    }

    @Override
    public void onContactChanged(User user) {
        adapter.update(user);
    }

    @Override
    public void onContactRemoved(User user) {
        adapter.remove(user);
    }

    @Override
    public void onItemClick(User user) {
        //Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ChatTaxiServiceActivity.class);
        intent.putExtra(ChatTaxiServiceActivity.EMAIL_KEY, user.getEmail());
        intent.putExtra(ChatTaxiServiceActivity.ONLINE_KEY, user.isOnline());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(User user) {
        presenter.removeContact(user.getEmail());
    }
}
