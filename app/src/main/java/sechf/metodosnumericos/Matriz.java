package sechf.metodosnumericos;

import android.content.Context;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

import static sechf.metodosnumericos.DibujaMatrices.fila;

/**
 * Created by Erick Chávez on 13/05/2017.
 */

public class Matriz {
    private ArrayList<ArrayList<Double>> datos;
    private int ancho;
    private int alto;

    private TableLayout matriz;

    public Matriz(int ancho, int alto, ArrayList<ArrayList<Double>> datos) {
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

    public Matriz(ArrayList<ArrayList<Double>> datos) {
        this.datos = datos;
        ancho = datos.get(0).size();
        alto = datos.size();
    }

    public TableLayout dibujaMatriz(Context context) {
        matriz = DibujaMatrices.dibujaMatrices(context, this);
        return matriz;
    }


    public String toString(Context context) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                sb.append(String.format("%.4f", datos.get(i).get(j)) + "\t\t");
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    public int[] getDimensiones() {
        int[] dimensiones = {ancho, alto};
        return dimensiones;
    }

    public ArrayList<ArrayList<Double>> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<ArrayList<Double>> datos) {
        this.datos = datos;
    }

    public void setDatos(double[][] datos) {
        ArrayList<ArrayList<Double>> datosArL = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> fila;

        for (int i = 0; i < alto; i++) {
            fila = new ArrayList<Double>();
            for (int j = 0; j < ancho; j++) {
                fila.add(datos[i][j]);
            }
            datosArL.add(fila);
        }

        this.datos = datosArL;
    }

    public void llenarMatriz(TableLayout dibujoMatriz) {
        TableRow fila;
        EditText et;
        ArrayList<ArrayList<Double>> filas = new ArrayList<>(dibujoMatriz.getChildCount());
        ArrayList<Double> columnas;

        for (int i = 0; i < dibujoMatriz.getChildCount(); i++) {
            fila = (TableRow) dibujoMatriz.getChildAt(i);
            columnas = new ArrayList<>(fila.getChildCount());

            for (int j = 0; j < fila.getChildCount(); j++) {
                et = (EditText) fila.getChildAt(j);
                columnas.add(Double.parseDouble(et.getText().toString()));

            }
            filas.add(columnas);
        }

        datos = filas;
    }

    public void llenarVacia() {
        datos = new ArrayList<>();
        ArrayList<Double> fila;

        for (int i = 0; i < alto; i++) {
            fila = new ArrayList<>();
            for (int j = 0; j < ancho; j++) {
                fila.add(0d);
            }
            datos.add(fila);
        }
    }

    public Matriz copy() {
        Matriz copia = new Matriz(ancho, alto);
        ArrayList<ArrayList<Double>> datosCopia = new ArrayList<>(datos.size());
        ArrayList<Double> fila;

        for (ArrayList<Double> listas : datos) {
            fila = new ArrayList<>(datos.get(0).size());
            for (Double val : listas) {
                fila.add(val);
            }
            datosCopia.add(fila);
        }
        copia.setDatos(datosCopia);

        return copia;
    }
}
