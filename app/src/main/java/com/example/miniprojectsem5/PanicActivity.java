package com.example.miniprojectsem5;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class PanicActivity extends AppCompatActivity {
    View police, ambulance, firebrigade;
    private VideoView videoView;
    private ImageView btn, back;
    private boolean isOn = false;
    private Handler handler = new Handler();
    private boolean isPressed = false;
    private MediaPlayer mediaPlayer;
    private FusedLocationProviderClient fusedLocationClient;
    private String selectedEmergency = "Police";  // Default emergency type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_panic);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.btn_bg_blender);
        videoView.setVideoURI(videoUri);

        showBottomSheetDialog();
        back = findViewById(R.id.btnback);
        btn = findViewById(R.id.imageviewbutton);
        mediaPlayer = MediaPlayer.create(PanicActivity.this, R.raw.click);

        btn.setOnTouchListener(new View.OnTouchListener() {
            private Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (isPressed) {
                        btn.setImageResource(R.drawable.on);
                        isOn = true;
                        videoView.setVisibility(View.GONE);  // Hide video after 5 seconds
                        mediaPlayer.stop();
                        sendEmergencyAlert();  // Send location and emergency type
                    }
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isPressed = true;
                        btn.setImageResource(R.drawable.off);
                        mediaPlayer.start();
                        videoView.setVisibility(View.VISIBLE);
                        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.setLooping(true);  // Enable looping for the video
                            }
                        });
                        videoView.start();
                        btn.bringToFront();
                        handler.postDelayed(runnable, 5000);  // 5-second delay
                        break;
                    case MotionEvent.ACTION_UP:
                        isPressed = false;
                        handler.removeCallbacks(runnable);
                        mediaPlayer.pause();
                        videoView.stopPlayback();
                        videoView.setVisibility(View.GONE);
                        if (!isOn) {
                            btn.setImageResource(R.drawable.off);
                        }
                        break;
                }
                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PanicActivity.this, HomeActivity.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PanicActivity.this);
        View sheetView = getLayoutInflater().inflate(R.layout.panicbottombar, null);
        bottomSheetDialog.setContentView(sheetView);

        police = sheetView.findViewById(R.id.LayoutPolice);
        ambulance = sheetView.findViewById(R.id.LayoutAmbulance);
        firebrigade = sheetView.findViewById(R.id.LayoutFireBrigade);

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedEmergency = "Police";
                bottomSheetDialog.dismiss();
            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedEmergency = "Ambulance";
                bottomSheetDialog.dismiss();
            }
        });

        firebrigade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedEmergency = "Fire Brigade";
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }

    private void sendEmergencyAlert() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS}, 1);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    SharedPreferences sp = getSharedPreferences("UserPrefs",MODE_PRIVATE);
                    SharedPreferences sharedPreferences = getSharedPreferences("share_prefs",MODE_PRIVATE);
                    String contact_no = sp.getString("contactNo","no contact number");
                    String username = sharedPreferences.getString("username", "no User");
                    String address = sp.getString("address", "No address available");
                    String region = sp.getString("region", "No region available");
                    String phone = "8554895749";
                    String message = "Emergency: " + selectedEmergency + "\n" +
                            "User Details:\n" +
                            "Name: " + username + "\n" +
                            "Address: " + address + "\n" +
                            "Contact No: " + contact_no + "\n" +
                            "Region: " + region + "\n" +
                            "Location: https://maps.google.com/?q=" + latitude + "," + longitude + "\n";

                    SmsManager smsManager= SmsManager.getDefault();
                    ArrayList<String> parts = smsManager.divideMessage(message);
                    smsManager.sendMultipartTextMessage(phone,null,parts,null,null);
                    Toast.makeText(PanicActivity.this, "Sending alert to " + selectedEmergency + ": " + message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PanicActivity.this, HomeActivity.class));




                } else {
                    Toast.makeText(PanicActivity.this, "Unable to get location!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PanicActivity.this, HomeActivity.class));
                }
            }
        });
    }

    @Override

    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0) {
                boolean locationGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean smsGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (locationGranted && smsGranted) {
                    sendEmergencyAlert();
                } else {
                    Toast.makeText(this, "Location or SMS permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
