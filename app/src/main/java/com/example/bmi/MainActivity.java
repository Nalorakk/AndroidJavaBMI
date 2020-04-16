package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    String name ="undefined";

    final static String nameVariableKey = "NAME_VARIABLE";
    final static String textViewTexKey = "TEXTVIEW_TEXT";

    private static final String PREFS_FILE = "Account";
    private static final String PREF_HEIGHT = "Height";
    private static final String PREF_WEIGHT = "Weight";
    private static final String PREF_AGE = "Age";
    private static final String PREF_FORMULA = "formula";
    private static final String PREF_PActivity = "PActivity";
    private static final String PREF_RESULT = "Result";
    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinners();
        Load();
        Button buttonCalc = findViewById(R.id.buttonColc);
        buttonCalc.setOnClickListener(this);
    }
   /* @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(nameVariableKey, name);
        TextView nameView = (TextView) findViewById(R.id.textResult);
        outState.putString(textViewTexKey, nameView.getText().toString());

        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        name = savedInstanceState.getString(nameVariableKey);
        String textViewText= savedInstanceState.getString(textViewTexKey);
        TextView nameView = (TextView) findViewById(R.id.textResult);
        nameView.setText(textViewText);
    }*/

    @Override
    protected void onPause(){
        super.onPause();
        Save();
    }
    public void spinners(){
        Spinner spinnerFormula = findViewById(R.id.formulaSpinner);
        ArrayAdapter<CharSequence> adapterFormula = ArrayAdapter.createFromResource(this, R.array.formula_item, android.R.layout.simple_spinner_item);
        adapterFormula.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFormula.setAdapter(adapterFormula);
        spinnerFormula.setOnItemSelectedListener(this);

        Spinner spinnerPhysicalActivity = findViewById(R.id.spinnerPhysicalActivity);
        ArrayAdapter<CharSequence> adapterPhysicalActivity = ArrayAdapter.createFromResource(this, R.array.ras_name, android.R.layout.simple_spinner_item);
        adapterPhysicalActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPhysicalActivity.setAdapter(adapterPhysicalActivity);
        spinnerPhysicalActivity.setOnItemSelectedListener(this);
    }
    public void Load(){
        EditText heightSave = findViewById(R.id.editHeight);
        EditText weightSave = findViewById(R.id.editWeight);
        EditText ageSave = findViewById(R.id.editAge);
        TextView resultSave = findViewById(R.id.textResult);
        Spinner formulaSave = findViewById(R.id.formulaSpinner);
        Spinner spinerLevelSave = findViewById(R.id.spinnerPhysicalActivity);
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        // получаем настройки
        String heightLoad = settings.getString(PREF_HEIGHT,"");
        String weightLoad = settings.getString(PREF_WEIGHT,"");
        String ageLoad = settings.getString(PREF_AGE,"");
        String resultLoad = settings.getString(PREF_RESULT,"");
        int formulaLoad = settings.getInt(PREF_FORMULA, 0);
        int levelALaod = settings.getInt(PREF_PActivity, 0);
        heightSave.setText(heightLoad);
        weightSave.setText(weightLoad);
        ageSave.setText(ageLoad);
        resultSave.setText(resultLoad);
        formulaSave.setSelection(formulaLoad);
        spinerLevelSave.setSelection(levelALaod);
    }
    public  void Save(){
        EditText heightSave = findViewById(R.id.editHeight);
        EditText weightSave = findViewById(R.id.editWeight);
        EditText ageSave = findViewById(R.id.editAge);
        Spinner formulaSave = findViewById(R.id.formulaSpinner);
        Spinner spinerLevelSave = findViewById(R.id.spinnerPhysicalActivity);
        TextView resultSave = findViewById(R.id.textResult);
        // сохраняем в настройках
        prefEditor = settings.edit();
        prefEditor.putString(PREF_HEIGHT, heightSave.getText().toString());
        prefEditor.putString(PREF_WEIGHT, weightSave.getText().toString());
        prefEditor.putString(PREF_AGE, ageSave.getText().toString());
        prefEditor.putString(PREF_RESULT, resultSave.getText().toString());
        prefEditor.putInt(PREF_FORMULA, formulaSave.getSelectedItemPosition());
        prefEditor.putInt(PREF_PActivity, spinerLevelSave.getSelectedItemPosition());
        prefEditor.apply();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*Toast.makeText(this, "Запись - "
                + parent.getSelectedItem().toString()
                + "\nПозиция - "
                + parent.getSelectedItemPosition(), Toast.LENGTH_LONG).show();*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        try {
            EditText height = findViewById(R.id.editHeight);
            EditText weight = findViewById(R.id.editWeight);
            EditText age = findViewById(R.id.editAge);
            double  heightd = Double.parseDouble(height.getText().toString());
            double  weightd = Double.parseDouble(weight.getText().toString());
            double  aged = Double.parseDouble(age.getText().toString());

            //double BMI =  Double.parseDouble(weight.getText().toString())/Math.pow((Double.parseDouble(height.getText().toString())/100),2);
            double BMI =  weightd/Math.pow((heightd/100),2);
            String BMI_Result = new BigDecimal(BMI).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
            String BMI_text="", BM_S;
            if(BMI < 16){
                BMI_text = "Выраженный дефицит массы тела";
            }else if(BMI > 16 & BMI < 18.5 ) {
                BMI_text = "Недостаточная масса тела";
            }else if(BMI > 18.5 & BMI < 25 ){
                BMI_text = "Нормальная масса тела";
            }else if(BMI > 25 & BMI < 30 ){
                BMI_text = "Избыточная масса тела (предожирение)";
            }else if(BMI > 30 & BMI < 35 ){
                BMI_text = "Ожирение 1-ой степени";
            }else if(BMI > 35 & BMI < 40 ){
                BMI_text = "Ожирение 2-ой степени";
            }else if(BMI > 40){
                BMI_text = "Ожирение 3-ой степени";
            }
            RadioButton rman = findViewById(R.id.RadioMan);
            RadioButton rwoman = findViewById(R.id.RadioWoman);
            Spinner FormulaS = findViewById(R.id.formulaSpinner);
            Spinner spinerLevelA = findViewById(R.id.spinnerPhysicalActivity);
            TextView textResult = findViewById(R.id.textResult);
            double levelActivity = 0;
            double fotmelaBM = 0 ;
            int result= 0;
            switch (spinerLevelA.getSelectedItemPosition()) {
                case  (0):
                    levelActivity = 1;
                    break;
                case  (1):
                    levelActivity = 1.2;
                    break;
                case  (2):
                    levelActivity = 1.375;
                    break;
                case  (3):
                    levelActivity = 1.4625;
                    break;
                case  (4):
                    levelActivity = 1.550;
                    break;
                case  (5):
                    levelActivity = 1.6375;
                    break;
                case  (7):
                    levelActivity = 1.725;
                    break;
                case  (8):
                    levelActivity = 1.9;
                    break;
            }
            switch (FormulaS.getSelectedItemPosition()){
                case (0):
                    if(rman.isChecked() == true ) {
                        fotmelaBM = Math.round((66.5 + (13.75 * weightd) + (5.003 * heightd) - (6.775 * aged)) * levelActivity);
                        result = (int) fotmelaBM;
                    } else {
                        fotmelaBM = Math.round((655.1 + (9.563 * weightd) + (1.85 * heightd) - (4.676 * aged)) * levelActivity);
                        result = (int) fotmelaBM;
                    }
                    break;
                case (1):
                    if(rman.isChecked() == true ) {
                        fotmelaBM = Math.round((10 * weightd + 6.25 * heightd - 5 * aged + 5) * levelActivity);
                        result = (int) fotmelaBM;
                    } else {
                        fotmelaBM = Math.round((10 * weightd + 6.25 * heightd - 5 * aged - 161) * levelActivity);
                        result = (int) fotmelaBM;
                    }
                    break;
                case (2):
                    if(rman.isChecked() == true ) {
                        fotmelaBM = Math.round((66 + (13.7 * weightd) + (5 * heightd) - (6.8 * aged)) * levelActivity);
                        result = (int) fotmelaBM;
                    } else {
                        fotmelaBM = Math.round((655 + (9.6 * weightd) + (1.8 * heightd) - (4.7 * aged)) * levelActivity);
                        result = (int) fotmelaBM;
                    }
                    break;
            }
            String resultText = "Ваш индекс массы тела: " + BMI_Result + " \n Данное значение ИМТ соответствует: \n" + BMI_text +"\nДля поддержания веса организму требуется: " +result + "ккал в день." ;
            textResult.setText(resultText);
        } catch (Exception e) {
            Toast.makeText(this, " Пустое значение " , Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(this, "Ваш ИМТ: "+BMI_S+" Данное значение ИМТ соответствует: " + BMI_text, Toast.LENGTH_SHORT).show();

    }
    public void onClickClear(View v) {
        EditText heightSave = findViewById(R.id.editHeight);
        EditText weightSave = findViewById(R.id.editWeight);
        EditText ageSave = findViewById(R.id.editAge);
        TextView resultSave = findViewById(R.id.textResult);
        Spinner formulaSave = findViewById(R.id.formulaSpinner);
        Spinner spinerLevelSave = findViewById(R.id.spinnerPhysicalActivity);
        heightSave.setText("");
        weightSave.setText("");
        ageSave.setText("");
        resultSave.setText("");
        formulaSave.setSelection(0);
        spinerLevelSave.setSelection(0);
    }
}
