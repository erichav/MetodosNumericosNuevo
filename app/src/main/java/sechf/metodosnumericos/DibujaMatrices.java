package sechf.metodosnumericos;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Erick Chávez on 13/05/2017.
 */

public class DibujaMatrices {
    public static TableLayout espacioMatriz;
    public static TableRow fila;


    public static TableLayout dibujaMatrices(Context context, Matriz matriz) {
        espacioMatriz = new TableLayout(context);
        espacioMatriz.setOrientation(LinearLayout.VERTICAL);
        fila = new TableRow(context);

        int ancho = matriz.getDimensiones()[0];
        int alto = matriz.getDimensiones()[1];

        for (int i = 0; i < alto; i++) {
            fila = new TableRow(context);
            for (int j = 0; j < ancho; j++) {
                EditText et = new EditText(context);
                et.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
                et.setVisibility(View.VISIBLE);
                et.setId((i + 1) * (j + 1));
                fila.addView(et);
                et.setText("0");
            }

            espacioMatriz.addView(fila);
        }

        return espacioMatriz;
    }

    public static TableLayout dibujaResMatriz(Context context, Matriz matriz) {
        espacioMatriz = new TableLayout(context);
        espacioMatriz.setOrientation(LinearLayout.VERTICAL);
        fila = new TableRow(context);

        int ancho = matriz.getDimensiones()[0];
        int alto = matriz.getDimensiones()[1];

        for (int i = 0; i < alto; i++) {
            fila = new TableRow(context);
            for (int j = 0; j < ancho; j++) {
                TextView tv = new TextView(context);
                tv.setInputType(InputType.TYPE_NULL);
                tv.setVisibility(View.VISIBLE);
                String salida = String.format("%.4f", matriz.getDatos().get(i).get(j));
                tv.setText(salida + "\t\t");
                fila.addView(tv);

            }

            espacioMatriz.addView(fila);
        }

        return espacioMatriz;
    }

}
