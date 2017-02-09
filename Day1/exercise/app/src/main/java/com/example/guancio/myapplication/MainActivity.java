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
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    ArrayList<String> members;
    private Vector<CharSequence> units2;
    private ArrayAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.source_unit);
        spinner.setOnItemSelectedListener(this);

        List<CharSequence> units = new Vector<CharSequence>();
        units.add("Meters");
        units.add("Miles");
        units.add("Feets");
        units.add("Kilograms");
        units.add("Pounds");
        units.add("Ounces");
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, units);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.destination_unit);

        units2 = new Vector<CharSequence>();
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, units2);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);

    }

    public void sendMessage(View view) {
        EditText input = (EditText) findViewById(R.id.edit_message);

        float value = Float.parseFloat(input.getText().toString());
        Spinner spinner = (Spinner) findViewById(R.id.source_unit);
        Spinner spinner2 = (Spinner) findViewById(R.id.destination_unit);

        String unit1 = (String) spinner.getItemAtPosition(spinner.getSelectedItemPosition());
        String unit2 = (String) spinner.getItemAtPosition(spinner2.getSelectedItemPosition());

        //float value2 = convert(unit1, unit2, )

        String message = input.getText().toString();
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
        
    }

    static String[] distances = {"Meters", "Miles", "Feets"};
    static String[] weights = {"Kilograms", "Pounds", "Ounces"};
    List<String> getCompliantMeasures(String unit) {
        List<String> dst = Arrays.asList(distances);
        List<String> wgt = Arrays.asList(weights);
        List<String> res = new Vector<String>();
        if (dst.contains(unit)) {
            res.addAll(dst);
        }
        else if (wgt.contains(unit)) {
            res.addAll(wgt);
        }
        res.remove(unit);
        return res;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String message = (String) parent.getItemAtPosition(position);
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();

        List<String> newList = getCompliantMeasures(message);
        units2.clear();
        units2.addAll(newList);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        String message = "Nothing selected";
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
