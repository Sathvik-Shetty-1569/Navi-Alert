package com.example.miniprojectsem5;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Navigation_Bar_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.drawer_header);

        SharedPreferences sharedPreferences = getSharedPreferences("share_prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        TextView usernameTextView = findViewById(R.id.menu_username);
        usernameTextView.setText(username);

        SharedPreferences sp = getSharedPreferences("email_prefs", MODE_PRIVATE);
        String email = sp.getString("email", "email");
        TextView emailTextView = findViewById(R.id.menu_email);
        emailTextView.setText(email);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}
