package ar.com.mic.myapplication.utilidades;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.irozon.sneaker.Sneaker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import ar.com.aeroplantas.pad.PadApplication;
import ar.com.mic.myapplication.MainActivity;
import ar.com.mic.myapplication.R;
//import ar.com.aeroplantas.pad.common.viewModel.LoginViewModel;
//import ar.com.aeroplantas.pad.model.entity.Abastecedora;
//import ar.com.aeroplantas.pad.model.entity.Aeroplanta;
//import ar.com.aeroplantas.pad.model.entity.AerovaleAerocombustible;
//import ar.com.aeroplantas.pad.model.entity.AerovaleAeroespecialidad;
//import ar.com.aeroplantas.pad.model.entity.AerovaleAlije;
//import ar.com.aeroplantas.pad.model.entity.AerovaleCombustible;
//import ar.com.aeroplantas.pad.model.entity.Producto;
//import ar.com.aeroplantas.pad.model.response.AerovaleResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Utiles {

    public final static String CODIGO_PAIS_ARGENTINA = "0303";

    public final static String JET_A1_PRODUCTO_CODIGO = "44A8954C-4A74-4C07-B022-100000000012";
    public final static double JET_A1_TOPE_INFERIOR = 0.775;
    public final static double JET_A1_TOPE_SUPERIOR = 0.840;

    public final static String AV_GAS_PRODUCTO_CODIGO = "44A8954C-4A74-4C07-B022-100000000103";
    public final static double AV_GAS_TOPE_INFERIOR = 0.665;
    public final static double AV_GAS_TOPE_SUPERIOR = 0.730;

    public final static String GUID_VACIO = "00000000-0000-0000-0000-000000000000";

    public final static float FACTOR_LITRO_GALON = 0.264172f;
    public final static float FACTOR_KILO_LIBRA = 2.20462f;

    public final static long VOLUMEN_MAXIMO_DESPACHADO = 500000;
    public final static long VOLUMEN_ALERTA_DESPACHADO = 55000;

    public static final String SHARED_PREFERENCES_FILE = "preferences";
    public static final String SP_LAST_PADRON_AEROPUERTOS_UPDATE = "lastPadronAeropuertosUpdate";
    public static final String SP_LAST_PADRON_LOCALIDADES_UPDATE = "lastPadronLocalidadesUpdate";
    public static final String SP_LAST_PADRON_PROVINCIAS_UPDATE = "lastPadronProvinciasUpdate";
    public static final String SP_LAST_PADRON_CLIENTES_UPDATE = "lastPadronClientesUpdate";
    public static final String SP_LAST_PADRON_AERONAVES_UPDATE = "lastPadronAeronavesUpdate";
    public static final String SP_LAST_PADRON_AUTOMOTORES_UPDATE = "lastPadronAutomotoresUpdate";
    public static final String SP_LAST_PADRON_LABORATORIOS_UPDATE = "lastPadronLaboratoriosUpdate";
    public static final String SP_LAST_TRADERS_UPDATE = "SP_LAST_TRADERS_UPDATE";
    public static final String SP_LAST_RANCHOS_UPDATE = "SP_LAST_RANCHOS_UPDATE";
    public static final String SP_BYTES_DESCONOCIDOS_RECIBIDOS = "SP_BYTES_DESCONOCIDOS_RECIBIDOS";

    // PARAMETROS BUNDLE
    public static final String EXTRA_MODO_TRABAJO_AEROVALE = "EXTRA_MODO_TRABAJO_AEROVALE";
    public static final String EXTRA_ID_AEROVALE_DB = "EXTRA_ID_AEROVALE_DB";
    public static final String EXTRA_RECARGAR_DATOS_MAIN_ACTIVITY = "EXTRA_ID_AEROVALE_DB";
    public static final String EXTRA_ERROR_AUTORIZACION = "EXTRA_ERROR_AUTORIZACION";
    public static final String EXTRA_DATOS_CAUDALIMETRO = "EXTRA_DATOS_CAUDALIMETRO";
    public static final String EXTRA_GUID_PEDIDO = "EXTRA_GUID_PEDIDO";
    public static final String EXTRA_AEROVALE_GUID = "EXTRA_AEROVALE_GUID";
    public static final String EXTRA_TIPO_AEROVALE = "EXTRA_TIPO_AEROVALE";
    public static final String EXTRA_ESTADO_IMPRESION = "EXTRA_ESTADO_IMPRESION";
    public static final String EXTRA_CLIENTE_CTA_SGC = "EXTRA_CLIENTE_CTA_SGC";
    public static final String EXTRA_ID_AEROVALE_EXCESO_DEVOLUCION_DB = "EXTRA_ID_AEROVALE_EXCESO_DEVOLUCION_DB";

    public static final String EXTRA_BUNDLE_PRECARGA_GUID_PARAMETER = "EXTRA_BUNDLE_PRECARGA_GUID_PARAMETER";
    public static final String EXTRA_BUNDLE_PEDIDO_GUID_PARAMETER = "EXTRA_BUNDLE_PEDIDO_GUID_PARAMETER";
    public static final String EXTRA_BUNDLE_AEROVALE_PARAMETER = "EXTRA_BUNDLE_AEROVALE_PARAMETER";
    public static final String EXTRA_BUNDLE_MOSTRAR_SELECCION_ABASTECEDORA_INICIAL = "EXTRA_BUNDLE_MOSTRAR_SELECCION_ABASTECEDORA_INICIAL";
    public static final String EXTRA_BUNDLE_CERRAR_APP = "EXTRA_BUNDLE_CERRAR_APP";
    public static final String EXTRA_BUNDLE_MENSAJE_CERRAR_APP = "EXTRA_BUNDLE_MENSAJE_CERRAR_APP";

    // REQUEST CODES
    public static final int REQUEST_CAMARA = 3;
    public static final int REQUEST_BACK_FIRMA_OPERADOR = 4;
    public static final int REQUEST_BACK_ACTUALIZAR_PADRONES = 5;
    public static final int REQUEST_FIRMA_CIERRE_PEDIDO = 6;

    // HTTP ERRORS
    public static final String HTTP_SERVER_ERROR = "500";
    public static final String HTTP_ERROR_USUARIO_SIN_AEROPLANTA = "40901";
    public static final String HTTP_ERROR_GENERICO = "1234";
    public static final String HTTP_ERROR_NO_SE_ENCUENTRA_EL_OBJETO = "40401";
    public static final String HTTP_ERROR_EN_ARCHIVO_CARGADO = "40402";
    public static final String HTTP_ERROR_DATO_DUPLICADO = "40403";
    public static final String HTTP_ERROR_VALIDACION = "41201";
    public static final String HTTP_ESTADO_INCONSISTENTE = "40903";
    public static final String HTTP_ERROR_EXCEPCION_AFIP = "50202";
    public static final String HTTP_DATO_EXISTENTE = "40904";
    public static final String HTTP_ERROR_CONEXION_AFIP = "50201";

    public static final String ENTORNO_IDENTITY_AONIKEN = "AONIKEN";
    public static final String ENTORNO_IDENTITY_MAGDESA = "magdesa";
    public static final String ENTORNO_IDENTITY_MAGTEST = "magtest";
    public static final String ENTORNO_IDENTITY_MAGPROD = "mag";

    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final int REQUEST_ACCESS_COARSE_LOCATION = 2;
    public static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static String[] PERMISSIONS_BLUETOOTH = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };

    public static String CODIGO_GLOBAL_EZEIZA = "9999";

    public static int REQUEST_ENABLE_BT = 1;

    // ENUMS

    public static enum ESTADO_ABASTECEDORAS {
        HAY_DISPONIBLES,
        NO_HAY_DISPONIBLES,
        ERROR_CONEXION
    }

    public static enum CONDICION_IVA {
        CONSUMIDOR_FINAL,
        RESPONSABLE_INSCRIPTO,
        OTROS
    }

    public static enum ACCION_ABRIR_PRECARGA {
        CREAR_NUEVO_AEROVALE,
        ANULAR_PRCARGA,
        CANCELAR
    }


    public static enum ACCION_TIPO_AEROVALE_CREACION {
        CANCELAR,
        AEROCOMBUSTIBLE,
        VEHICULOS,
        AEROESPECIALIDAD,
        ALIJE
    }

    public static enum ACCION_IMPRESION_AEROVALE {
        CANCELAR,
        IMPRIMIR
    }

    public static enum ACCION_ABRIR_AEROVALE {
        ABRIR_AEROVALE,
        ANULAR_AEROVALE,
        IMPRIMIR_AEROVALE,
        EDITAR_AEROVALE,
        CANCELAR
    }

    public static enum ACCION_FINALIZAR_AEROVALE {
        FINALIZAR_CON_FIRMA,
        FINALIZAR_SIN_FIRMA,
        CANCELAR
    }

    public static enum MODO_TRABAJO_AEROVALE {
        CREAR_AEROVALE,
        CREAR_AEROVALE_DESDE_PRECARGA,
        RETOMAR_AEROVALE,
        MODIFICAR_AEROVALE
    }

    public static enum ESTADO_AEROVALE {
        PRECARGADO,
        DESPACHO_INICIADO,
        DESPACHO_FINALIZADO,
        PENDIENTE,
        FINALIZADO_SIN_FIRMA,
        FINALIZADO_CON_FIRMA,
        SINCRONIZADO,
        ANULADO
    }

    public static enum ESTADO_PEDIDO {
        ABIERTO,
        CERRADO,
        ANULADO
    }

    public static enum PROGRESO_UI {
        LISTADO_INICIAL,
        VENTANA_1_DATOS_GENERALES,
        VENTANA_2_DATOS_PRODUCTO,
        VENTANA_3_FIRMA_OPERADOR,
        VENTANA_3_1_VISUALIZACION_AEV,
        VENTANA_3_2_DDJJ,
        VENTANA_4_FIRMA_CLIENTE,
        VENTANA_5_FINALIZAR_AEROVALE,
    }

    public static enum ERROR_CREAR_AEROVALE_VENTANA_1 {
        PENDIENTE_DE_CARGA,
        SIN_ERRORES,
        CLIENTE_OBLIGATORIA,
        MATRICULA_OBLIGATORIA,
        NRO_VUELO_NO_VALIDO,
        NO_HAY_SELECCIONADO_SURTIDOR,
        PRESION_PUNTA_MANGUERA_NO_DECIMAL,
        PRESION_DIFERENCIAL_NO_DECIMAL,
        CAUDAL_OBSERVADO_NO_DECIMAL,
        PRESION_PUNTA_MANGUERA_INVALIDA,
        PRESION_DIFERENCIAL_INVALIDA,
        CAUDAL_OBSERVADO_INVALIDO
    }

    public static final int MAX_DIGITOS_PERMITIDOS_AFORADOR = 15;

    public static enum ERROR_CREAR_AEROVALE_VENTANA_2 {
        PENDIENTE_DE_CARGA,
        SIN_ERRORES,
        AEROPUERTO,
        TEMPERATURA_VACIO,
        TEMPERATURA_NO_DECIMAL,
        TEMPERATURA_FUERA_DE_RANGO,
        DENSIDAD_VACIO,
        DENSIDAD_NO_DECIMAL,
        DENSIDAD_FUERA_DE_RANGO,
        PRUEBA_DE_AGUA_POSITIVA,
        RANCHO_OBLIGATORIO,
        RANCHO_DEBE_ESTAR_VACIO,
        VOLUMEN_DESPACHADO_VACIO,
        VOLUMEN_DESPACHADO_NO_ENTERO,
        VOLUMEN_DESPACHADO_15_GRADOS_VACIO,
        VOLUMEN_DESPACHADO_15_GRADOS_NO_DECIMAL,
        AFORADOR_INICIAL_VACIO,
        AFORADOR_INICIAL_NO_DECIMAL,
        AFORADOR_INICIAL_EXCEDE_LONGITUD,
        AFORADOR_FINAL_VACIO,
        AFORADOR_FINAL_NO_DECIMAL,
        AFORADOR_FINAL_EXCEDE_LONGITUD,
        AFORADOR_INICIAL_MAYOR_O_IGUAL_QUE_AFORADOR_FINAL,
        CLIENTE_NO_ADMITE_PREVIO_PAGO,
        CLIENTE_NO_ADMITE_CUENTA_CORRIENTE,
        VOLUMEN_DESPACHADO_MAYOR_A_RANCHO_DISPONIBLE,
        VOLUMEN_DESPACHADO_EXCEDE_VALOR_MAXIMO,
        LOCALIDAD_NO_SELECCIONADA
    }

    public static enum ERROR_CREAR_AEROVALE_VENTANA_3 {
        PENDIENTE_DE_CARGA,
        SIN_ERRORES,
        OPERADOR_NO_FIRMO
    }

    public static enum ERROR_CREAR_AEROVALE_VENTANA_4 {
        PENDIENTE_DE_CARGA,
        SIN_ERRORES,
        CLIENTE_NO_FIRMO
    }

    public static enum ERROR_CREAR_AEROVALE_VENTANA_5 {
        PENDIENTE_DE_CARGA,
        SIN_ERRORES,
        CLIENTE_NO_FIRMO
    }

    public static enum ERROR_CREAR_AEROVALE_AEROESPECIALIDAD {
        PENDIENTE_DE_CARGA,
        SIN_ERRORES,
        CLIENTE_OBLIGATORIA,
        CLIENTE_NO_ADMITE_PREVIO_PAGO,
        CLIENTE_NO_ADMITE_CUENTA_CORRIENTE,
        AL_MENOS_DEBE_CARGARSE_UNA_AEROESPECIALIDAD,
        CANTIDAD_INGRESADA_EN_AEROESPECIALIDAD_DEBE_SER_NUMERICO,
        NRO_VUELO_NO_VALIDO,
        LOCALIDAD_NO_SELECCIONADA
    }

    public static enum ERROR_CREAR_AEROVALE_COMBUSTIBLE {
        PENDIENTE_DE_CARGA,
        SIN_ERRORES,
        CLIENTE_OBLIGATORIA,
        CLIENTE_NO_ADMITE_PREVIO_PAGO,
        CLIENTE_NO_ADMITE_CUENTA_CORRIENTE,
        VOLUMEN_DESPACHADO_VACIO,
        VOLUMEN_DESPACHADO_NO_NUMERICO,
        CANTIDAD_INGRESADA_EN_AEROESPECIALIDAD_DEBE_SER_NUMERICO,
        LOCALIDAD_NO_SELECCIONADA,
        AFORADODR_INICIAL_VACIO,
        AFORADODR_INICIAL_NO_NUMERICO,
        AFORADODR_FINAL_VACIO,
        AFORADODR_FINAL_NO_NUMERICO,
        AFORADOR_INICIAL_MAYOR_O_IGUAL_AFORADOR_FINAL,
        NO_HAY_SELECCIONADO_SURTIDOR,
        DENSIDAD_INVALIDA,
        DENSIDAD_VACIO,
        DENSIDAD_NO_DECIMAL,
        DENSIDAD_FUERA_DE_RANGO,
        TEMPERATURA_INVALIDA,
        TEMPERATURA_FUERA_DE_RANGO,
        TEMPERATURA_VACIO,
    }

    public static enum ERROR_CREAR_AEV_ALIJE_VENTANA_1 {
        PENDIENTE_DE_CARGA,
        SIN_ERRORES,
        CLIENTE_OBLIGATORIA,
        MATRICULA_OBLIGATORIA,
        CODIGO_PRUEBA_LABORATORIO_VACIA,
        FECHA_PRUEBA_LABORATORIO_VACIA,
        RESULTADO_PRUEBA_LABORATORIO_NO_VALIDO,
        TEMPERATURA_VACIO,
        TEMPERATURA_NO_DECIMAL,
        TEMPERATURA_FUERA_DE_RANGO,
        DENSIDAD_VACIO,
        DENSIDAD_NO_DECIMAL,
        DENSIDAD_FUERA_DE_RANGO,
        PRUEBA_DE_AGUA_POSITIVA,
        VARILLADO_INICIAL_VACIO,
        VARILLADO_INICIAL_NO_DECIMAL,
        VARILLADO_FINAL_VACIO,
        VARILLADO_FINAL_NO_DECIMAL,
        VARILLADO_INICIAL_MAYOR_O_IGUAL_QUE_VARILLADO_FINAL,
        AFORADOR_INICIAL_VACIO,
        AFORADOR_INICIAL_NO_DECIMAL,
        AFORADOR_FINAL_VACIO,
        AFORADOR_FINAL_NO_DECIMAL,
        AFORADOR_INICIAL_MAYOR_O_IGUAL_QUE_AFORADOR_FINAL,
        LOCALIDAD_NO_SELECCIONADA,
        CLIENTE_NO_ADMITE_PREVIO_PAGO,
        CLIENTE_NO_ADMITE_CUENTA_CORRIENTE
    }

    public static enum ERROR_CREACION_CLIENTE {
        PENDIENTE_DE_CARGA,
        SIN_ERRORES,
        CUIT_NO_VALIDO,
        CUIT_VACIO,
        RAZON_SOCIAL_VACIA,
        LONOGITUD_RAZON_SOCIAL_NO_VALIDA
    }

    public static enum ERROR_CREACION_VEHICULO {
        PENDIENTE_DE_CARGA,
        SIN_ERRORES,
        LONOGITUD_MATRICULA_NO_VALIDA,
        MATRICULA_VACIA,
        TIPO_VEHICULO_NO_SELECCIONADO,
        MATRICULA_SOLO_LETRAS_NUMEROS,
        OPERANDO_SIN_ABASTECEDORA
    }

    public static enum ERROR_CARGA_CLIENTE_FACTURACION {
        SIN_ERRORES,
        DNI_VACIO,
        CUIT_VACIO,
        DNI_INVALIDO,
        CUIT_INVALIDO,
        RAZON_SOCIAL_VACIA,
        LONOGITUD_RAZON_SOCIAL_NO_VALIDA,
        LOCALIDAD_NO_SELECCIONADA,
        DOMICILIO_VACIO
    }

    public static enum ESTADO_CRONOMETRO {
        NO_INICIADO,
        INICIADO,
        FINALIZADO
    }

    public static enum TIPO_PAGO {
        INDETERMINADO,
        CONTADO,
        CUENTA_CORRIENTE,
        PREVIO_PAGO,
        DONACION,
        CONSUMO_INTERNO
    }

    public static enum TIPO_VUELO {
        INDETERMINADO,
        CABOTAJE,
        INTERNACIONAL
    }

    public static enum UNIDADES_MEDIDA {
        UNIDADES,
        LITROS,
        KILOGRAMOS,
        LIBRAS,
        GALON
    }

    public static enum ACCION_CERRAR_AEROVALE {
        GUARDAR_Y_SALIR,
        GUARDAR_SIN_FIRMA,
        GUARDAR_CON_FIRMA
    }

    public static enum TIPO_PRODUCTO {
        INDETERMINADO,
        AEROCOMBUSTIBLE,
        AEROESPECIALIDAD,
        COMBUSTIBLE,
        SERVICIOS
    }

    public static enum TIPO_ELEMENTO_LISTADO {
        AEROVALE,
        PRECARGA,
        AEROVALE_ALIJE
    }

    public static enum ERROR_AUTORIZACION {
        AUTENTICACION_MSAL,
        AUTORIZACION_PAD
    }

    public static enum MODO_SELECCION_AEROPLANTA {
        SELECCION_INICIAL_AEROPLANTA,
        MODIFICANDO_AEROPLANTA
    }

    public static enum MODO_SELECCION_ABASTECEDORA {
        SELECCION_INICIAL_ABASTECEDORA,
        MODIFICANDO_ABASTECEDORA
    }

    public static enum TIPO_ALERT_DIALOG {
        INFO,
        SUCCESS,
        WARNING,
        ERROR,
    }

    public static enum TIPO_ALERT_DIALOG_INTERACTION {
        OK,
        OK_CANCEL
    }

    public static enum TIPO_VEHICULO {
        AERONAVE,
        AUTOMOTOR,
        NO_ESPECIFICADO
    }

    public static enum TAREA_SINCRONIZACION {
        ANULAR_AEROVALE,
        CERRAR_PEDIDO,
        CREAR_CLIENTE,
        CREAR_AERONAVE
    }

    public static enum EVENTO_PROCESS_ERROR {
        MOSTRAR_MENSAJE,
        SALIR_DE_APP,
        ABORTAR_EJECUCION_METODO,
        CONTINUAR_EJECUCION,
        ELIMINAR_AEV,
        REGRESAR_A_LISTADO_INICIAL,
        FACTURA_YA_EMITIDA,
        REMITO_YA_GUARDADO,
        FALLO_CONEXION_AFIP_REINTENTAR
    }

    public static enum TIPO_AEROVALE {
        AEROCOMBUSTIBLE,
        AEROESPECIALIDAD,
        COMBUSTIBLE,
        ALIJE,
        SERVICIO,
        DEVOLUCION
    }

    public static enum RESULTADO_PRUEBA_LABORATORIO {
        PENDIENTE,
        APROBADO,
        RECHAZADO
    }

    public static enum ACCION_CLIENTE_INGRESO_MATRICULA {
        SELECCIONAR_NORMALMENTE,
        MOSTRAR_CONFIRMACION_VEHICULO_PERTENECE_A_OTRO_CLIENTE,
        ENVIAR_A_VENTANTA_DE_CREACION_DE_VEHICULO
    }

    public static enum MODO_SELECCION_DISPOSITIVO {
        PRUEBA,
        USO_REAL
    }

    public static enum ESTADO_IMPRESION {
        NO_FORZAR_IMPRESION,
        FORZAR_IMPRESION_POR_FALLA_DE_CONEXION
    }

    public static enum TIPO_OBSERVACION {
        DESPACHO,
        ANULACION,
        DESPACHO_NO_REALIZADO
    }

    public static enum TIPO_MENSAJE {
        SUCCESS,
        ERROR,
        WARNING
    }

    public static enum ESTADO_TANQUE {
        REPOSO,
        RECEPCION,
        DESPACHO,
        INDETERMINADO
    }

    public static enum TIPO_MOVIMIENTO {
        DESPACHO,
        DESPACHO_INTERNO,
        AJUSTE_MANUAL,
        TRANSFERENCIA,
        REBOMBEO,
        ALIJE,
        PURGA_EN_SECO
    }

    public static enum TIPO_CONTENEDOR {
        INDETERMINADO,
        TANQUE,
        POLIDUCTO,
        CISTERNA,
        ABASTECEDORA,
        DEPOSITO
    }

    public static enum ESTADO_USUARIO {
        INHABILITADO,
        PENDIENTE_VALIDACIÓN,
        VALIDADO
    }

    public static enum ACCION_LOGIN {
        CONFIGURACION_INICIAL,
        AGUARDANDO_VALIDACION,
        ACCESO_HABILITADO,
        ACCESO_INHABILITADO
    }

    public static enum TIPO_USUARIO {
        OPERADOR,
        SUPERVISOR,
        ADMINISTRADOR,
        JEFEDEAEROPLANTA,
        LABORATORIO,
        TUTOR,
        COMERCIAL,
        DESPACHANTE,
        ADMCLIENTES,
        TRADERS,
        MANTENIMIENTO,
        MONITOREO,
        PRECIOS,
        APROBADORPRECIOS,
        APROBADORBYR,
        SEGURIDADTABLAS,
        CCOMERCIAL_LN,
        COMERCIAL_AV,
        FACTURACION18,
        IMPUESTOS,
        MANDATARIO,
        OPERADOR_3,
        PRODUCTOS,
        REFACTURACION,
        RESPRODUCTO
    }

    public static enum TIPO_PICO {
        Carter,
        Gatillo
    }

    public static enum ESTADO_FACTURA {
        CALCULADO,
        EMITIDO,
        IMPRESO,
        ANULADO,
        ERROR
    }


    public static enum TIPO_CAUDALIMETRO {

        INDETERMINADO,
        MECANICO,
        EMR3,
        EMR4,
        LCRIQ

    }

    public static enum TIPO_BLOB_CONTAINER {
        FIRMAOPERADOR,
        FIRMACONDUCTOR,
        FILES
    }

    public static enum TipoPedido {
        Manual,
        Alije,
        Tams
    }

    public static enum TIPO_SERVICIO {
        INDETERMINADO,
        ALIJE,
        RANCHO,
    }

    public enum TIPO_DOCUMENTO {
        DNI(96), CUIT(80), CUIL(86), EXTRANJERO(1);

        private final int val;

        private TIPO_DOCUMENTO(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static TIPO_DOCUMENTO getByValue(int val) {
            if (val == 96) {
                return DNI;
            } else if (val == 86) {
                return CUIL;
            } else if (val == 80) {
                return CUIL;
            } else {
                return EXTRANJERO;
            }
        }
    }

    public static String obtenerNombreBlobContainerStorage(TIPO_BLOB_CONTAINER blob) {
        if (blob == TIPO_BLOB_CONTAINER.FIRMAOPERADOR) {
            return "firmaoperador";
        } else if (blob == TIPO_BLOB_CONTAINER.FIRMACONDUCTOR) {
            return "firmaconductor";
        } else if (blob == TIPO_BLOB_CONTAINER.FILES) {
            return "files";
        }
        return "";
    }

    public static void mostrarWaitingDialog(Window w, RelativeLayout progressBar) {
        w.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);

    }

    public static void ocultarWaitingBar(Window w, RelativeLayout progressBar) {
        w.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.GONE);
    }

    public static enum REMOTE_RESPONSE {
        SUCCESSFUL,
        ERROR
    }

    public String obtenerDescripcionUnidadMedida(UNIDADES_MEDIDA unidad) {
        if (unidad == UNIDADES_MEDIDA.UNIDADES) {
            return "Unidades";
        } else if (unidad == UNIDADES_MEDIDA.LITROS) {
            return "Litros";
        } else if (unidad == UNIDADES_MEDIDA.KILOGRAMOS) {
            return "Kilogramos";
        } else if (unidad == UNIDADES_MEDIDA.LIBRAS) {
            return "Libras";
        }

        return "OTRA UNIDAD";
    }

    public static TIPO_PAGO obtenerEnumTipoPago(String tipoPago) {
        if (tipoPago.equals("Indeterminado")) {
            return TIPO_PAGO.INDETERMINADO;
        } else if (tipoPago.equals("Contado")) {
            return TIPO_PAGO.CONTADO;
        } else if (tipoPago.equals("CuentaCorriente")) {
            return TIPO_PAGO.CUENTA_CORRIENTE;
        } else if (tipoPago.equals("PrevioPago")) {
            return TIPO_PAGO.PREVIO_PAGO;
        } else if (tipoPago.equals("Donacion")) {
            return TIPO_PAGO.DONACION;
        } else if (tipoPago.equals("ConsumoInterno")) {
            return TIPO_PAGO.CONSUMO_INTERNO;
        }

        return TIPO_PAGO.INDETERMINADO;
    }

    public static String obtenerTipoPagoRemoto(TIPO_PAGO plazoPago) {
        if (plazoPago == TIPO_PAGO.INDETERMINADO) {
            return "Indeterminado";
        } else if (plazoPago == TIPO_PAGO.CONTADO) {
            return "Contado";
        } else if (plazoPago == TIPO_PAGO.CUENTA_CORRIENTE) {
            return "CuentaCorriente";
        } else if (plazoPago == TIPO_PAGO.PREVIO_PAGO) {
            return "PrevioPago";
        } else if (plazoPago == TIPO_PAGO.DONACION) {
            return "Donacion";
        } else if (plazoPago == TIPO_PAGO.CONSUMO_INTERNO) {
            return "ConsumoInterno";
        }

        return "OTRO PLAZO PAGO";
    }

    public static String obtenerDescripcionTipoPago(TIPO_PAGO plazoPago) {
        if (plazoPago == TIPO_PAGO.INDETERMINADO) {
            return "Indeterminado";
        } else if (plazoPago == TIPO_PAGO.CONTADO) {
            return "Contado";
        } else if (plazoPago == TIPO_PAGO.CUENTA_CORRIENTE) {
            return "Cuenta Corriente";
        } else if (plazoPago == TIPO_PAGO.PREVIO_PAGO) {
            return "Previo Pago";
        } else if (plazoPago == TIPO_PAGO.DONACION) {
            return "Donación";
        } else if (plazoPago == TIPO_PAGO.CONSUMO_INTERNO) {
            return "Consumo Interno";
        }

        return "OTRO PLAZO PAGO";
    }

    public static String obtenerTextoEnumEstado(ESTADO_AEROVALE estado) {
        if (estado == ESTADO_AEROVALE.PRECARGADO) {
            return "Precargado";
        } else if (estado == ESTADO_AEROVALE.DESPACHO_INICIADO) {
            return "DespachoIniciado";
        } else if (estado == ESTADO_AEROVALE.DESPACHO_FINALIZADO) {
            return "DespachoFinalizado";
        } else if (estado == ESTADO_AEROVALE.PENDIENTE) {
            return "Pendiente";
        } else if (estado == ESTADO_AEROVALE.FINALIZADO_SIN_FIRMA) {
            return "FinalizadoSinFirma";
        } else if (estado == ESTADO_AEROVALE.FINALIZADO_CON_FIRMA) {
            return "Finalizado";
        } else if (estado == ESTADO_AEROVALE.SINCRONIZADO) {
            return "Sincronizado";
        } else if (estado == ESTADO_AEROVALE.ANULADO) {
            return "Anulado";
        }

        return "OTRA ESTADO";
    }

    public static ESTADO_AEROVALE obtenerEnumEstado(String estadoString) {
        if (estadoString.equals("Precargado")) {
            return ESTADO_AEROVALE.PRECARGADO;
        } else if (estadoString.equals("DespachoIniciado")) {
            return ESTADO_AEROVALE.DESPACHO_INICIADO;
        } else if (estadoString.equals("DespachoFinalizado")) {
            return ESTADO_AEROVALE.DESPACHO_FINALIZADO;
        } else if (estadoString.equals("Pendiente")) {
            return ESTADO_AEROVALE.PENDIENTE;
        } else if (estadoString.equals("FinalizadoSinFirma")) {
            return ESTADO_AEROVALE.FINALIZADO_SIN_FIRMA;
        } else if (estadoString.equals("Finalizado")) {
            return ESTADO_AEROVALE.FINALIZADO_CON_FIRMA;
        } else if (estadoString.equals("Sincronizado")) {
            return ESTADO_AEROVALE.SINCRONIZADO;
        } else if (estadoString.equals("Anulado")) {
            return ESTADO_AEROVALE.ANULADO;
        }

        return ESTADO_AEROVALE.PRECARGADO;
    }

    public static String obtenerNombreEstado(ESTADO_AEROVALE estado) {
        if (estado == ESTADO_AEROVALE.PRECARGADO) {
            return "Precargado";
        } else if (estado == ESTADO_AEROVALE.DESPACHO_INICIADO) {
            return "Despacho Iniciado";
        } else if (estado == ESTADO_AEROVALE.DESPACHO_FINALIZADO) {
            return "Despacho Finalizado";
        } else if (estado == ESTADO_AEROVALE.PENDIENTE) {
            return "Pendiente";
        } else if (estado == ESTADO_AEROVALE.FINALIZADO_SIN_FIRMA) {
            return "Finalizado Sin Firma";
        } else if (estado == ESTADO_AEROVALE.FINALIZADO_CON_FIRMA) {
            return "Finalizado Con Firma";
        } else if (estado == ESTADO_AEROVALE.SINCRONIZADO) {
            return "Sincronizado";
        } else if (estado == ESTADO_AEROVALE.ANULADO) {
            return "Anulado";
        }

        return "OTRA ESTADO";
    }

    public static TIPO_VUELO obtenerEnumTipoVuelo(String tipoVueloString) {
        if (tipoVueloString.equals("Indeterminado")) {
            return TIPO_VUELO.INDETERMINADO;
        } else if (tipoVueloString.equals("Cabotaje")) {
            return TIPO_VUELO.CABOTAJE;
        } else if (tipoVueloString.equals("Internacional")) {
            return TIPO_VUELO.INTERNACIONAL;
        }

        return TIPO_VUELO.INDETERMINADO;
    }

    public static String obtenerNombreTipoVuelo(TIPO_VUELO tipoVuelo) {
        if (tipoVuelo == TIPO_VUELO.INDETERMINADO) {
            return "Indeterminado";
        } else if (tipoVuelo == TIPO_VUELO.CABOTAJE) {
            return "Cabotaje";
        } else if (tipoVuelo == TIPO_VUELO.INTERNACIONAL) {
            return "Internacional";
        }

        return "Indeterminado";
    }

    public static String obtenerDescripcionEstadoTanque(ESTADO_TANQUE estadoTanque) {
        if (estadoTanque == ESTADO_TANQUE.REPOSO) {
            return "Reposo";
        } else if (estadoTanque == ESTADO_TANQUE.RECEPCION) {
            return "Recepción";
        } else if (estadoTanque == ESTADO_TANQUE.DESPACHO) {
            return "Despacho";
        }

        return "OTRA ESTADO";
    }

    public static ArrayList<String> armarArrayUnidadesMedida() {
        ArrayList<String> listaUnidades = new ArrayList<String>();

        for (UNIDADES_MEDIDA unidad : UNIDADES_MEDIDA.values()) {
            listaUnidades.add(unidad.toString());
        }

        return listaUnidades;
    }

    public static ArrayList<String> armarArrayTipoPago() {
        ArrayList<String> tiposPago = new ArrayList<String>();

        for (TIPO_PAGO p : TIPO_PAGO.values()) {
            if (p != TIPO_PAGO.INDETERMINADO) {
                tiposPago.add(p.toString());
            }
        }

        return tiposPago;
    }

    public static ArrayList<String> armarArrayTipoPago(TIPO_PAGO tipoPagoCliente) {
        ArrayList<String> tiposPago = new ArrayList<String>();

        for (TIPO_PAGO p : TIPO_PAGO.values()) {
            if (p == TIPO_PAGO.CONTADO || (p != TIPO_PAGO.INDETERMINADO && p == tipoPagoCliente)) {
                tiposPago.add(p.toString());
            }
        }

        return tiposPago;
    }

    public static ArrayList<String> armarArrayEstados() {
        ArrayList<String> estados = new ArrayList<String>();

        estados.add("TODOS");

        for (ESTADO_AEROVALE e : ESTADO_AEROVALE.values()) {
            estados.add(obtenerNombreEstado(e));
        }

        return estados;
    }

    public static ArrayList<String> armarArrayResultadoPruebaLaboratorio() {
        ArrayList<String> listaResultadoPrueba = new ArrayList<String>();

        for (RESULTADO_PRUEBA_LABORATORIO r : RESULTADO_PRUEBA_LABORATORIO.values()) {
            listaResultadoPrueba.add(r.toString());
        }

        return listaResultadoPrueba;
    }

    public static int obtenerIconoDeEstado(int estado) {
        switch (ESTADO_AEROVALE.values()[estado]) {
            case PRECARGADO:
                return R.drawable.icono_precarga;
            case DESPACHO_INICIADO:
                return R.drawable.icono_despacho_iniciado;
            case DESPACHO_FINALIZADO:
                return R.drawable.icono_despacho_finalizado;
            case PENDIENTE:
                return R.drawable.icono_pendiente;
            case FINALIZADO_SIN_FIRMA:
                return R.drawable.icono_finalizado_sin_firma;
            case FINALIZADO_CON_FIRMA:
                return R.drawable.icono_finalizado_con_firma;
            case SINCRONIZADO:
                return R.drawable.icono_sincronizado;
            case ANULADO:
                return R.drawable.icono_anulado;
            default:
                return R.drawable.icono_estado_4;
        }
    }

    public static int obtenerIconoDeTipoAerovale(TIPO_AEROVALE tipoProducto) {

        switch (tipoProducto) {
            case AEROCOMBUSTIBLE:
                return R.drawable.icono_producto_aerocombustibles;
            case AEROESPECIALIDAD:
                return R.drawable.icono_producto_aeroespecialidades;
            case COMBUSTIBLE:
                return R.drawable.icono_producto_combustibles;
            case ALIJE:
                return R.drawable.icono_producto_alije;
            default:
                return R.drawable.icono_producto_indeterminado;
        }
    }

    public static String formatearFechaEnFechaHoraDto(Date fechaSql) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(fechaSql);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatearFechaEnFechaHoraCorta(Date fechaSql) {
        try {
            return new SimpleDateFormat("dd/MM/yy HH:mm").format(fechaSql);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatearFechaEnFechaCorta(Date fechaSql) {
        try {
            return new SimpleDateFormat("dd/MM/yy").format(fechaSql);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatearStringWsEnStringFechaCorta(String fechaHoraWs) {
        try {
            SimpleDateFormat dfWs = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date d = dfWs.parse(fechaHoraWs);

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
            return df.format(d);
        } catch (Exception e1) {
            try {
                SimpleDateFormat dfWs = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date d = dfWs.parse(fechaHoraWs);

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                return df.format(d);
            } catch (Exception e2) {
                return "";
            }
        }
    }

    public static String obtenerHoraDeFecha(Date fechaSql) {
        try {
            return new SimpleDateFormat("HH:mm").format(fechaSql);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatearStringWsEnStringFechaHora(String fechaHoraWs) {
        try {
            SimpleDateFormat dfWs = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date d = dfWs.parse(fechaHoraWs);

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
            return df.format(d);
        } catch (Exception e1) {
            try {
                SimpleDateFormat dfWs = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date d = dfWs.parse(fechaHoraWs);

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
                return df.format(d);
            } catch (Exception e2) {
                return "";
            }
        }
    }

    public static String formatearStringFechaEnStringWs(String fechaHora) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
            Date d = df.parse(fechaHora);
            SimpleDateFormat dfWs = new SimpleDateFormat("yyyy-MM-dd");
            return dfWs.format(d);
        } catch (Exception e1) {
            return "";
        }
    }

    public static Date parsearFechaWs(String fecha) {
        try {
            fecha = fecha.substring(0, 19);
            SimpleDateFormat dfWs = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return dfWs.parse(fecha);
        } catch (Exception e) {
            return null;
        }
    }


    public static Date parsearFechaCorta(String fecha) {
        try {
            SimpleDateFormat dfWs = new SimpleDateFormat("dd/MM/yy");
            return dfWs.parse(fecha);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatearCronometro(long time) {
        int d = 0, h = 0, m = 0, s = 0;

        h = (int) (time / 3600000);
        m = (int) (time - h * 3600000) / 60000;
        s = (int) (time - h * 3600000 - m * 60000) / 1000;

        String hh = h < 10 ? "0" + h : h + "";
        String mm = m < 10 ? "0" + m : m + "";
        String ss = s < 10 ? "0" + s : s + "";

        return hh + " hs " + mm + " min " + ss + " seg";
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean empty(String str) {
        if (str == null) return true;

        if (str.trim().isEmpty()) return true;

        if (str.equals(GUID_VACIO)) return true;

        return false;
    }

    public static boolean isDecimal(String num) {
        return num.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isDecimalPositivo(String num) {
        return num.matches("\\d+(\\.\\d+)?");
    }

    public static boolean isInteger(String num) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    public static boolean isLong(String num) {
        try {
            Long.parseLong(num);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    public static boolean isIntegerPositivo(String num) {
        if (!isInteger(num)) {
            return false;
        }

        if (Integer.parseInt(num) < 0) {
            return false;
        }

        return true;
    }

    public static void goToMainActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_RECARGAR_DATOS_MAIN_ACTIVITY, true);
        activity.startActivity(intent);
        activity.finish();
    }

    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);

        return bos.toByteArray();
    }

    public static boolean verificacionCuitCorrecta(String cuit) {
        boolean ret = false;

        if (cuit == null || cuit.trim().length() == 0) {
            return false;
        }

        String trimCuit = cuit.trim();

        try {
            int[] magicNumbers = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};
            int[] numbers = new int[11];
            int i;
            int sum = 0;

            trimCuit = trimCuit.replace("-", "");

            if (trimCuit.length() != 11) {
                return false;
            }

            for (i = 0; i < 11; i++) {
                numbers[i] = Integer.parseInt(trimCuit.substring(i, i + 1));
            }

            int verifDigit = numbers[10];

            for (i = 0; i < 10; i++) {
                sum = sum + numbers[i] * magicNumbers[i];
            }

            int div = sum / 11;
            int prod = div * 11;
            int diff = sum - prod;
            verifDigit = (diff > 0) ? 11 - diff : diff;

            ret = (verifDigit == numbers[i]);
        } catch (Exception e) {
            Log.e("verificacionCuitCorrecta", e.getMessage());
        }
        return ret;
    }

    public static boolean verificacionDniCorrecto(String dni) {

        if (!isIntegerPositivo(dni)) {
            return false;
        }

        if (dni.length() > 8) {
            return false;
        }

        return true;
    }

    public static boolean verificacionLongitudMatriculaCorrecta(String matricula) {

        if (matricula.length() <= 1) {
            return false;
        }

        if (matricula.length() > 10) {
            return false;
        }

        return true;
    }

    public static MultipartBody.Part crearDumyFile(File dir, String name) {
        File dumy = null;

        try {
            dumy = new File(dir, name + ".png");

            if (dumy.exists() && dumy.isFile()) {
                dumy.delete();
            }

            dumy.createNewFile();
        } catch (java.io.IOException ioEx) {
            Log.e("crearDumyFile", ioEx.getMessage());
        }

        return MultipartBody.Part.createFormData(name, dumy.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), dumy));
    }

    public static MultipartBody.Part crearMultipartImageFile(File dir, byte[] bytes, String name) {
        File img = null;

        try {
            img = new File(dir, name + ".png");

            if (img.exists() && img.isFile()) {
                img.delete();
            }

            img.createNewFile();

            OutputStream os = new FileOutputStream(img);
            os.write(bytes);
            os.close();

        } catch (java.io.IOException ioEx) {
            Log.e("crearDumyFile", ioEx.getMessage());
        }

        return MultipartBody.Part.createFormData(name, img.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), img));
    }

    public static RequestBody crearFormDataImageFile(File dir, byte[] bytes, String name) {

        MultipartBody requestBody = null;
        File img = null;

        try {
            img = new File(dir, name + ".png");

            if (img.exists() && img.isFile()) {
                img.delete();
            }

            img.createNewFile();

            OutputStream os = new FileOutputStream(img);
            os.write(bytes);
            os.close();

            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart(name, img.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), img));
            requestBody = builder.build();
        } catch (java.io.IOException ioEx) {
            Log.e("crearDumyFile", ioEx.getMessage());
        }

        return requestBody;
    }

    public static void mostrarMensajeErrorInteractivo(AppCompatActivity activity, String mensaje) {
        if (activity != null && mensaje != null) {
            CustomAlertDialog dlg = new CustomAlertDialog(activity, "Atención", mensaje);
            dlg.setType(TIPO_ALERT_DIALOG.ERROR);
            dlg.setInteractionType(TIPO_ALERT_DIALOG_INTERACTION.OK);
            dlg.show();
        }
    }

    public static void mostrarMensajeErrorDiscreto(Activity activity, String mensaje) {
        if (activity != null && mensaje != null) {
            Sneaker.with(activity)
                    .setTitle(mensaje)
                    .setDuration(3000)
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakWarning();
        }
    }

    public static void mostrarMensajeSuccessDiscreto(Activity activity, String mensaje) {
        mostrarMensajeSuccessDiscreto(activity, mensaje, 3000);
    }

    public static void mostrarMensajeSuccessDiscreto(Activity activity, String mensaje, int ms) {
        if (activity != null && mensaje != null) {
            Sneaker.with(activity)
                    .setTitle(mensaje)
                    .setDuration(ms)
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakSuccess();
        }
    }

    public static void mostrarMensajeSuccessInteractivo(AppCompatActivity activity, String mensaje) {
        if (activity != null && mensaje != null) {
            CustomAlertDialog dlg = new CustomAlertDialog(activity, "Mensaje:", mensaje);
            dlg.setType(TIPO_ALERT_DIALOG.SUCCESS);
            dlg.setInteractionType(TIPO_ALERT_DIALOG_INTERACTION.OK);
            dlg.show();
        }
    }

    public static void mostrarMensajeSuccessInteractivo(String mensaje) {
        mostrarMensajeSuccessInteractivo(MainActivity.getCurrentActivity(), mensaje);
    }

    public static void mostrarErrorConexionServidor(Activity activity, String mensaje) {
        Sneaker.with(activity)
                .setTitle("Error de conexión")
                .setDuration(3000)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .sneakWarning();
    }

