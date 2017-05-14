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

import net.objecthunter.exp4j.*;

public class Secante extends Activity{
    private Button btnCalcular;
    private EditText funcion;
    private EditText puntoInicial;
    private EditText puntoFinal;
    private EditText error;
    private TextView resultados;
    private Toast toast;

    public Secante() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_secante);
        btnCalcular = (Button) findViewById(R.id.btnCalcularBiseccion);
        funcion = (EditText) findViewById(R.id.funcion);
        puntoInicial = (EditText) findViewById(R.id.ptoInicial);
        puntoFinal = (EditText) findViewById(R.id.ptoFinal);
        error = (EditText) findViewById(R.id.error);
        resultados = (TextView) findViewById(R.id.resultados);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //secante("sin(x)", 3, 4, 0.00001);
            }
        });
    }

    public static void secante(String func, double iniVal, double finVal, double tol){
        Expression j;
        try{
            j = new ExpressionBuilder(func)
                        .variables("x")
                        .build()
                        .setVariable("x", iniVal);
            }catch(Exception e){
                System.out.println("Función inválida.");
                return;
            }

            double c = iniVal;

            while(Math.abs(j.evaluate())>tol) {
                double iniEval = j.evaluate();

                j.setVariable("x", finVal);
                double finEval = j.evaluate();

                c = finVal - (finEval) * (finVal - iniVal) / (finEval - iniEval);

                iniVal = finVal;
                finVal = c;
        }
            System.out.println(c);
    }
}
