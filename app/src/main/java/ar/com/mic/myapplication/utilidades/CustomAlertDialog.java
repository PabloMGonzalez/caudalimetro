package ar.com.mic.myapplication.utilidades;

import android.app.Dialog;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ar.com.mic.myapplication.R;

public class CustomAlertDialog {

    final Dialog dialog ;

    DialogInterface.OnClickListener listener = null;

    public CustomAlertDialog(AppCompatActivity activity, String titulo, String mensaje){

        dialog = new Dialog(activity);

        dialog.setContentView(R.layout.activity_alert_dialog);
        dialog.getWindow().setAttributes(getLayoutParams(activity));
        dialog.setCancelable(false);

        TextView txtTitulo = (TextView) dialog.findViewById(R.id.txtTitulo);
        txtTitulo.setText(titulo);

        TextView txtMensaje = (TextView) dialog.findViewById(R.id.txtMensaje);
        txtMensaje.setText(mensaje);

        Button btnAceptar = (Button) dialog.findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) listener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                dialog.dismiss();
            }
        });

        Button btnCancelar = (Button) dialog.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) listener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                dialog.dismiss();
            }
        });
    }

    public void setButtonTexts(String btnOkText, String btnCancelText){
        Button btnAceptar = (Button) dialog.findViewById(R.id.btnAceptar);
        btnAceptar.setText(btnOkText);

        Button btnCancelar = (Button) dialog.findViewById(R.id.btnCancelar);
        btnCancelar.setText(btnCancelText);
    }

    private WindowManager.LayoutParams getLayoutParams(AppCompatActivity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.9f);

        layoutParams.width = dialogWindowWidth;

        return layoutParams;
    }

    public void setInteractionType(Utiles.TIPO_ALERT_DIALOG_INTERACTION tipoInteraccion){
        if(tipoInteraccion == Utiles.TIPO_ALERT_DIALOG_INTERACTION.OK){
            Button btnCancelar = (Button) dialog.findViewById(R.id.btnCancelar);
            btnCancelar.setVisibility(View.GONE);
        }
    }

    public void setType(Utiles.TIPO_ALERT_DIALOG tipoDialogo){
        ImageView imgIcono = (ImageView) dialog.findViewById(R.id.imgIcono);

        RelativeLayout fondoResaltado = (RelativeLayout) dialog.findViewById(R.id.fondoResaltado);

        if(tipoDialogo == Utiles.TIPO_ALERT_DIALOG.INFO){
            fondoResaltado.setBackgroundResource(R.color.dialogo_info);
            imgIcono.setImageResource(R.drawable.dialogo_info_icon);
        }else if(tipoDialogo == Utiles.TIPO_ALERT_DIALOG.SUCCESS){
            fondoResaltado.setBackgroundResource(R.color.dialogo_success);
            imgIcono.setImageResource(R.drawable.dialogo_success_icon);
        }else if(tipoDialogo == Utiles.TIPO_ALERT_DIALOG.WARNING){
            fondoResaltado.setBackgroundResource(R.color.dialogo_warning);
            imgIcono.setImageResource(R.drawable.dialogo_warning_icon);
        }else if(tipoDialogo == Utiles.TIPO_ALERT_DIALOG.ERROR){
            fondoResaltado.setBackgroundResource(R.color.dialogo_error);
            imgIcono.setImageResource(R.drawable.dialogo_error_icon);
        }
    }

    public void setListener(DialogInterface.OnClickListener listener){
        this.listener = listener;
    }

    public void show(){
        dialog.show();
    }
}
