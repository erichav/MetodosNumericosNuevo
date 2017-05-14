package sechf.metodosnumericos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

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
                dibujoMatriz = matriz.dibujaMatriz(this);
                dibujoMatriz.setVisibility(View.VISIBLE);
                ActionBar.LayoutParams param = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT);
                param.leftMargin = 320;
                param.topMargin = 260;
                param.gravity = Gravity.CENTER;
//                this.addContentView(dibujoMatriz, param);
                espacioMatriz.addView(dibujoMatriz);
                matrizExistencia = true;
            }
        } catch (Exception e) {
        }
    }

    public void resolver(View view) {
        try {
            matriz.llenarMatriz(dibujoMatriz);
            Operaciones.gauss(matriz);
            Intent intent = new Intent(this, GaussResult.class);
            startActivity(intent);
        } catch (NullPointerException e) {
            System.out.println("No hay matriz para resolver.");
        }
    }



    /*public static double[][] gaussJordan(double[][]arr){
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
        }
        return arr;
    }

    public static void printMatrix(double[][]arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print("{");
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println("}");
        }
    }*/
}
