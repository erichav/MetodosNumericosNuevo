package sechf.metodosnumericos;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class InterpolacionNewton extends Activity {

    private Button agregarPunto;
    private Button eliminarPunto;
    private Button calcular;
    private EditText puntoX;
    private EditText puntoY;
    private TextView puntos;
    private TextView resultado;
    private ArrayList<ArrayList<Double>> coordenadas;
    private Toast toast;

    public InterpolacionNewton(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_interpolacion_newton);
        agregarPunto = (Button) findViewById(R.id.btnAgregarPto);
        eliminarPunto = (Button) findViewById(R.id.btnEliminarPto);
        calcular = (Button) findViewById(R.id.btnCalcularInterpol);
        puntoX = (EditText) findViewById(R.id.ptoX);
        puntoY = (EditText) findViewById(R.id.ptoY);
        puntos = (TextView) findViewById(R.id.puntos);
        resultado = (TextView) findViewById(R.id.resultados);
        agregarPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarValores(Double.parseDouble(puntoX.getText().toString()), Double.parseDouble(puntoY.getText().toString()));
                imprimirValores();
            }
        });
        eliminarPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarValores();
                imprimirValores();
            }
        });
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String res = interpolacionNewton(coordenadas);
                toast = Toast.makeText(getApplicationContext(), "Polinomio Generado" , Toast.LENGTH_LONG);
                toast.show();
                resultado.setText(res);
            }
        });
        coordenadas = new ArrayList<ArrayList<Double>>();
    }

    private void agregarValores(Double puntoX, Double puntoY){
        ArrayList<Double> puntosXY = new ArrayList<Double>(2);
        puntosXY.add(puntoX);
        puntosXY.add(puntoY);
        coordenadas.add(puntosXY);
        toast = Toast.makeText(getApplicationContext(), "Valor agregado" , Toast.LENGTH_LONG);
        toast.show();
    }

    private void imprimirValores(){
        String res = "";
        for(ArrayList<Double> lista: coordenadas){
            res += lista.get(0) + ", ";
            res += lista.get(1) + "\n";
        }
        puntos.setText(res);
    }

    private void eliminarValores(){
        if(coordenadas.size() > 0) {
            coordenadas.remove(coordenadas.size() - 1);
            toast = Toast.makeText(getApplicationContext(), "Valor eliminado", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private ArrayList<ArrayList<Double>> matrizConPuntos(ArrayList<ArrayList<Double>> puntos){
        ArrayList<ArrayList<Double>> matriz = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> linea;
        for(int i = 0; i < puntos.size(); i++){
            linea = new ArrayList<Double>(puntos.size()+1);
            for(int j = 0; j < puntos.size()+1; j++){
                if(j==0 || j == 1)
                    linea.add(puntos.get(i).get(j));
                else
                    linea.add(0.0);
            }
            matriz.add(linea);
        }
        return matriz;
    }

    private ArrayList<Double> multiplos(ArrayList<ArrayList<Double>> matriz){

        double[][] arr = new double[matriz.size()][matriz.size()+1];

        for(int i = 0; i < matriz.size(); i++){
            for(int j = 0; j < matriz.size(); j++){
                arr[i][j] = matriz.get(i).get(j);
            }
        }

        for(int j = 2; j < matriz.size() + 1; j++){
            for(int i = j-1; i < matriz.size(); i++){
                arr[i][j] = (arr[i][j-1]-arr[i-1][j-1])/(arr[i][0]-arr[i-(j-1)][0]);
            }
        }
		/*
		ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> lista;
		for(int i = 0; i < matriz.size(); i++){
			lista = new ArrayList<Double>(matriz.size()+1);
			for(int j = 0; j < matriz.size()+1; j++){
				lista.add(arr[i][j]);
			}
			temp.add(lista);
		}*/

        ArrayList<Double> multiplos = new ArrayList<Double>();

        for(int i = 0; i < matriz.size(); i++){
            multiplos.add(arr[i][i+1]);
        }

        return multiplos;
    }

    private String polinomio(ArrayList<Double> multiplos, ArrayList<ArrayList<Double>> puntos){
        String polinomio = Double.toString(multiplos.get(0));
        for(int i = 1; i < multiplos.size(); i++){
            polinomio += "+(" + Double.toString(multiplos.get(i))+"*";
            for(int j = 0; j < i; j++){
                polinomio += "(x-("+puntos.get(j).get(0)+"))";
                if(j+1 < i)
                    polinomio += "*";
            }
            polinomio += ")";
        }
        return polinomio;
    }

    private String interpolacionNewton(ArrayList<ArrayList<Double>> puntos){
        ArrayList<ArrayList<Double>> matrizCeros = matrizConPuntos(puntos);
        ArrayList<Double> multiplos = multiplos(matrizCeros);
        return polinomio(multiplos, puntos);
    }
}
