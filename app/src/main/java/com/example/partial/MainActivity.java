package com.example.partial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_values, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = i;
                Log.v("TAG", String.valueOf(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public int toInt(Editable text) {
        return Integer.parseInt(String.valueOf(text));
    }
    EditText firstNr;
    EditText secondNr;
    String result;
    String typeString;
    public void calculeaza(View view) {
        firstNr = findViewById(R.id.firstNr);
        secondNr = findViewById(R.id.secondNr);
        Log.v("TAG", String.valueOf(toInt(firstNr.getText()) + toInt(secondNr.getText())));
        result = "";
        typeString = "";
        switch (type) {
            case 0:
                result = String.valueOf(toInt(firstNr.getText()) + toInt(secondNr.getText()));
                typeString = "+";
                break;
            case 1:
                result = String.valueOf(toInt(firstNr.getText()) - toInt(secondNr.getText()));
                typeString = "-";
                break;
            case 2:
                result = String.valueOf(toInt(firstNr.getText()) * toInt(secondNr.getText()));
                typeString = "*";
                break;
            case 3:
                if (toInt(secondNr.getText()) == 0) {
                    Toast toast = Toast.makeText(this, "Can't devide by 0", Toast.LENGTH_SHORT);
                    toast.show();
                    result = "INF";
                } else
                    result = String.valueOf(toInt(firstNr.getText()) / toInt(secondNr.getText()));
                typeString = "/";
                break;


        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultat");
        builder.setMessage(String.valueOf(firstNr.getText()) + typeString + String.valueOf(secondNr.getText()) + " = " + result);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("SHARE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("smsto:0740123456");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", String.valueOf(firstNr.getText()) + typeString + String.valueOf(secondNr.getText()) + " = " + result + " WAW");
                startActivity(it);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}