//    public static String getSubtituloVentanasAerovales(){
//        return LoginViewModel.getUsuario().getAeroplanta().getNombre() + " - " + LoginViewModel.getUsuario().getAbastecedora().getNombre();
//    }
//
//    public static boolean temperaturaFueraDeRango(String temperatura){
//        Double temp = Double.valueOf(temperatura);
//
//        if(temp > 100){
//            return true;
//        }
//
//        if(temp < -50){
//            return true;
//        }
//
//        return false;
//    }
//
//    public static String densidadFueraDeRango(Producto producto, String densidad){
//
//        double d = Double.valueOf(densidad);
//        double topeInferior = producto.getDensidadMin();
//        double topeSuperior = producto.getDensidadMax();
//
//        if(d < topeInferior){
//            return " (Inf: " + topeInferior + " | Sup: " + topeSuperior + ")";
//        }
//
//        if(d > topeSuperior){
//            return " (Inf: " + topeInferior + " | Sup: " + topeSuperior + ")";
//        }
//
//        return null;
//    }

    public static String getExceptionMessage(Throwable e) {
        if (e.getMessage() != null) {
            return e.getMessage();
        }

        if (e.getStackTrace() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString(); // stack trace as a string
        }

        return "NO EXCEPTION MESSAGE";
    }

    public static String formatearNumeroConSeparadores(String num) {
        String pattern = "###,###,###.##";

        try {
            DecimalFormat myFormatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(Locale.ITALIAN));
            return myFormatter.format(Double.valueOf(num));
        } catch (Exception ex) {
            return "";
        }
    }

    public static String formatearNumeroConSeparadoresTresDecimales(String num) {
        Float densidad = Float.valueOf(num);
        return String.format("%.3f", Float.valueOf(num));
    }
}
//    public static Aeroplanta obtenerAeroplantaDelUsuario(Application application){
//        Aeroplanta aeroplanta;
//
//        if(LoginViewModel.getUsuario() != null){
//            aeroplanta = LoginViewModel.getUsuario().getAeroplanta();
//        }else{
//            SharedPreferences preferences = application.getSharedPreferences(Utiles.SHARED_PREFERENCES_FILE, MODE_PRIVATE);
//
//            aeroplanta = new Aeroplanta();
//            aeroplanta.setNombre(preferences.getString("aeroplantaNombre", ""));
//            aeroplanta.setCodigoAeroplanta(preferences.getString("aeroplantaCodigo", ""));
//        }
//
//        return aeroplanta;
//    }
//
//    public static Abastecedora obtenerAbastecedoraDelUsuario(Application application){
//        Abastecedora abastecedora;
//
//        if(LoginViewModel.getUsuario() != null){
//            abastecedora = LoginViewModel.getUsuario().getAbastecedora();
//        }else{
//            SharedPreferences preferences = application.getSharedPreferences(Utiles.SHARED_PREFERENCES_FILE, MODE_PRIVATE);
//
//            abastecedora = new Abastecedora();
//            abastecedora.setCodigoAbastecedora(preferences.getString("abastecedoraCodigo", ""));
//            abastecedora.setNombre(preferences.getString("abastecedoraNombre", ""));
//            abastecedora.setTipoProducto( TIPO_PRODUCTO.values()[preferences.getInt("abastecedoraTipoProducto", 0)] );
//            abastecedora.setNombreProducto(preferences.getString("abastecedoraNombreProducto", ""));
//            abastecedora.setProductoCodigo(preferences.getString("abastecedoraProductoCodigo", ""));
//        }
//
//        return abastecedora;
//    }
//
//    public static float convertirLitrosEnGalones(float litros){
//        return litros * FACTOR_LITRO_GALON;
//
//    }
//
//    public static float convertirLitrosEnKilogramos(float litros, float densidad){
//        return litros * densidad;
//    }
//
//    public static float convertirLitrosEnLibras(float litros, float densidad){
//        float kilos = convertirLitrosEnKilogramos(litros, densidad);
//        return kilos * FACTOR_KILO_LIBRA;
//    }
//
//    public static String obtenerCantidadDespachadaConvertida(int unidad, float litros, float densidad){
//
//        if(unidad == UNIDADES_MEDIDA.GALON.ordinal()){
//            return formatearNumeroConSeparadores(String.valueOf(Utiles.convertirLitrosEnGalones(litros))) + " GAL" ;
//        }else if(unidad == UNIDADES_MEDIDA.LIBRAS.ordinal()){
//            return formatearNumeroConSeparadores(String.valueOf(Utiles.convertirLitrosEnLibras(litros, densidad))) + " LBS";
//        }else if(unidad == UNIDADES_MEDIDA.KILOGRAMOS.ordinal()){
//            return formatearNumeroConSeparadores(String.valueOf(Utiles.convertirLitrosEnKilogramos(litros, densidad))) + " KGS";
//        }else{
//            return formatearNumeroConSeparadores(String.valueOf(litros)) + " LTS";
//        }
//    }
//
//    public static String obtenerNumeracionCompleta(String prefijo, String numero){
//        String p = String.format("%1$" + 3 + "s", prefijo).replace(' ', '0');
//        String n = String.format("%1$" + 5 + "s", numero).replace(' ', '0');
//
//        return p + n;
//    }
//
//    public static long floatToLong(float f){
//        return (long) f;
//    }
//
//    public static String toJson(Object obj){
//        return new Gson().toJson(obj);
//    }
//
//    public static AerovaleAerocombustible completarAevAerocombustibleConResponse(AerovaleAerocombustible a, AerovaleResponse r){
//
//        a.setGuid(r.getGuid());
//        a.setNumeroAerovale(r.getNumeroAerovale());
//        a.setEstado(Utiles.obtenerEnumEstado(r.getEstado()));
//        a.setAeronaveGuid(r.getAeronaveGuid());
//
//        return a;
//    }
//
//    public static AerovaleAlije completarAevAlijeConResponse(AerovaleAlije a, AerovaleResponse r){
//        a.setGuid(r.getGuid());
//        a.setNumeroAerovale(r.getNumeroAerovale());
//        a.setEstado(Utiles.obtenerEnumEstado(r.getEstado()));
//        a.setAeronaveGuid(r.getAeronaveGuid());
//        a.setPendientePost(false);
//
//        return a;
//    }
//
//    public static AerovaleAeroespecialidad completarAevAeroespecialidadConResponse(AerovaleAeroespecialidad a, AerovaleResponse r){
//
//        a.setGuid(r.getGuid());
//        a.setNumeroAerovale(r.getNumeroAerovale());
//        a.setEstado(Utiles.obtenerEnumEstado(r.getEstado()));
//        a.setVehiculoGuid(r.getAeronaveGuid());
//
//        return a;
//    }
//
//    public static AerovaleCombustible completarAevCombustibleConResponse(AerovaleCombustible a, AerovaleResponse r){
//
//        a.setGuid(r.getGuid());
//        a.setNumeroAerovale(r.getNumeroAerovale());
//        a.setEstado(Utiles.obtenerEnumEstado(r.getEstado()));
//        a.setAutomotorGuid(r.getAeronaveGuid());
//
//        return a;
//    }
//
//    public static Date obtenerLastUpdateTraders(Application application){
//        SharedPreferences preferences = application.getSharedPreferences(Utiles.SHARED_PREFERENCES_FILE, MODE_PRIVATE);
//        Long timestamp = preferences.getLong(SP_LAST_TRADERS_UPDATE, 0);
//
//        return timestamp == 0 ? null : new Date(timestamp);
//    }
//
//    public static Date obtenerLastUpdateRanchos(Application application){
//        SharedPreferences preferences = application.getSharedPreferences(Utiles.SHARED_PREFERENCES_FILE, MODE_PRIVATE);
//        Long timestamp = preferences.getLong(SP_LAST_RANCHOS_UPDATE, 0);
//
//        return timestamp == 0 ? null : new Date(timestamp);
//    }
//
//    public static String getURLForResource (int resourceId) {
//        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
//        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
//    }
//
//    public static String formatearSalidaVolumenAforadores(long longValue){
//        BigDecimal bigDecimalValue  = new BigDecimal(longValue).divide(new BigDecimal(10));
//
//        if( bigDecimalValue.signum() == 0 || bigDecimalValue.scale() <= 0 || bigDecimalValue.stripTrailingZeros().scale() <= 0 ){
//            // No tiene decimales
//            return String.format("%d", bigDecimalValue.longValue()); // .setScale(0, BigDecimal.ROUND_HALF_EVEN)
//        }else{
//            return String.format("%.1f", bigDecimalValue.floatValue()).replace(',', '.');
//        }
//    }
//
//    public static String formatearSalidaVolumenAforadores(double longValue){
//        BigDecimal bigDecimalValue  = new BigDecimal(longValue);
//
//        if( bigDecimalValue.signum() == 0 || bigDecimalValue.scale() <= 0 || bigDecimalValue.stripTrailingZeros().scale() <= 0 ){
//            // No tiene decimales
//            return String.format("%d", bigDecimalValue.longValue());
//        }else{
//            return String.format("%.1f", bigDecimalValue.floatValue()).replace(",", ".");
//        }
//    }
//
//    public static long codificarEntradaVolumenAforadores(double doubleValue){
//        return new BigDecimal(doubleValue).multiply(new BigDecimal(10)).longValue();
//    }
//    public static String obtenerAbreviaturaUnidadMedida(UNIDADES_MEDIDA unidad){
//        if(unidad == UNIDADES_MEDIDA.UNIDADES){
//            return "Unid.";
//        }else if(unidad == UNIDADES_MEDIDA.LITROS){
//            return "Lts.";
//        }else if(unidad == UNIDADES_MEDIDA.KILOGRAMOS){
//            return "Kg.";
//        }else if(unidad == UNIDADES_MEDIDA.LIBRAS){
//            return "Lb.";
//        }
//
//        return "-";
//    }
//
//    public static String obtenerAbreviaturaFacturaUnidadMedida(UNIDADES_MEDIDA unidadMedida){
//        if(unidadMedida == UNIDADES_MEDIDA.KILOGRAMOS){
//            return "KG";
//        }else if(unidadMedida == UNIDADES_MEDIDA.LIBRAS){
//            return "LB";
//        }else if(unidadMedida == UNIDADES_MEDIDA.GALON){
//            return "GAL";
//        }else if(unidadMedida == UNIDADES_MEDIDA.LITROS){
//            return "LT";
//        }else if(unidadMedida == UNIDADES_MEDIDA.UNIDADES){
//            return "UN";
//        }
//
//        return "-";
//    }
//
//    public static String obtenerDescripcionTipoDocumento(int tipoDoc){
//        try{
//            TIPO_DOCUMENTO tipoDocumento = TIPO_DOCUMENTO.getByValue(tipoDoc);
//
//            if(tipoDocumento == TIPO_DOCUMENTO.CUIL){
//                return "CUIL";
//            }else if(tipoDocumento == TIPO_DOCUMENTO.CUIT){
//                return "CUIT";
//            }else if(tipoDocumento == TIPO_DOCUMENTO.DNI){
//                return "DNI";
//            }else if(tipoDocumento == TIPO_DOCUMENTO.EXTRANJERO){
//                return "DOCUMENTO";
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//            return "";
//        }
//
//        return "";
//    }
//
//    public static boolean mailValido(String mail){
//        Pattern pattern = Pattern
//                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
//
//        Matcher mather = pattern.matcher(mail);
//
//        return mather.find();
//    }
//
//    public static String getNombreAbastecedora(){
//        return "\"" + LoginViewModel.getUsuario().getAbastecedora().getNombre() +  "\"";
//    }
//
//    public static boolean procesarCaudalimetro(){
//        return LoginViewModel.getUsuario().getAbastecedora().getTipoCaudalimetro() == TIPO_CAUDALIMETRO.LCRIQ;
//    }
//}
