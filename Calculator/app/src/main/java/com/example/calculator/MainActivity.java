package com.example.calculator;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.view.WindowInsetsCompat.Type;

public class MainActivity extends AppCompatActivity {

    private double n1 = 0;
    private double n2 = 0;
    private String operador = "";
    private TextView textViewScreen;
    private String input = "";
    private String inputn2 = "";
    private boolean error = false;
    private Switch switchUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        switchUnits = findViewById(R.id.switchUnits);

        this.textViewScreen = findViewById(R.id.textViewScreen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void BorrarPantalla(View v){
        this.input = "";
        this.inputn2 = "";
        this.textViewScreen.setText("0");
        this.n1 = 0;
        this.n2 = 0;
        this.operador = "";
        this.error = false;
    }

    public void RealizarOperacion(View v){
        if(this.operador.equals("/") && this.n2 == 0) {
            this.textViewScreen.setText("Error: Div 0");
            this.error = true;
            return;

        } else {
            if(this.operador.equals("/")) this.n1 /= this.n2;
            else if(this.operador.equals("x")) this.n1 *= this.n2;
            else if(this.operador.equals("-")) this.n1 -= this.n2;
            else this.n1 += this.n2;

            this.n2 = 0;
            this.operador = "";

            this.input = String.format("%.3f", this.n1);
            this.inputn2 = "";

            this.textViewScreen.setText(this.input);
        }
    }

    public void NumeroPulsado(View v){
        Button button = (Button) v;
        if(this.error) BorrarPantalla(v);

        if(this.operador.isEmpty()) {
            this.input += button.getText().toString();
            this.n1 = Double.parseDouble(this.input);
        }
        else {
            this.input += button.getText().toString();
            this.inputn2 += button.getText().toString();
            this.n2 = Double.parseDouble(this.inputn2);
        }
        this.textViewScreen.setText(this.input);
    }

    public void OperacionPulsada(View v) {
        Button button = (Button) v;

        if (this.error) BorrarPantalla(v);

        if (this.operador.isEmpty()) {
            this.operador = button.getText().toString();
            this.input += this.operador;
        } else {
            if (this.operador.equals("/") && this.n2 == 0) {
                this.textViewScreen.setText("Error: Div 0");
                this.error = true;
                return;
            }

            if (this.operador.equals("/")) this.n1 /= this.n2;
            else if (this.operador.equals("x")) this.n1 *= this.n2;
            else if (this.operador.equals("-")) this.n1 -= this.n2;
            else this.n1 += this.n2;

            this.n2 = 0;
            this.operador = button.getText().toString();

            this.input = String.format("%.3f", this.n1) + this.operador;
            this.inputn2 = "";
        }
        this.textViewScreen.setText(this.input);
    }


    public void TrigonometricaPulsada(View v) {
        Button button = (Button) v;
        if (this.error) BorrarPantalla(v);

        if (this.operador.equals("/") && this.n2 == 0) {
            this.textViewScreen.setText("Error: Div 0");
            this.error = true;
            return;
        }

        String function = button.getText().toString();
        boolean enRadianes = !this.switchUnits.isChecked();
        if (this.n2 != 0) RealizarOperacion(v);

        double angle = this.n1;
        if (enRadianes) angle = Math.toRadians(this.n1);

        if (function.equals("sin(x)")) this.n1 = Math.sin(angle);
        else if (function.equals("cos(x)")) this.n1 = Math.cos(angle);
        else this.n1 = Math.tan(angle);

        this.n2 = 0;
        this.operador = "";

        this.input = String.format("%.3f", this.n1);
        this.inputn2 = "";

        this.textViewScreen.setText(this.input);
    }
}
