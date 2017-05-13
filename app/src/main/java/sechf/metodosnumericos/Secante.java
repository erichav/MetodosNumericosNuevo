package sechf.metodosnumericos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import net.objecthunter.exp4j.*;

public class Secante extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secante);
        secante("sin(x)", 3, 4, 0.00001);
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
