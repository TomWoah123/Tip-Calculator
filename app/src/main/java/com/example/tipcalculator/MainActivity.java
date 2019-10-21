package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
{
    private EditText bill;
    private Spinner tipPercent;
    private static final int MAX_TIP = 100;
    private TextView totalBill;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bill = findViewById( R.id.billAmount );
        create();
    }

    public void create()
    {
        tipPercent = findViewById( R.id.spinner );
        List<String> tips = new ArrayList<>();
        for ( int tip = 15; tip <= MAX_TIP; tip++ )
        {
            tips.add( String.valueOf( tip ) );
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item, tips );
        tipPercent.setAdapter( adapter );
        bill = findViewById( R.id.billAmount );
        bill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getTotalBill();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tipPercent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getTotalBill();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getTotalBill()
    {
        try
        {
            bill = findViewById( R.id.billAmount);
            if ( bill.getText().toString().isEmpty() || bill.getText().toString().equals( "." ) )
            {
                totalBill = findViewById( R.id.total );
                totalBill.setText( "Total Amount: " );
            }
            List<String> num = new ArrayList<>( Arrays.asList( bill.getText().toString().split( "\\." ) ) );
            if ( num.size() == 2 )
            {
                String afterDec = num.get( 1 );
                if ( afterDec.length() > 2 )
                {
                    String twoDec = afterDec.substring( 0, 2 );
                    bill.setText( num.get( 0 ) + "." + twoDec );
                    bill.setSelection( bill.getText().toString().length() );
                }
            }
            Double billAmount = Double.parseDouble( bill.getText().toString() );
            Double tip = Double.parseDouble( tipPercent.getSelectedItem().toString() );
            Double total = billAmount * ( 1 + tip / 100 );
            DecimalFormat df = new DecimalFormat( "0.00" );
            totalBill = findViewById( R.id.total );
            totalBill.setText( "Total Amount: $" + df.format( total ) );
        }
        catch( Exception e )
        {
            System.out.print( e );
        }
    }
}