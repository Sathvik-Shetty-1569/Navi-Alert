package com.example.miniprojectsem5;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.miniprojectsem5.databinding.ActivityMainBinding;

public class IncidentActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private CardSelectionViewModel cardSelectionViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frame_layout);

        cardSelectionViewModel = new ViewModelProvider(this).get(CardSelectionViewModel.class);


        loadFragment(new TypeFragment(), false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.type) {
                    loadFragment(new TypeFragment(), false);
                } else if (itemId == R.id.describe) {
                         if((cardSelectionViewModel.getIsCard1Selected().getValue() != null && cardSelectionViewModel.getIsCard1Selected().getValue())
                                 || (cardSelectionViewModel.getIsCard2Selected().getValue() != null && cardSelectionViewModel.getIsCard2Selected().getValue())
                                 || (cardSelectionViewModel.getIsCard3Selected().getValue() != null && cardSelectionViewModel.getIsCard3Selected().getValue())
                                 || (cardSelectionViewModel.getIsCard4Selected().getValue() != null && cardSelectionViewModel.getIsCard4Selected().getValue())
                                 || (cardSelectionViewModel.getIsCard5Selected().getValue() != null && cardSelectionViewModel.getIsCard5Selected().getValue())
                                 || (cardSelectionViewModel.getIsCard6Selected().getValue() != null && cardSelectionViewModel.getIsCard6Selected().getValue())){
                             loadFragment(new DescriptionFragment(), false);

                         }
                         else{
                             Toast.makeText(getApplicationContext(),"Please select an incident before proceeding.",Toast.LENGTH_SHORT).show();
                             return false;
                         }

                } else {
                    loadFragment(new ComfirmFragment(), false);
                }

                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, new TypeFragment()) // Replace with the container ID and initial fragment
                    .commit();
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame_layout);

        if (currentFragment instanceof DescriptionFragment) {
            // Navigate back to TypeFragment
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, new TypeFragment()) // Replace with your TypeFragment instance
                    .commit();
        } else if (currentFragment instanceof ComfirmFragment) {
            // Navigate to HomeActivity
            Intent intent = new Intent(IncidentActivity.this, HomeActivity.class); // Replace HomeActivity with your actual home activity class
            startActivity(intent);
            finish(); // Optional: Remove IncidentActivity from the back stack
        } else {
            // Default behavior for other cases
            super.onBackPressed();
        }
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, fragment);

        fragmentTransaction.commit();
    }
}
