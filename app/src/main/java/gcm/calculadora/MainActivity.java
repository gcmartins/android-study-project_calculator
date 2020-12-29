package gcm.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Button> numericButtons, opButtons;

    // Variavel que armazena a operacao
    private String operator = "";
    // Variavel que armezana o primeiro numero da operacao desejada
    private Double memoryNumber = 0.0;
    private Button buttonDot;
    private Button buttonClear;

    private TextView visor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lista dos botoes numericos
        numericButtons = Arrays.asList(
                (Button) findViewById(R.id.button0),
                (Button) findViewById(R.id.button1),
                (Button) findViewById(R.id.button2),
                (Button) findViewById(R.id.button3),
                (Button) findViewById(R.id.button4),
                (Button) findViewById(R.id.button5),
                (Button) findViewById(R.id.button6),
                (Button) findViewById(R.id.button7),
                (Button) findViewById(R.id.button8),
                (Button) findViewById(R.id.button9));

        // Lista dos botoes de operacao matematica
        opButtons = Arrays.asList((Button) findViewById(R.id.buttonSub),
                (Button) findViewById(R.id.buttonAdd),
                (Button) findViewById(R.id.buttonMult),
                (Button) findViewById(R.id.buttonDiv),
                (Button) findViewById(R.id.buttonEqual));

        // Botao do separador decimal
        buttonDot = (Button) findViewById(R.id.buttonDot);

        // Visor da calculadora
        visor = (TextView) findViewById(R.id.textView1);
        visor.setText("0");

        // Botao para limpar e reiniciar a calculadora
        buttonClear = (Button) findViewById(R.id.buttonClear);

        // Configura as acoes dos botoes
        setNumericButtons();
        setButtonClear();
        setButtonDot();
        setOpButtons();
    }

    // Configura os listeners para os botoes numericos
    private void setNumericButtons() {
        for (Button button : numericButtons) {
            final CharSequence number = button.getText();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String visorText = visor.getText().toString();
                    if (visorText.equals("0")) {
                        visor.setText(number);
                    } else {
                        visor.setText(visorText.concat(number.toString()));
                    }
                    try {
                        Double.parseDouble(visorText);
                    } catch (NumberFormatException e){
                        visor.setText(visorText);
                    }
                }
            });
        }
    }

    // Configura o listener para o botao buttonClear
    private void setButtonClear() {
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator = "";
                visor.setText("0");
                memoryNumber = 0.0;
            }
        });
    }

    // Configura o listener para o botao buttonDot
    private void setButtonDot(){
        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String visorText = visor.getText().toString();
                visor.setText(visorText.concat("."));
                try {
                    Double.parseDouble(visorText.concat("."));
                } catch (NumberFormatException e){
                    visor.setText(visorText);
                }

            }
        });
    }

    // Configura os listeners para os botoes de operacao
    private void setOpButtons(){
        for (Button button : opButtons){
            final String opString = button.getText().toString();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        memoryNumber = calculate();
                        DecimalFormat decimalFormat = new DecimalFormat("#.######");
                        if (opString.equals("=")){
                            visor.setText(decimalFormat.format(memoryNumber));
                        } else {
                            visor.setText("0");
                            operator = opString;
                        }

                    } catch (UnsupportedOperationException e){
                        visor.setText("Error: Division by 0!");
                    } catch (NumberFormatException e){
                        visor.setText("Reset calculator!");
                    }
                }
            });
        }
    }

    // Calcula a operacao retornando o resultado
    private Double calculate(){
        Double visorNumber = Double.parseDouble(visor.getText().toString());
        switch (operator){
            case "+":
                operator = "";
                return memoryNumber + visorNumber;
            case "-":
                operator = "";
                return memoryNumber - visorNumber;
            case "/":
                operator = "";
                if (visorNumber == 0.0){
                    throw new UnsupportedOperationException();
                }
                return memoryNumber / visorNumber;
            case "*":
                operator = "";
                return memoryNumber * visorNumber;
            default:
                return visorNumber;
        }
    }

}