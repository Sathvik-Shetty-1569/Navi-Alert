package com.example.miniprojectsem5;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;


public class DescriptionFragment extends Fragment {
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    private CardView cardView5;
    private CardView cardView6;
    private ImageView back;
    EditText message;
    private CardSelectionViewModel cardSelectionViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_description, container, false);
        Button next = view.findViewById(R.id.buttonnext);
         back = view.findViewById(R.id.btnback);
         message = view.findViewById(R.id.editTextmessage);

back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        BottomNavigationView bottomNavigationView = ((IncidentActivity) getActivity()).getBottomNavigationView();
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.type);
        }
    }
});
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messages = message.getText().toString().trim();
                if (TextUtils.isEmpty(messages)) {
                    // Show a toast if the message is empty
                    Toast.makeText(requireContext(), "Please provide details about the incident before proceeding. ", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences sp = requireActivity().getSharedPreferences("incidece", MODE_PRIVATE);
                    String cardName = sp.getString("cardName", null);

                    if (cardName != null) {
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                            sendSMS(cardName);
                        } else {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
                        }

                        BottomNavigationView bottomNavigationView = ((IncidentActivity) getActivity()).getBottomNavigationView();
                        if (bottomNavigationView != null) {
                            bottomNavigationView.setSelectedItemId(R.id.comfirm);
                        }
                    } else {
                        Toast.makeText(requireContext(), "No card selected", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        cardView1 = view.findViewById(R.id.cardGunThreat);
        cardView2 = view.findViewById(R.id.cardFire);
        cardView3 = view.findViewById(R.id.cardBomb);
        cardView4 = view.findViewById(R.id.cardHazard);
        cardView5 = view.findViewById(R.id.cardWeather);
        cardView6 = view.findViewById(R.id.cardOther);
        TextView cardTextView = view.findViewById(R.id.titledescribe);

        cardSelectionViewModel = new ViewModelProvider(requireActivity()).get(CardSelectionViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            String cardName = args.getString("cardName");
            Log.d("DescriptionFragment12", "Card name: " + cardName);
            SharedPreferences sp = requireActivity().getSharedPreferences("incidece", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("cardName",cardName);
            editor.apply();
            if (cardName != null) {
                cardTextView.setText(cardName);
                highlightSelectedCard(cardName);
            } else {
                Log.e("DescriptionFragment", "Card name is null");
            }
        } else {
            SharedPreferences sp = requireActivity().getSharedPreferences("incidece", MODE_PRIVATE);
            String cardname = sp.getString("cardName",null);
            if(cardname != null){
                Log.e("DescriptionFragment44", cardname);
                cardTextView.setText(cardname);  // Set card name to the TextView (for demo)
                highlightSelectedCard(cardname);
            }else {
                Log.e("DescriptionFragment45", "Arguments are null");
            }
        }

        return view;
    }





    private void highlightSelectedCard(String cardName) {
        Log.e( "highlightSelectedCard: ",cardName );
        cardView1.setVisibility(View.VISIBLE);
        cardView2.setVisibility(View.GONE);
        cardView3.setVisibility(View.GONE);
        cardView4.setVisibility(View.GONE);
        cardView5.setVisibility(View.GONE);
        cardView6.setVisibility(View.GONE);
        switch (cardName) {
            case "cardGunThreat":
                cardView1.setVisibility(View.VISIBLE);
                break;
            case "cardFire":
                cardView2.setVisibility(View.VISIBLE);
                break;
            case "cardBomb":
                cardView3.setVisibility(View.VISIBLE);
                break;
            case "cardHazard":
                cardView4.setVisibility(View.VISIBLE);
                break;
            case "cardWeather":
                cardView5.setVisibility(View.VISIBLE);
                break;
            case "cardOther":
                cardView6.setVisibility(View.VISIBLE);
                break;
            default:
                Log.e("DescriptionFragment", "Invalid card name: " + cardName);
                break;
        }
        Log.e( "highlightSelectedCard:12 ",cardName );
    }

    private void sendSMS(String cardName) {
        SharedPreferences sp = requireActivity().getSharedPreferences("UserPrefs",MODE_PRIVATE);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("share_prefs",MODE_PRIVATE);
        String contact_no = sp.getString("contactNo","no contact number");
        String username = sharedPreferences.getString("username", "no User");
        String address = sp.getString("address", "No address available");
        String region = sp.getString("region", "No region available");
        String phone = "";
        String messages =  "User Details:\n" +
                "Name: " + username + "\n" +
                "Address: " + address + "\n" +
                "Contact No: " + contact_no + "\n" +
                "Region: " + region + "\n" +"User's Incident Report :"+message.getText().toString();

        switch (cardName) {
            case "cardGunThreat":
                phone = "8554895749";
                break;
            case "cardFire":
                phone = "8554895749";
                break;
            case "cardBomb":
                phone = "8554895749";
                break;
            case "cardHazard":
                phone = "8554895749";
                break;
            case "cardWeather":
                phone = "8554895749";
                break;
            case "cardOther":
                phone = "8554895749";
                break;
            default:
                Toast.makeText(requireContext(), "Invalid card selection", Toast.LENGTH_SHORT).show();
                return;
        }

        if (!messages.isEmpty()) {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(messages);
            smsManager.sendMultipartTextMessage(phone, null, parts, null, null);
            Toast.makeText(requireContext(), "SMS Sent Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Please provide information about the incident", Toast.LENGTH_SHORT).show();
        }
    }







}