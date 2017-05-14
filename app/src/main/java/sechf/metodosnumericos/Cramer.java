package sechf.metodosnumericos;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cramer extends Activity {

    private Button agregar;
    private Button borrar;
    private Button calcular;
    private EditText valores;
    private TextView coeficientes;
    private TextView faltantes;
    private TextView resultados;
    private Toast toast;
    private ArrayList<ArrayList<Double>> ecuaciones;
    private Integer numIncognitas = 0;
    private Integer numCoeficientes = 0;

    public Cramer() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_cramer);
        ecuaciones = new ArrayList<ArrayList<Double>>();
        agregar = (Button) findViewById(R.id.btnAgregarValores);
        borrar = (Button) findViewById(R.id.btnBorrarValores);
        calcular = (Button) findViewById(R.id.btnCalcularCramer);
        valores = (EditText) findViewById(R.id.valores);
        faltantes = (TextView) findViewById(R.id.ecuacionesFaltantes);
        coeficientes = (TextView) findViewById(R.id.puntos);
        resultados = (TextView) findViewById(R.id.resultados);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    leerEntrada(valores.getText().toString());
                } catch(Exception e){
                    toast = Toast.makeText(getApplicationContext(), "Verifique valores ingresados" , Toast.LENGTH_LONG);
                    toast.show();
                }
                imprimirValores();
            }
        });
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarValores();
                imprimirValores();
            }
        });
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numIncognitas == 0) {
                    toast = Toast.makeText(getApplicationContext(), "Cálculos hechos", Toast.LENGTH_LONG);
                    toast.show();
                    resultados.setText(metodoDeCrammer(ecuaciones));
                    resultados.setMovementMethod(new ScrollingMovementMethod());
                }else if(numIncognitas < 0){
                    toast = Toast.makeText(getApplicationContext(), "Sobran coeficientes" , Toast.LENGTH_LONG);
                    toast.show();
                } else{
                    toast = Toast.makeText(getApplicationContext(), "Faltan coeficientes" , Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private void leerEntrada(String valores) throws Exception{
        ArrayList<Double> coef = new ArrayList<Double>();
        String[] valoresSimples = valores.split(",");
        for(int i = 0; i < valoresSimples.length; i++){
            coef.add(Double.parseDouble(valoresSimples[i]));
        }
        toast = Toast.makeText(getApplicationContext(), "Se agregaron valores" , Toast.LENGTH_LONG);
        toast.show();
        if (ecuaciones.size() == 0){
            numCoeficientes = coef.size();
            ecuaciones.add(coef);
            numIncognitas = ecuaciones.get(0).size()-2;
        }
        else if(coef.size() != numCoeficientes){
            toast = Toast.makeText(getApplicationContext(), "Verifique el número de coeficientes" +
                    "\nnúmero de coeficientes erróneo" , Toast.LENGTH_LONG);
            toast.show();
        } else{
            ecuaciones.add(coef);
            numIncognitas--;
        }
        faltantes.setText("Ecuaciones faltantes: "+ numIncognitas);
    }

    private void imprimirValores(){
        String res = "";
        for(ArrayList<Double> lista: ecuaciones){
            for(Double val: lista){
                res += val + ",";
            }
            res += "\n";
        }
        coeficientes.setText(res);
        coeficientes.setMovementMethod(new ScrollingMovementMethod());
    }

    private void eliminarValores(){
        if(ecuaciones.size() > 0) {
            numIncognitas++;
            ecuaciones.remove(ecuaciones.size() - 1);
            toast = Toast.makeText(getApplicationContext(), "Se eliminaron valores", Toast.LENGTH_LONG);
            toast.show();
            faltantes.setText("Ecuaciones faltantes: "+ numIncognitas);
        }
    }

    private ArrayList<ArrayList<Double>> matrizSencilla(ArrayList<ArrayList<Double>> matrizCompleta){
        ArrayList<ArrayList<Double>> matriz = new ArrayList<ArrayList<Double>>();
        for(ArrayList<Double> arreglo: matrizCompleta){
            ArrayList<Double> res = new ArrayList<Double>();
            for(int i = 0; i < arreglo.size()-1; i++){
                res.add(arreglo.get(i));
            }
            matriz.add(res);
        }
        return matriz;
    }

    private double determinante(ArrayList<ArrayList<Double>> matriz) {
        double[][] arr = new double[matriz.size()][matriz.size()];
        for(int i = 0; i < matriz.size(); i++){
            System.out.println("i= "+i);
            for(int j = 0; j < matriz.size(); j++){
                System.out.println("j= "+j);
                arr[i][j] = matriz.get(i).get(j);
            }
        }
        return detUtil(arr);
    }

    private double detUtil(double[][] arr) {
        double result = 0.0;
        if (arr.length == 1) {
            result = arr[0][0];
            return result;
        }
        if (arr.length == 2) {
            result = arr[0][0] * arr[1][1] - arr[0][1] * arr[1][0];
            return result;
        }
        for (int i = 0; i < arr[0].length; i++) {
            double temp[][] = new double[arr.length - 1][arr[0].length - 1];

            for (int j = 1; j < arr.length; j++) {
                for (int k = 0; k < arr[0].length; k++) {
                    if (k < i)
                        temp[j - 1][k] = arr[j][k];
                    else if (k > i)
                        temp[j - 1][k - 1] = arr[j][k];
                }
            }
            result += arr[0][i] * Math.pow(-1, (int) i) * detUtil(temp);
        }
        return result;
    }

    private String metodoDeCrammer(ArrayList<ArrayList<Double>> matrizCompleta){
        ArrayList<Double> resultados = new ArrayList<Double>();
        ArrayList<ArrayList<Double>> matrizSencilla = matrizSencilla(matrizCompleta);
        System.out.println("Otro");
        Double determinante = determinante(matrizSencilla);
        if(determinante == 0.0) {
            toast = Toast.makeText(getApplicationContext(), "No existe solución", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            for(int i = 0; i < matrizCompleta.size(); i++){
                matrizSencilla = new ArrayList<ArrayList<Double>>();
                for(int j = 0; j < matrizCompleta.size(); j++){
                    ArrayList<Double> temp = new ArrayList<Double>();
                    for(int k = 0; k < matrizCompleta.size(); k++){
                        if(k==i)
                            temp.add(matrizCompleta.get(j).get(matrizCompleta.size()));
                        else
                            temp.add(matrizCompleta.get(j).get(k));
                    }
                    matrizSencilla.add(temp);
                }
                System.out.println("Extra");
                resultados.add(determinante(matrizSencilla)/determinante);
            }
        }
        return resultadoCramer(resultados);
    }

    private String resultadoCramer(ArrayList<Double> valores){
        String res = "";
        for(Double val: valores){
            res += "X"+valores.indexOf(val)+"= "+val + "\n";
        }
        return res;
    }
}