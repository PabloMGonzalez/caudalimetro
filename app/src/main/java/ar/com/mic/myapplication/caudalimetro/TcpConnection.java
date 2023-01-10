package ar.com.mic.myapplication.caudalimetro;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class TcpConnection {

    public interface DatosRecibidosListener {
        void onAforadorLeidoListener(TIPO_DATO_PEDIDO tipoAforadorPedido, String data);
        void onComandoDesconocidoRecibidoListener(String data);
        void onErrorConexionListener(TIPO_DATO_PEDIDO tipoAforadorPedido, String msg );
        void onSuccessConexionListener();
        void onDataEnviadaListener(String data);
    }

    public static enum SELECCION_CAUDALIMETRO {
        SELECCION_PENDIENTE,
        CAUDALIMETRO_SELECCIONADO,
        NO_ENCONTRE_CAUDALIMETRO,
        NO_UTILIZAR_CAUDALIMETRO
    }


    public static enum TIPO_DATO_PEDIDO {
        INICIAL,
        FINAL,
        TEMPERATURA,
        CONFIGURACION
    }

    DatosRecibidosListener listener;
    private TIPO_DATO_PEDIDO tipoDatoPedido;

    WifiManager wifiManager ;

    String ip = "192.168.1.30";
    int port = 10000;

    Socket socket;
    private OutputStream output;
    private InputStream inputStream;

    Thread ConnectionThread = null;

    public TcpConnection(DatosRecibidosListener listener){
        this.listener = listener;
    }

    public void iniciarConexion(){
        ConnectionThread = new Thread(new ConnectionThread());
        ConnectionThread.start();
    }

    public void cerrarConexion(){
        closeSocket();

        if(ConnectionThread != null) ConnectionThread.interrupt();
    }

    private void closeSocket(){
        if(socket != null && socket.isConnected() && !socket.isClosed()){
            try {
                socket.close();
                inputStream.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void leerAforador(TIPO_DATO_PEDIDO tipoDatoPedido){
        this.tipoDatoPedido = tipoDatoPedido;
        new Thread(new SendingThread()).start();
    }


    class ConnectionThread implements Runnable {

        public void run() {

            try {
                closeSocket();

                socket = new Socket(ip, port);
                output = socket.getOutputStream();
                inputStream = socket.getInputStream();

                listener.onSuccessConexionListener();

                closeSocket();

                //new Thread(receivingThread = new ReceivingThread()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class SendingThread implements Runnable {
        final byte[] CMD_AFORADOR_ACTUAL_LIQUID_CONTROL ={ (byte)0X7E, (byte)0X7E, (byte)0XFA, (byte)0XFF ,(byte)0X02 ,(byte)0X02 ,(byte)0X20 ,(byte)0X11 ,(byte)0XA5, (byte)0X49 };
        //final byte[] CMD_AFORADOR_ACTUAL_LIQUID_CONTROL ={ (byte)0X7E, (byte)0X7E, (byte)0XFA, (byte)0XFF ,(byte)0X02 ,(byte)0X01 ,(byte)0X00 ,(byte)0X2F ,(byte)0X34 };

        SendingThread() { }

        @Override
        public void run() {

            try {
                closeSocket();

                //socket = new Socket(ip, port);
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 3 * 1000);
                socket.setSoTimeout(3*1000);

                output = socket.getOutputStream();
                inputStream = socket.getInputStream();

                Thread.sleep(500);

                output.write(CMD_AFORADOR_ACTUAL_LIQUID_CONTROL);
                listener.onDataEnviadaListener(new String(CMD_AFORADOR_ACTUAL_LIQUID_CONTROL, StandardCharsets.UTF_8));
                socket.shutdownOutput();

                BufferedInputStream bis = new BufferedInputStream(inputStream);
                DataInputStream dis = new DataInputStream(bis);
                byte[] readedByte = new byte[1];
                String hexaStringData = "";
                byte[] bytesData = new byte[1024];
                int i = 0;
                while (dis.read(readedByte) != -1) {
                    hexaStringData += bytesToHexString(readedByte) + " ";
                    bytesData[i++] = readedByte[0];
                }

                processIncommingMessage(bytesData, hexaStringData);

                closeSocket();
            } catch (IOException e) {
                e.printStackTrace();
                listener.onErrorConexionListener( tipoDatoPedido, "TIMEOUT.");
            } catch (InterruptedException e) {
                e.printStackTrace();
                listener.onErrorConexionListener( tipoDatoPedido, "INTERRUPT.");
            }
        }
    }

    private void processIncommingMessage(final byte[] bytes, String hexaString){

        byte[] paquete = limpiarPaqueteRecibidoLiquidControl( bytes );

        byte[] datos = Arrays.copyOfRange(paquete, 8, 12);

        String datosHexa = bytesToHex(datos);

        String valorAforador = String.format( "%d", (Long.valueOf(datosHexa,16)) );

        listener.onAforadorLeidoListener(tipoDatoPedido, valorAforador);
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public byte[] limpiarPaqueteRecibidoLiquidControl(byte[] paqueteSinLimpiar){
        byte[] paqueteFinal = new byte[paqueteSinLimpiar.length];

        int cantCaracteresEnmascarados = 0;

        for(int indicePaqueteSinLimpiar = 0, indicePaqueteFinal = 0; indicePaqueteSinLimpiar < paqueteSinLimpiar.length ; indicePaqueteSinLimpiar++, indicePaqueteFinal++){

            if(indicePaqueteSinLimpiar<=1){
                paqueteFinal[indicePaqueteFinal] = paqueteSinLimpiar[indicePaqueteSinLimpiar];
                continue;
            }

            if(paqueteSinLimpiar[indicePaqueteSinLimpiar] == (byte) Integer.parseInt("1B", 16)){
                indicePaqueteSinLimpiar++;
                paqueteFinal[indicePaqueteFinal] = paqueteSinLimpiar[indicePaqueteSinLimpiar];
                cantCaracteresEnmascarados++;
            }else{
                paqueteFinal[indicePaqueteFinal] = paqueteSinLimpiar[indicePaqueteSinLimpiar];
            }
        }

        return  Arrays.copyOfRange(paqueteFinal, 0, paqueteSinLimpiar.length - cantCaracteresEnmascarados);
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public String detectWifiSsid(WifiManager wifiManager){

        WifiInfo wifiInfo;

        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            return wifiInfo.getSSID();
        }

        return null;
    }

    private void scanSuccess() {
        List<ScanResult> results = wifiManager.getScanResults();

    }

    private void scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        List<ScanResult> results = wifiManager.getScanResults();
        //... potentially use older scan results ...
    }
}
