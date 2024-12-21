package com.example.miniprojectsem5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Profile_Activity extends AppCompatActivity {
Spinner spn;
Button btn;
    EditText add;
    EditText cont_no;
    ImageView back;
    Boolean saved;

    String initialAddress, initialContactNo, initialRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);


        SharedPreferences sharedPreferences = getSharedPreferences("share_prefs", MODE_PRIVATE);
        saved = false;
        String username = sharedPreferences.getString("username", "User");
        TextView usernameTextView = findViewById(R.id.textview_username);
        usernameTextView.setText(username);

        btn = findViewById(R.id.button_Apply_changes);
        add = findViewById(R.id.editTextAddress);
        cont_no = findViewById(R.id.editTextContact);
        back = findViewById(R.id.btnback);
        spn = findViewById(R.id.spinner_profile_region);

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.spinnerlist,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spn.setAdapter(adapter);

        SharedPreferences sp = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        initialAddress = sp.getString("address", "");
        initialContactNo = sp.getString("contactNo", "");
        initialRegion = sp.getString("region", "");

        // Set fields to initial values
        add.setText(initialAddress);
        cont_no.setText(initialContactNo);
        setSpinnerToValue(spn, initialRegion);

        // Set saved to true if no initial changes
        saved = true;

        // Add listeners to track changes and update the `saved` flag accordingly
        add.addTextChangedListener(new ChangeWatcher());
        cont_no.addTextChangedListener(new ChangeWatcher());



        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkForChanges();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!saved) {
                    Toast.makeText(getApplicationContext(), "Save the changes", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(Profile_Activity.this, HomeActivity.class));
                }
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text1 = add.getText().toString().trim();
                String text2 = cont_no.getText().toString().trim();
                String spinnerSelection = spn.getSelectedItem().toString();

                if (text1.isEmpty() || text2.isEmpty() || spn.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Please complete all details!", Toast.LENGTH_SHORT).show();
                }  else if (text2.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Phone number must be 10 digits!", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("address", text1);
                    editor.putString("contactNo", text2);
                    editor.putString("region", spinnerSelection);
                    editor.apply();
                    saved = true;
                    Toast.makeText(getApplicationContext(), "Changes applied successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Profile_Activity.this, HomeActivity.class));
                }
            }
        });


        SharedPreferences spd = getSharedPreferences("UserPrefs",MODE_PRIVATE);
        String address = spd.getString("address","");
        String contactNo = spd.getString("contactNo", "");
        String region = spd.getString("region", "");

        add.setText(address);
        cont_no.setText(contactNo);
        if (region != null) {
            setSpinnerToValue(spn, region);
        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setSpinnerToValue(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        int spinnerPosition = adapter.getPosition(value);
        spinner.setSelection(spinnerPosition);
    }


    // Checks if current values differ from the initial values
    private void checkForChanges() {
        String currentAddress = add.getText().toString();
        String currentContactNo = cont_no.getText().toString();
        String currentRegion = spn.getSelectedItem().toString();

        saved = currentAddress.equals(initialAddress) && currentContactNo.equals(initialContactNo) && currentRegion.equals(initialRegion);
    }

    // TextWatcher to detect changes in EditTexts
    private class ChangeWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkForChanges();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }
}