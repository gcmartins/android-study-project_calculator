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

    private static String operator = "";
    private static Double memoryNumber = 0.0;
    private Button buttonDot;
    private Button buttonClear;

    private TextView visor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        opButtons = Arrays.asList((Button) findViewById(R.id.buttonSub),
                (Button) findViewById(R.id.buttonAdd),
                (Button) findViewById(R.id.buttonMult),
                (Button) findViewById(R.id.buttonDiv),
                (Button) findViewById(R.id.buttonEqual));
        buttonDot = (Button) findViewById(R.id.buttonDot);

        visor = (TextView) findViewById(R.id.textView1);
        visor.setText("0");
        buttonClear = (Button) findViewById(R.id.buttonClear);
        setNumericButtons();
        setButtonClear();
        setButtonDot();
        setOpButtons();
    }

    private void setNumericButtons() {
        for (Button button : numericButtons) {
            final CharSequence number = button.getText();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence visorText = visor.getText();
                    if (visorText == "0") {
                        visor.setText(number);
                    } else {
                        visor.setText(visorText.toString().concat(number.toString()));
                    }
                }
            });
        }
    }

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

    private void setButtonDot(){
        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence visorText = visor.getText();
                visor.setText(visorText.toString().concat("."));
            }
        });
    }

    private void setOpButtons(){
        for (Button button : opButtons){
            final String opString = button.getText().toString();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    memoryNumber = calculate();
                    DecimalFormat decimalFormat = new DecimalFormat("#.######");
                    if (opString.equals("=")){
                        visor.setText(decimalFormat.format(memoryNumber));
                    } else {
                        visor.setText("0");
                        operator = opString;
                    }
                }
            });
        }
    }

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
                return memoryNumber / visorNumber;
            case "x":
                operator = "";
                return memoryNumber * visorNumber;
            default:
                return visorNumber;
        }
    }

}