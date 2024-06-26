package es.ieslavereda.calculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private double operando;
    private Operacion operacion;
    private TextView textView;
    private TextView textViewResultado;

    private CheckBox MostrarOpcionesDeshab;
    private LinearLayout opcionesDeshab;
    private Button buttonClearE;
    private Button buttonClear;
    private Button buttonEquals;
    private Button buttonSuma;
    private Button buttonResta;
    private Button buttonMulti;
    private Button buttonDiv;
    private CheckBox deshabSuma;
    private CheckBox deshabResta;
    private CheckBox deshabMulti;
    private CheckBox deshabDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState!=null){ // Si no es nulo hay que deserializar lo que ha guardado
            operando = savedInstanceState.getDouble("operando");
            //operacion = (Operacion) savedInstanceState.getSerializable("operacion");
            textView = (TextView) savedInstanceState.getSerializable("textView");
            textViewResultado = (TextView) savedInstanceState.getSerializable("textViewResultado");
        }

        MostrarOpcionesDeshab = findViewById(R.id.checkBox);
        opcionesDeshab = findViewById(R.id.opcionesDeshabilitar);
        deshabSuma = findViewById(R.id.checkBoxSumar);
        deshabResta = findViewById(R.id.checkBoxRestar);
        deshabMulti = findViewById(R.id.checkBoxMultiplicar);
        deshabDiv = findViewById(R.id.checkBoxDividir);

        buttonClearE = findViewById(R.id.buttonCE);
        buttonClear = findViewById(R.id.buttonAC);
        buttonEquals = findViewById(R.id.buttonEquals);

        buttonSuma = findViewById(R.id.buttonMas);
        buttonResta = findViewById(R.id.buttonMenos);
        buttonMulti = findViewById(R.id.buttonMulti);
        buttonDiv = findViewById(R.id.buttonDivid);

        textView = findViewById(R.id.textView);
        textViewResultado = findViewById(R.id.textViewResultado);

        buttonClear.setOnClickListener(view -> {
            textView.setText("0");
            operacion = null;
            operando = 0.0;
            textViewResultado.setVisibility(View.INVISIBLE);
        });
        buttonClearE.setOnClickListener(view -> {
            if (textView.getText().toString().equals("0") && operacion!=null){
                operacion=null;
                textViewResultado.setText(textViewResultado.getText().toString()
                        .substring(0,textViewResultado.getText().toString().length()-3));
            } else {
                if (textView.getText().toString().length()==1)
                    textView.setText("0");
                else
                    textView.setText(textView.getText().toString()
                            .substring(0,textView.getText().toString().length()-1));
            }
        });

        buttonEquals.setOnClickListener(view -> {
            if (operacion == null){
                Toast.makeText(this, "Selecciona primero una operación", Toast.LENGTH_SHORT).show();
            } else {
                textViewResultado.setVisibility(View.VISIBLE);
                switch (operacion) {
                    case SUMA:
                        operando=operando+Double.parseDouble(textView.getText().toString());
                        break;
                    case RESTA:
                        operando=operando-Double.parseDouble(textView.getText().toString());
                        break;
                    case MULTIPLICACION:
                        operando=operando*Double.parseDouble(textView.getText().toString());
                        break;
                    case DIVISION:
                        operando=operando/Double.parseDouble(textView.getText().toString());
                        break;
                }
                int operandoInt = (int)operando;
                if (operando%1==0.0)
                    textViewResultado.setText("Resultado: "+operandoInt);
                else
                    textViewResultado.setText("Resultado: "+operando);
                textView.setText("0");
                operacion=null;
            }
        });

        MostrarOpcionesDeshab.setOnClickListener(view -> {
            if (MostrarOpcionesDeshab.isChecked())
                opcionesDeshab.setVisibility(View.VISIBLE);
            else {
                opcionesDeshab.setVisibility(View.GONE);
            }
        });
    }
    public void onClickNumero(View view){
        Button button = (Button) view;
        if (textView.getText().toString().length()>=10){
            Toast.makeText(this, "Alcanzada la longitud máxima permitida", Toast.LENGTH_SHORT).show();
        } else {
            if (textView.getText().toString().equals("0") && !button.getText().toString().equals("."))
                textView.setText(button.getText().toString());
            else
                textView.setText(textView.getText().toString() + button.getText().toString());
        }
    }
    public void onClickOperacion(View view){
        Button button = (Button) view;

        if (button.getId()==R.id.buttonMas && deshabSuma.isChecked())
            return;
        if (button.getId()==R.id.buttonMenos && deshabResta.isChecked())
            return;
        if (button.getId()==R.id.buttonMulti && deshabMulti.isChecked())
            return;
        if (button.getId()==R.id.buttonDivid && deshabDiv.isChecked())
            return;

        if (operando==0.0)
            operando = Double.parseDouble(textView.getText().toString());
        if (operacion!=null && !textView.getText().toString().equals("0")){
            switch (operacion) {
                case SUMA:
                    operando = operando+Double.parseDouble(textView.getText().toString());
                    break;
                case RESTA:
                    operando = operando-Double.parseDouble(textView.getText().toString());
                    break;
                case MULTIPLICACION:
                    operando = operando*Double.parseDouble(textView.getText().toString());
                    break;
                case DIVISION:
                    operando = operando/Double.parseDouble(textView.getText().toString());
                    break;
            }
        }
        int operandoInt = (int)operando;
        switch (button.getText().toString()){
            case "+":
                operacion = Operacion.SUMA;
                if (operando%1==0.0)
                    textViewResultado.setText(operandoInt + " + ");
                else
                    textViewResultado.setText(operando + " + ");
                break;
            case "-":
                operacion = Operacion.RESTA;
                if (operando%1==0.0)
                    textViewResultado.setText(operandoInt + " - ");
                else
                    textViewResultado.setText(operando + " - ");
                break;
            case "*":
                operacion = Operacion.MULTIPLICACION;
                if (operando%1==0.0)
                    textViewResultado.setText(operandoInt + " * ");
                else
                    textViewResultado.setText(operando + " * ");
                break;
            case "/":
                operacion = Operacion.DIVISION;
                if (operando%1==0.0)
                    textViewResultado.setText(operandoInt + " / ");
                else
                    textViewResultado.setText(operando + " / ");
                break;
        }
        textViewResultado.setVisibility(View.VISIBLE);
        textView.setText("0");
    }
    public void onClickDeshab(View view){
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()){
            switch (checkBox.getText().toString()){
                case "Sumar":
                    buttonSuma.getBackground().setAlpha(80);
                    break;
                case "Restar":
                    buttonResta.getBackground().setAlpha(80);
                    break;
                case "Multiplicar":
                    buttonMulti.getBackground().setAlpha(80);
                    break;
                case "Dividir":
                    buttonDiv.getBackground().setAlpha(80);
                    break;
            }
        } else {
            switch (checkBox.getText().toString()){
                case "Sumar":
                    buttonSuma.getBackground().setAlpha(255);
                    break;
                case "Restar":
                    buttonResta.getBackground().setAlpha(255);
                    break;
                case "Multiplicar":
                    buttonMulti.getBackground().setAlpha(255);
                    break;
                case "Dividir":
                    buttonDiv.getBackground().setAlpha(255);
                    break;
            }
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putDouble("operando",operando);
        //outState.putSerializable("operacion",(Serializable) operacion);
        outState.putSerializable("textView",(Serializable) textView);
        outState.putSerializable("textViewResultado",(Serializable) textViewResultado);
    }
}