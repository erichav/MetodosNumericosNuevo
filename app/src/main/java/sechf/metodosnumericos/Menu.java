package sechf.metodosnumericos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Menu extends Activity {
    public Button btnMetodoCramer;
    public Button btnMetodoBiseccion;
    public Button btnMetodoBairstow;
    public Button btnMetodoSecante;
    public Button btnMetodoGaussJordan;
    public Button btnMetodoNewtonInterpol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_menu);
        metodoBairstow();
        metodoBiseccion();
        metodoCramer();
        metodoSecante();
        metodoGaussJordan();
        metodoNewtonInterpol();
    }

    private void metodoBiseccion() {
        btnMetodoBiseccion = (Button)findViewById(R.id.btnBiseccion);
        btnMetodoBiseccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inti = new Intent(Menu.this, Biseccion.class);
                startActivity(inti);
            }
        });
    }

    private void metodoSecante() {
        btnMetodoSecante = (Button)findViewById(R.id.btnSecante);
        btnMetodoSecante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inti = new Intent(Menu.this, Secante.class);
                startActivity(inti);
            }
        });

    }

    private void metodoBairstow() {
        btnMetodoBairstow = (Button)findViewById(R.id.btnBairstow);
        btnMetodoBairstow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inti = new Intent(Menu.this, Bairstow.class);
                startActivity(inti);
            }
        });

    }

    private void metodoNewtonInterpol() {
        btnMetodoNewtonInterpol = (Button)findViewById(R.id.btnInterpol);
        btnMetodoNewtonInterpol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inti = new Intent(Menu.this, InterpolacionNewton.class);
                startActivity(inti);
            }
        });

    }

    private void metodoGaussJordan() {
        btnMetodoGaussJordan = (Button)findViewById(R.id.btnGaussJordan);
        btnMetodoGaussJordan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inti = new Intent(Menu.this, GaussJordan.class);
                startActivity(inti);
            }
        });

    }

    private void metodoCramer() {
        btnMetodoCramer = (Button)findViewById(R.id.btnCramer);
        btnMetodoCramer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inti = new Intent(Menu.this, Cramer.class);
                startActivity(inti);
            }
        });
    }
}
