package ar.com.mic.myapplication;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.net.wifi.WifiManager;
import android.content.Context;
import ar.com.mic.myapplication.utilidades.CustomAlertDialog;
import ar.com.mic.myapplication.utilidades.Utiles;
import android.content.DialogInterface;

import java.util.Set;

import ar.com.mic.myapplication.caudalimetro.TcpConnection;
import ar.com.mic.myapplication.common.view.BaseActivity;
public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    private TextView mStatusBleTv, mPairedTv;
    ImageView mBlueIV;
    Button mOnBtn, mOffBtn, mDiscoverBtn, mPairedBtn, mProbarCaudalimetro;
    BluetoothAdapter bluetoothAdapter;
    TcpConnection tcpConnection;
    private static BaseActivity currentActivity;


    public static BaseActivity getCurrentActivity(){
        return currentActivity;
    }

    public static void setCurrentActivity(BaseActivity activity){
        currentActivity = activity;
    }


    private void mostrarPanelWifi(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
            startActivityForResult(panelIntent, 545);
        }else{
            startActivity( new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
        }
    }

    public void pedirDatosAforador() {

        String ssid = tcpConnection.detectWifiSsid((WifiManager) getSystemService(Context.WIFI_SERVICE));

        if( ssid == null){
            CustomAlertDialog dlg = new CustomAlertDialog(this, "Atención", "Atención: Debe conectarse a la red WIFI " + Utiles.getNombreAbastecedora() + " que corresponde a su abastecedora.");
            dlg.setType(Utiles.TIPO_ALERT_DIALOG.ERROR);
            dlg.setInteractionType(Utiles.TIPO_ALERT_DIALOG_INTERACTION.OK);
            dlg.setListener(new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        mostrarPanelWifi();
                    }
                }
            });
            dlg.show();
            return;
        }else if( ! ssid.equals(Utiles.getNombreAbastecedora()) ){
            CustomAlertDialog dlg = new CustomAlertDialog(this, "Atención", "Atención: Debe conectarse a la red WIFI " + Utiles.getNombreAbastecedora() + " que corresponde a su abastecedora. Actualmente conectado a " + ssid);
            dlg.setType(Utiles.TIPO_ALERT_DIALOG.ERROR);
            dlg.setInteractionType(Utiles.TIPO_ALERT_DIALOG_INTERACTION.OK);
            dlg.setListener(new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        mostrarPanelWifi();
                    }
                }
            });
            dlg.show();
            return;
        }
        tcpConnection.leerAforador(TcpConnection.TIPO_DATO_PEDIDO.INICIAL);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusBleTv = findViewById(R.id.statusBluetoothTv);
        mPairedTv = findViewById(R.id.pairTv);
        mBlueIV = findViewById(R.id.bluetoothIv);
        mOnBtn = findViewById(R.id.onButn);
        mOffBtn = findViewById(R.id.offButn);
        mDiscoverBtn = findViewById(R.id.discoverableBtn);
        mPairedBtn = findViewById(R.id.PairedBtn);
        mProbarCaudalimetro = findViewById(R.id.btnProbarCaudalimetro);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            mStatusBleTv.setText("Bluetooth is not available");
        } else {
            mStatusBleTv.setText("Bluetooth is  available");


            if (bluetoothAdapter.isEnabled()) {
                mBlueIV.setImageResource(R.drawable.bluetooth_on);
            } else {
                mBlueIV.setImageResource(R.drawable.bluetooth_off);

            }

            mOnBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!bluetoothAdapter.isEnabled()) {
                        showToast("Turning on Bluetooth..");
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                            return;
                        }

                        startActivityIfNeeded(intent, REQUEST_ENABLE_BT);
                    } else {
                        showToast("Bluetooth is already on");

                    }
                }
            });

            mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);
                        return;
                    }
                    if (!bluetoothAdapter.isDiscovering()) {
                        showToast("Making Your Device Discoverable");
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        startActivityIfNeeded(intent, REQUEST_DISCOVER_BT);
                    }
                }
            });
            mOffBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bluetoothAdapter.isEnabled()) {
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                            return;
                        }
                        bluetoothAdapter.disable();
                        showToast("Turning  Bluetooth off");
                        mBlueIV.setImageResource(R.drawable.bluetooth_off);
                    } else {
                        showToast("Bluetooth is already off");

                    }
                }
            });





            mProbarCaudalimetro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pedirDatosAforador();
                }
            });



            mPairedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bluetoothAdapter.isEnabled()) {
                        mPairedTv.setText("Paired Devices");
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                            return;
                        }
                        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();

                        for (BluetoothDevice device : devices) {
                            mPairedTv.append("\n Device : " + device.getName() + " , " + device);
                        }
                    } else {
                        showToast("Turn On bluetooth to get paired devices");
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    mBlueIV.setImageResource(R.drawable.bluetooth_on);
                    showToast("Bluetooth is On");
                } else {
                    showToast("Bluetooth is Off");

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}