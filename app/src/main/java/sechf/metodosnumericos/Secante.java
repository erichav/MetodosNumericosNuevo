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
    private EditText tolerancia;
    private TextView resultados;
    private Toast toast;

    public Secante() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_secante);
        btnCalcular = (Button)findViewById(R.id.btnCalcularSecante);
        funcion = (EditText) findViewById(R.id.funcion);
        puntoInicial = (EditText) findViewById(R.id.ptoInicial);
        puntoFinal = (EditText) findViewById(R.id.ptoFinal);
        tolerancia = (EditText) findViewById(R.id.error);
        resultados = (TextView) findViewById(R.id.resultados);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secante(funcion, puntoInicial, puntoFinal, tolerancia);
            }
        });
    }

    public void secante(EditText func, EditText iniVal, EditText finVal, EditText tol){
        Expression j;
        double valIni = Double.parseDouble(puntoInicial.getText().toString());
        double valFini = Double.parseDouble(puntoFinal.getText().toString());
        double toler = Double.parseDouble(tolerancia.getText().toString());
        try{
            j = new ExpressionBuilder(func.toString())
                        .variables("x")
                        .build()
                        .setVariable("x", valIni);
            }catch(Exception e){
                System.out.println("Función inválida.");
                return;
            }

            double c = valIni;

            while(Math.abs(j.evaluate())>toler) {
                double iniEval = j.evaluate();

                j.setVariable("x", valFini);
                double finEval = j.evaluate();

                c = valFini - (finEval) * (valFini - valIni) / (finEval - iniEval);

                iniVal = finVal;
                valFini = c;
        }
            System.out.println(c);
    }
}
