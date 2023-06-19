package com.example.helloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textResultado;
    private EditText editPeso, editAltura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResultado = findViewById(R.id.textResultado);
        editAltura = findViewById(R.id.editAltura);
        editPeso = findViewById(R.id.editPeso);
    }

    public void calcular(View view) {

        double peso = Double.parseDouble(editPeso.getText().toString());
        double altura = Double.parseDouble(editAltura.getText().toString());
        double resultado = peso / (altura * altura);

        if (resultado < 19) {
            textResultado.setText("Abaixo do peso: " + resultado);

        } else if (resultado <= 19 || resultado < 25) {
            textResultado.setText("Peso normal: " + resultado);

        } else if (resultado <= 25 || resultado < 30) {
            textResultado.setText("Sobrepeso: " + resultado);

        } else if (resultado <= 30 || resultado < 40) {
            textResultado.setText("Obesidade tipo 1: " + resultado);

        } else if (resultado >= 40) {
            textResultado.setText("Obesidade tipo 2: " + resultado);
        }

    }


}