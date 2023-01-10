package ar.com.mic.myapplication.common.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ar.com.mic.myapplication.MainActivity;
import ar.com.mic.myapplication.R;
import ar.com.mic.myapplication.utilidades.Utiles;

public abstract class BaseActivity extends AppCompatActivity
        implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.setCurrentActivity(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Utiles.hideKeyboard(this);

        return false;
    }
}
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.listado_aerovales_menu, menu);
//
//        if( ! getClass().getSimpleName().equals("ListadoAerovalesActivity")
//            && ! getClass().getSimpleName().equals("ListadoPedidosActivity")
//                && ! getClass().getSimpleName().equals("MainStockActivity") ){
//                menu.findItem(R.id.accion_menu_refrescar_lista_principal).setVisible(false);
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.accion_menu_refrescar_lista_principal:
//            case R.id.accion_menu_configuracion:
//                accionMenuTitulo(item.getItemId());
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    public  void accionMenuTitulo(int Rid){
//        if(Rid == R.id.accion_menu_configuracion){
//            if( ! getClass().getSimpleName().equals("DialogoConfiguracionActivity") ) {
//                mostrarFragmentConfiguracion();
//            }
//        }
//    }
//
//    private void mostrarFragmentConfiguracion(){
//        Intent intent = new Intent(this, DialogoConfiguracionActivity.class);
//        startActivity(intent);
//    }
//}
