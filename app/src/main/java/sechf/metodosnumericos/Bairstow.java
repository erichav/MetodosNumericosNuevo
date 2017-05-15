package sechf.metodosnumericos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Bairstow extends Activity{

    private Button btnCalcularBairstow;
    private EditText valores;
    private TextView resultados;
    private Toast toast;

    //variables para el método
    private double[] a = new double[20];
    private double[] b = new double[20];
    private double[] c = new double[20];
    private ArrayList<Double> raices;
    private int n;

    public Bairstow(){
        //Constructor vacio requerido
    }


    //Se crean botones, textViews, etc.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_bairstow);
        btnCalcularBairstow = (Button)findViewById(R.id.btnCalcularBairstow);
        valores = (EditText)findViewById(R.id.coeficientes);
        resultados = (TextView)findViewById(R.id.resultados);
        btnCalcularBairstow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    leerEntrada(valores.getText().toString());
                    System.out.println("Tamaño de a: " + a.length);
                    ArrayList<Double> prueba = new ArrayList<Double>();
                    for(int i = 0; i<n; i++){
                        System.out.print(a[i]+ " ");
                        prueba.add(a[i]);
                    }
                    System.out.println("");
                    while(prueba.size()>3) {
                        prueba = bairSimple(prueba, 0.00001);
                    }
                    raices = evaluarFuncion(prueba);
                    System.out.println("Raíces: " + raices.toString());
                    String res = "";
                    if(raices.size()>0) {
                        for(int i = 0; i < raices.size(); i++){
                            raices.set(i, Math.floor(raices.get(i) * 1e5) / 1e5);
                        }
                        res = raices.get(0).toString();
                        if(raices.size()>1){
                            for (int i = 1; i < raices.size(); i++) {
                                res += ", " + raices.get(i).toString();
                            }
                        }
                    }else{
                        res += "Raíces imaginarias :(";
                    }
                    resultados.setText(res);
                } catch(Exception e){
                    toast = Toast.makeText(getApplicationContext(), "Verifique los valores ingresados" ,
                            Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private void leerEntrada(String s) {
        String[] valoresSimples = s.split(",");
        n = valoresSimples.length;
        for(int i = 0; i < valoresSimples.length; i++){
            a[i] = Double.parseDouble(valoresSimples[i]);
        }
    }

    // Division sintetica doble
    public ArrayList<Double> divSinDoble(ArrayList<Double> coef, double r, double s){
        ArrayList<Double> nuevoArreglo = new ArrayList<Double>(coef.size());
        for(Double dab : coef){
            nuevoArreglo.add(dab);
        }
        for(int i = 1; i < nuevoArreglo.size(); i++){
            if(i<2) {
                System.out.print("De " + nuevoArreglo.get(i));
                nuevoArreglo.set(i, nuevoArreglo.get(i) + nuevoArreglo.get(i - 1) * r);
                System.out.println(" A " + nuevoArreglo.get(i));
            } else {
                System.out.print("De " + nuevoArreglo.get(i));
                nuevoArreglo.set(i,nuevoArreglo.get(i) + nuevoArreglo.get(i - 1) * r + nuevoArreglo.get(i - 2) * s);
                System.out.println(" A " + nuevoArreglo.get(i));
            }
        }
        return nuevoArreglo;
    }

    //Metodo de Bairstow
    public ArrayList<Double> bairSimple(ArrayList<Double> coef, double prec){
        ArrayList<Double> b = coef, c, fin = new ArrayList<Double>(),copia = coef;
        int j = 0;
        double delta, r=-1, s=-1, deltaR, deltaS;
        int lengthB = 0,lengthC = 0;
        while(Math.sqrt(Math.abs(Math.pow(b.get(b.size()-1),2)+Math.pow(b.get(b.size()-2),2)))>prec){
            j++;
            System.out.println("coef " + coef.toString() +" en corrida " +j);
            System.out.println("Condicion " + Math.sqrt(Math.abs(Math.pow(b.get(b.size()-1),2)
                    +Math.pow(b.get(b.size()-2),2))) + "fue mayor que " +prec);
            b = divSinDoble(coef, r, s);
            System.out.println("Hice b " + b.toString() +" con div y ahora c");
            c = divSinDoble(b,r,s);
            System.out.println("Hice c " + c.toString());
            lengthC = c.size();
            lengthB = b.size();
            delta = Math.pow(c.get(lengthC-3),2)-c.get(lengthC-4)*c.get(lengthC-2);
            deltaR = ((c.get(lengthC-4)*b.get(lengthB-1))-(c.get(lengthC-3)*b.get(lengthB-2)))/delta;
            deltaS = ((b.get(lengthB-2)*c.get(lengthC-2))-(b.get(lengthB-1)*c.get(lengthC-3)))/delta;
            r+=deltaR;
            s+=deltaS;
            System.out.println("delta " + delta + " r "+ r + " s " + s);
        }
        System.out.println("Condicion " + Math.sqrt(Math.abs(Math.pow(b.get(b.size()-1),2)+
                Math.pow(b.get(b.size()-2),2))));
        for(int i = 0; i < lengthB-2; i++){
            fin.add(b.get(i));
        }

        return fin;
    }

    public ArrayList<Double> evaluarFuncion(ArrayList<Double> arreglo){
        ArrayList<Double> raices = new ArrayList<Double>();
        double a,b,c,d;
        if(arreglo.size()==3){
            a= arreglo.get(0);
            b = arreglo.get(1);
            c = arreglo.get(2);
            d = b * b - 4 * a * c;

            if(d > 0){
                System.out.println("Roots are real and unequal");
                raices.add(( - b + Math.sqrt(d))/(2*a));
                raices.add((-b - Math.sqrt(d))/(2*a));
                System.out.println("First root is:"+raices.get(0));
                System.out.println("Second root is:"+raices.get(1));
            }
            else if(d == 0){
                System.out.println("Roots are real and equal");
                raices.add((-b+Math.sqrt(d))/(2*a));
                System.out.println("Root:"+raices.get(0));
            }
            else{
                System.out.println("Roots are imaginary");
            }
        } else if(arreglo.size() == 2){
            raices.add(-1*(arreglo.get(1)));
        }
        return raices;
    }
}