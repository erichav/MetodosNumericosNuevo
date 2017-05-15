package sechf.metodosnumericos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Erick Chávez on 13/05/2017.
 */

public class GaussResult  extends Activity{
    private static ArrayList<Object> steps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauss_jordan);

        dibujar(steps);
    }

    public void regresar(View view) {
        reset();
        Intent intent;
        intent = new Intent(this, GaussJordan.class);
        startActivity(intent);

    }

    private void reset() {
        //RelativeLayout fondo = (RelativeLayout)findViewById(R.id.generaMatriz);
        //fondo.clearDisappearingChildren();
        resetSteps();
    }

    public static void resetSteps() {
        steps.clear();
    }

    private void dibujar(ArrayList<Object> objetos) {
        //RelativeLayout fondo = (RelativeLayout) findViewById(R.id.generaMatriz);
        ActionBar.LayoutParams param;
        TableLayout tabla = new TableLayout(this);
        TableRow fila;
        int y;
        for (Object objeto : objetos) {
            fila = new TableRow(this);
            if (objeto instanceof Matriz) {
                Matriz matriz = (Matriz) objeto;
                y = matriz.getDimensiones()[1] * 20;

                TableLayout dibujoMatriz = matriz.dibujaResultado(this);

                dibujoMatriz.setVisibility(View.VISIBLE);
                param = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT);
                param.leftMargin = 40;
                param.topMargin = y;
                param.gravity = Gravity.CENTER;

                fila.setVisibility(View.VISIBLE);
                fila.addView(dibujoMatriz);
            } else if (objeto instanceof String) {
                String texto = (String) objeto;

                TextView tv = new TextView(this);

                tv.setText(texto);
                tv.setVisibility(View.VISIBLE);
                param = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT);
                param.leftMargin = 20;
                param.topMargin = 40;
                param.gravity = Gravity.CENTER;

                fila.setVisibility(View.VISIBLE);
                fila.addView(tv);
            }
            tabla.setVisibility(View.VISIBLE);
            tabla.addView(fila);

            fila = new TableRow(this);
            TextView espacio = new TextView(this);
            espacio.setText("\n");
            fila.addView(espacio);
            tabla.addView(fila);
        }
        //fondo.addView(tabla);
    }

    public static void agregarMatriz(Matriz m) {
        steps.add(m);
    }

    public static void agregarTexto(String s) { steps.add(s); }
}
