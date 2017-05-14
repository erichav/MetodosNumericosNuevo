package sechf.metodosnumericos;

import android.content.Context;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

/**
 * Created by Erick Chávez on 13/05/2017.
 */

public class Matriz {
    private ArrayList<ArrayList<Float>> datos;
    private int ancho;
    private int alto;

    private TableLayout matriz;

    public Matriz(int ancho, int alto, ArrayList<ArrayList<Float>> datos) {
        this.ancho = ancho;
        this.alto = alto;
        this.datos = datos;
    }

    public Matriz(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    public Matriz(int ancho) {
        this.ancho = ancho;
        this.alto = ancho;
    }

    public Matriz(ArrayList<ArrayList<Float>> datos) {
        this.datos = datos;
        ancho = datos.get(0).size();
        alto = datos.size();
    }

    public TableLayout dibujaMatriz(Context context) {
        matriz = DibujaMatrices.dibujaMatrices(context, this);
        return matriz;
    }

    public TableLayout dibujaResultado(Context context) {
        matriz = DibujaMatrices.dibujaResMatriz(context, this);
        return matriz;
    }

    public int[] getDimensiones() {
        int[] dimensiones = {ancho, alto};
        return dimensiones;
    }

    public ArrayList<ArrayList<Float>> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<ArrayList<Float>> datos) {
        this.datos = datos;
    }

    public void llenarMatriz(TableLayout dibujoMatriz) {
        TableRow fila;
        EditText et;
        ArrayList<ArrayList<Float>> filas = new ArrayList<>(dibujoMatriz.getChildCount());
        ArrayList<Float> columnas;

        for (int i = 0; i < dibujoMatriz.getChildCount(); i++) {
            fila = (TableRow) dibujoMatriz.getChildAt(i);
            columnas = new ArrayList<>(fila.getChildCount());

            for (int j = 0; j < fila.getChildCount(); j++) {
                et = (EditText) fila.getChildAt(j);
                columnas.add(Float.parseFloat(et.getText().toString()));

            }
            filas.add(columnas);
        }

        datos = filas;
    }

    public void llenarVacia() {
        datos = new ArrayList<>();
        ArrayList<Float> fila;

        for (int i = 0; i < alto; i++) {
            fila = new ArrayList<>();
            for (int j = 0; j < ancho; j++) {
                fila.add(0f);
            }
            datos.add(fila);
        }
    }

    public Matriz copy() {
        Matriz copia = new Matriz(ancho, alto);
        ArrayList<ArrayList<Float>> datosCopia = new ArrayList<>(datos.size());
        ArrayList<Float> fila;

        for (ArrayList<Float> listas : datos) {
            fila = new ArrayList<>(datos.get(0).size());
            for (Float val : listas) {
                fila.add(val);
            }
            datosCopia.add(fila);
        }
        copia.setDatos(datosCopia);

        return copia;
    }
}
