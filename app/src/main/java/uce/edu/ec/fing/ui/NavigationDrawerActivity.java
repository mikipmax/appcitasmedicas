package uce.edu.ec.fing.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import uce.edu.ec.fing.R;


public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Elegimos el fragmento de Reservación por default al iniciar la app
        if (savedInstanceState == null) {
            ActividadTipoPago fragment = ActividadTipoPago.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        }
    }

    //Para ocultar o presentar un menú
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }


    //Cuando doy click en un item del menu, voy mostrando los layouts de acuerdo al item seleccionado.

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.navMedico:
                obtenerFragmento(ActividadMedico.newInstance());
                break;
            case R.id.navPaciente:
                obtenerFragmento(ActividadPaciente.newInstance());
                break;
            case R.id.navReservacion:
                obtenerFragmento(ActividadReservacion.newInstance());
                break;
            case R.id.navEstadoReserva:
                obtenerFragmento(ActividadEstadoReserva.newInstance());
                break;
            case R.id.nav_tipo_pago:
                obtenerFragmento(ActividadTipoPago.newInstance());
                break;
            case R.id.navEspecialidad:
                obtenerFragmento(ActividadEspecialidad.newInstance());
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void obtenerFragmento(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, fragment)
                .addToBackStack("")
                .commit();
    }

}
