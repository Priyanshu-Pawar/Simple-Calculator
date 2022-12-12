package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends Activity implements View.OnClickListener {

    TextView solution_TextView, result_TextView;
    private MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonAdd, buttonSubs,
            buttonMultiply, buttonDivision, buttonDEl, buttonEqual, buttonClear, buttonBracket, buttonPoint, buttonModulo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result_TextView = findViewById(R.id.result_TextView);
        solution_TextView = findViewById(R.id.solution_TextView);

        assignId(button0, R.id.button_zero);
        assignId(button1, R.id.button_one);
        assignId(button2, R.id.button_two);
        assignId(button3, R.id.button_three);
        assignId(button4, R.id.button_four);
        assignId(button5, R.id.button_five);
        assignId(button6, R.id.button_six);
        assignId(button7, R.id.button_seven);
        assignId(button8, R.id.button_eight);
        assignId(button9, R.id.button_nine);
        assignId(buttonAdd, R.id.button_addition);
        assignId(buttonSubs, R.id.button_subtract);
        assignId(buttonMultiply, R.id.button_multiply);
        assignId(buttonDivision, R.id.button_divide);
        assignId(buttonDEl, R.id.button_delete);
        assignId(buttonEqual, R.id.button_equalTo);
        assignId(buttonClear, R.id.button_c);
        assignId(buttonPoint, R.id.button_point);
        assignId(buttonModulo, R.id.button_modulo);
        assignId(buttonBracket, R.id.button_bracket);

    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solution_TextView.getText().toString();


        if (buttonText.equals("")) {
            solution_TextView.setText("");
        }
        if (buttonText.equals("AC")) {
            solution_TextView.setText("");
            result_TextView.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solution_TextView.setText(result_TextView.getText());
            return;
        }

        if (buttonText.equals("^")) {
            double d = Math.pow(Double.parseDouble(result_TextView.getText().toString()), Double.parseDouble(result_TextView.getText().toString()));
            String str = String.valueOf(d);
            if (str.endsWith(".0")) {
                str = str.replace(".0", "");
            }
            result_TextView.setText(String.valueOf(str));
             return;
        }
        if (buttonText.equals("DEL")) {
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
        } else {
            dataToCalculate += buttonText;
        }

        if (buttonText.equals("%")) {
            double d = Double.parseDouble(solution_TextView.getText().toString()) / 100;
            result_TextView.setText(String.valueOf(d));
            return;
        }
        solution_TextView.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("error")) {
            result_TextView.setText(finalResult);
        }

    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);

            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "error";
        }
    }

}
