package sechf.metodosnumericos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import java.util.ArrayList;

public class GaussJordan extends Activity{
    private EditText tamanhoCampo;
    private int tam;

    private Matriz matriz;
    private TableLayout dibujoMatriz;
    private RelativeLayout espacioMatriz;
    private boolean matrizExistencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_gauss_jordan);
        dibujoMatriz = new TableLayout(this);
        tamanhoCampo = (EditText) findViewById(R.id.tamMatriz);
        espacioMatriz = (RelativeLayout) findViewById(R.id.generaMatriz);
        tam = 0;
        matrizExistencia = false;
    }

    public void crearMatriz(View view) {
        try {
            if (tam != Integer.parseInt(tamanhoCampo.getText().toString())) {
                tam = Integer.parseInt(tamanhoCampo.getText().toString());
                matrizExistencia = false;
                dibujoMatriz.setVisibility(View.INVISIBLE);
            }
            if (!matrizExistencia) {

                matriz = new Matriz(tam + 1, tam);
                matriz.llenarVacia();

                //double ar[][] = {{4.0, 1.0, 4.0, 1.0},{2.0, 3.0, 4.0, 1.0 },{1.0, 2.0, 3.0, 1.0}};
                double ar[][] = {{2.0, 1.0, -3.0, 5.0},{3.0, -2.0, 2.0, 6.0 },{5.0, -3.0, 1.0, 16.0}};
                matriz.setDatos(ar);

                dibujoMatriz = matriz.dibujaMatriz(this);
                dibujoMatriz.setVisibility(View.VISIBLE);
                ActionBar.LayoutParams param = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT);
                param.leftMargin = 0;
                param.topMargin = 600;
                param.gravity = Gravity.CENTER;
                this.addContentView(dibujoMatriz, param);
                espacioMatriz.addView(dibujoMatriz);
                matrizExistencia = true;
            }
        } catch (Exception e) {
        }
    }

    public void resolver(View view) {
        try {
            double[][]arr = getArray(matriz);
            actualizaMatriz();
            printMatrix(arr);
            dibujoMatriz = matriz.dibujaMatriz(this);
            printMatrix(arr);
            GaussJordan(arr);
        } catch (NullPointerException e) {
            System.out.println("No hay matriz para resolver.");
        }
    }

    private double[][] getArray(Matriz matriz) {
        int ancho = matriz.getDimensiones()[0];
        int alto = matriz.getDimensiones()[1];
        ArrayList<ArrayList<Double>> arrL = matriz.getDatos();
        double[][] arr = new double[alto][ancho];
        for (int i = 0; i<alto;i++){
            for (int j = 0; j<ancho; j++) {
                arr[i][j]=arrL.get(i).get(j);
            }
        }

        return arr;
    }
/*
    private void GaussJordan(double[][] array){
        double[][] a = array;
        int e = 1;
        int n = a.length;
        for (int j = 0; j < n; j++) {
            int p = j;
            double max = Math.abs(a[p][0]);
            // find pivot index p
            for (int i = j + 1; i < n; i++) {
                if (Math.abs(a[i][j]) > max) {
                    p = i;

                }

            }

            // interchange rows
            if (p > j) {
                double temp = 0;
                for (int i = 0; i < a[1].length; i++) {
                    // put row j value in temp
                    temp = a[j][i];
                    // move p into j
                    a[j][i] = a[p][i];
                    // put temp back into p
                    a[p][i] = temp;
                }
            }

            // Divide row j by A[j][j]
            double divisor = a[j][j];
            for ( int i = 0; i < a[j].length; i ++){
                a[j][i] = a[j][i]/divisor;
            }

//			 Subtract
            for (int i = 0; i < a.length; i++) {
                if (i != j) {
                    double multiple = a[i][j];
                    for (int x = 0; x < a[1].length; x++) {
                        a[i][x] = (a[i][x] - a[i][j] * a[j][x]);

                    }
                }
            }

            matriz.setDatos(a);
            dibujoMatriz = matriz.dibujaResultado(this);
            dibujoMatriz.setVisibility(View.VISIBLE);
            dibujoMatriz.setY(a.length*170+(j)*a.length*80);
            espacioMatriz.addView(dibujoMatriz);

        }
    }
*/
    private void GaussJordan(double[][]arr){
        int startColumn = 0;
        for (int row=0; row<arr.length; row++) {
            //if the number in the start column is 0, try to switch with another
            while (arr[row][startColumn]==0.0){
                boolean switched = false;
                int i=row;
                while (!switched && i<arr.length) {
                    if(arr[i][startColumn]!=0.0){
                        double[] temp = arr[i];
                        arr[i]=arr[row];
                        arr[row]=temp;
                        switched = true;
                    }
                    i++;
                }
                //if after trying to switch, it is still 0, increase column
                if (arr[row][startColumn]==0.0) {
                    startColumn++;
                }
            }
            //if the number isn't one, reduce to one
            if(arr[row][startColumn]!=1.0) {
                double divisor = arr[row][startColumn];
                for (int i=startColumn; i<arr[row].length; i++) {
                    arr[row][i] = arr[row][i]/divisor;
                }
            }
            //make sure the number in the start column of all other rows is 0
            for (int i=0; i<arr.length; i++) {
                if (i!=row && arr[i][startColumn]!=0) {
                    double multiple = 0-arr[i][startColumn];
                    for (int j=startColumn; j<arr[row].length; j++){
                        arr[i][j] +=
                                multiple*arr[row][j];
                    }
                }
            }
            startColumn++;


            matriz.setDatos(arr);
            dibujoMatriz = matriz.dibujaResultado(this);
            dibujoMatriz.setVisibility(View.VISIBLE);
            dibujoMatriz.setY(arr.length*170+(row)*arr.length*80);
            espacioMatriz.addView(dibujoMatriz);
        }
    }
//*/



    public void actualizaMatriz() {

        int ancho = matriz.getDimensiones()[0];
        int alto = matriz.getDimensiones()[1];


        EditText et;
        double[][]arr = getArray(matriz);

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                et = (EditText) findViewById(i*ancho + j);
                arr[i][j]=Double.parseDouble(et.getText().toString());
            }
        }

        matriz.setDatos(arr);
    }

    public void printMatrix(double[][]arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print("{");
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println("}");
        }
    }
}
