package com.example.guancio.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static String[] distances = {"Meters", "Miles", "Feet", "Inches"};
    static String[] weights = {"Kilograms", "Pounds", "Ounces", "Grains"};
    static String[] volumes = {"Liters", "Pint", "Fluid ounces", "Gallon"};

    Map<String,Float> conversions = new HashMap<String,Float>() {{
        put("Meters", 1.0f); put("Miles", 1/5280.f*3.28084f); put("Feet", 3.28084f); put("Inches", 3.28084f*12);
        put("Kilograms", 1.0f); put("Pounds", 1000f/28.349f/16); put("Ounces", 1000f/28.349f); put("Grains", 1000f/28.349f/16*7000);
        put("Liters", 1.0f); put("Pint", 1.0f/3.785f*8); put("Fluid ounces", 1.0f/3.785f*8*16); put("Gallon", 1.0f/3.785f);
    }};

    private Vector<CharSequence> targetUnits;
    private ArrayAdapter<String> targetAdapter;
    
/*

    ArrayList<String> members;
    
    private ArrayAdapter adapter2;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<CharSequence> units = new Vector<CharSequence>();
        units.addAll(Arrays.asList(distances));
        units.addAll(Arrays.asList(weights));
        units.addAll(Arrays.asList(volumes));

        Spinner spinnerSource = (Spinner) findViewById(R.id.source_unit);
        spinnerSource.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, units);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the targetAdapter to the spinner
        spinnerSource.setAdapter(adapter);

        Spinner spinnerTarget = (Spinner) findViewById(R.id.destination_unit);

        targetUnits = new Vector<CharSequence>();
        // Create an ArrayAdapter using the string array and a default spinner layout
        targetAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, targetUnits);
        // Specify the layout to use when the list of choices appears
        targetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the targetAdapter to the spinner
        spinnerTarget.setAdapter(targetAdapter);

    }

    public void sendMessage(View view) {
        EditText input = (EditText) findViewById(R.id.edit_measurement);

        float value = Float.parseFloat(input.getText().toString());
        Spinner spinnerSource = (Spinner) findViewById(R.id.source_unit);
        Spinner spinnerTarget = (Spinner) findViewById(R.id.destination_unit);

        String unitSource = (String) spinnerSource.getItemAtPosition(spinnerSource.getSelectedItemPosition());
        String unitTarget = (String) spinnerTarget.getItemAtPosition(spinnerTarget.getSelectedItemPosition());

        value = convert(value, unitSource, unitTarget);

        String message = String.valueOf(value );
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }


    private float getStdRatio(String unitSource) {
        return conversions.get(unitSource);
    }

    private float convert(float value, String unitSource, String unitTarget) {
        float sourceRatio = 1/getStdRatio(unitSource);
        float targetRatio = getStdRatio(unitTarget);
        return value * sourceRatio * targetRatio;
    }


    List<String> getCompliantMeasures(String unit) {
        List<String> dst = Arrays.asList(distances);
        List<String> wgt = Arrays.asList(weights);
        List<String> vlm = Arrays.asList(weights);

        List<String> res = new Vector<String>();
        if (dst.contains(unit))
            res.addAll(dst);
        else if (wgt.contains(unit))
            res.addAll(wgt);
        else if (vlm.contains(unit))
            res.addAll(wgt);

        res.remove(unit);
        return res;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedUnit = (String) parent.getItemAtPosition(position);
        List<String> newList = getCompliantMeasures(selectedUnit);
        targetUnits.clear();
        targetUnits.addAll(newList);
        targetAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
