package com.example.miniprojectsem5;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TypeFragment extends Fragment {
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    private CardView cardView5;
    private CardView cardView6;
    private CardSelectionViewModel cardSelectionViewModel;
    private ImageView back;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_type, container, false);
        back = view.findViewById(R.id.btnback);
        cardView1 = view.findViewById(R.id.cardGunThreat);
        cardView2 = view.findViewById(R.id.cardFire);
        cardView3 = view.findViewById(R.id.cardBomb);
        cardView4 = view.findViewById(R.id.cardHazard);
        cardView5 = view.findViewById(R.id.cardWeather);
        cardView6 = view.findViewById(R.id.cardOther);

        cardSelectionViewModel = new ViewModelProvider(requireActivity()).get(CardSelectionViewModel.class);
        cardSelectionViewModel.getIsCard1Selected().observe(getViewLifecycleOwner(), isSelected -> {
            cardView1.setCardBackgroundColor(ContextCompat.getColor(requireContext(), isSelected ? R.color.teal : R.color.white));
        });

        cardSelectionViewModel.getIsCard2Selected().observe(getViewLifecycleOwner(), isSelected -> {
            cardView2.setCardBackgroundColor(ContextCompat.getColor(requireContext(), isSelected ? R.color.teal : R.color.white));
        });

        cardSelectionViewModel.getIsCard3Selected().observe(getViewLifecycleOwner(), isSelected -> {
            cardView3.setCardBackgroundColor(ContextCompat.getColor(requireContext(), isSelected ? R.color.teal : R.color.white));
        });

        cardSelectionViewModel.getIsCard4Selected().observe(getViewLifecycleOwner(), isSelected -> {
            cardView4.setCardBackgroundColor(ContextCompat.getColor(requireContext(), isSelected ? R.color.teal : R.color.white));
        });

        cardSelectionViewModel.getIsCard5Selected().observe(getViewLifecycleOwner(), isSelected -> {
            cardView5.setCardBackgroundColor(ContextCompat.getColor(requireContext(), isSelected ? R.color.teal : R.color.white));
        });

        cardSelectionViewModel.getIsCard6Selected().observe(getViewLifecycleOwner(), isSelected -> {
            cardView6.setCardBackgroundColor(ContextCompat.getColor(requireContext(), isSelected ? R.color.teal : R.color.white));
        });

        back.setOnClickListener(v -> {
           startActivity(new Intent(getActivity(),HomeActivity.class));

        });
        cardView1.setOnClickListener(v -> {
            loadDescriptionFragment("cardGunThreat");
            // Toggle selection state
            boolean isSelected = cardSelectionViewModel.getIsCard1Selected().getValue() != null && cardSelectionViewModel.getIsCard1Selected().getValue();
            cardSelectionViewModel.updateCard1Selection(!isSelected);

            // Update the BottomNavigationView
            updateBottomNavigationView();  // Place it here before setting the selected item

            // Get the BottomNavigationView from the Activity and update the selected item
            BottomNavigationView bottomNavigationView = ((IncidentActivity) getActivity()).getBottomNavigationView();
            if (bottomNavigationView != null) {
                bottomNavigationView.setSelectedItemId(R.id.describe);  // Set the selected item to 'describe'
            }


        });

        cardView2.setOnClickListener(v -> {
            loadDescriptionFragment("cardFire");
            // Toggle selection state
            boolean isSelected = cardSelectionViewModel.getIsCard2Selected().getValue() != null && cardSelectionViewModel.getIsCard2Selected().getValue();
            cardSelectionViewModel.updateCard2Selection(!isSelected);
            updateBottomNavigationView();
            // Get the BottomNavigationView from the Activity and update the selected item
            BottomNavigationView bottomNavigationView = ((IncidentActivity) getActivity()).getBottomNavigationView();
            if (bottomNavigationView != null) {
                bottomNavigationView.setSelectedItemId(R.id.describe);  // Set the selected item to 'describe'
            }


        });
        cardView3.setOnClickListener(v -> {
            loadDescriptionFragment("cardBomb");
            // Toggle selection state
            boolean isSelected = cardSelectionViewModel.getIsCard3Selected().getValue() != null && cardSelectionViewModel.getIsCard3Selected().getValue();
            cardSelectionViewModel.updateCard3Selection(!isSelected);
            updateBottomNavigationView();
            // Get the BottomNavigationView from the Activity and update the selected item
            BottomNavigationView bottomNavigationView = ((IncidentActivity) getActivity()).getBottomNavigationView();
            if (bottomNavigationView != null) {
                bottomNavigationView.setSelectedItemId(R.id.describe);  // Set the selected item to 'describe'
            }


        });
        cardView4.setOnClickListener(v -> {
            loadDescriptionFragment("cardHazard");
            // Toggle selection state
            boolean isSelected = cardSelectionViewModel.getIsCard4Selected().getValue() != null && cardSelectionViewModel.getIsCard4Selected().getValue();
            cardSelectionViewModel.updateCard4Selection(!isSelected);
            updateBottomNavigationView();
            // Get the BottomNavigationView from the Activity and update the selected item
            BottomNavigationView bottomNavigationView = ((IncidentActivity) getActivity()).getBottomNavigationView();
            if (bottomNavigationView != null) {
                bottomNavigationView.setSelectedItemId(R.id.describe);  // Set the selected item to 'describe'
            }


        });
        cardView5.setOnClickListener(v -> {
            loadDescriptionFragment("cardWeather");
            // Toggle selection state
            boolean isSelected = cardSelectionViewModel.getIsCard5Selected().getValue() != null && cardSelectionViewModel.getIsCard5Selected().getValue();
            cardSelectionViewModel.updateCard5Selection(!isSelected);
            updateBottomNavigationView();
            // Get the BottomNavigationView from the Activity and update the selected item
            BottomNavigationView bottomNavigationView = ((IncidentActivity) getActivity()).getBottomNavigationView();
            if (bottomNavigationView != null) {
                bottomNavigationView.setSelectedItemId(R.id.describe);  // Set the selected item to 'describe'
            }


        });
        cardView6.setOnClickListener(v -> {
            loadDescriptionFragment("cardOther");
            // Toggle selection state
            boolean isSelected = cardSelectionViewModel.getIsCard6Selected().getValue() != null && cardSelectionViewModel.getIsCard6Selected().getValue();
            cardSelectionViewModel.updateCard6Selection(!isSelected);
            updateBottomNavigationView();
            // Get the BottomNavigationView from the Activity and update the selected item
            BottomNavigationView bottomNavigationView = ((IncidentActivity) getActivity()).getBottomNavigationView();
            if (bottomNavigationView != null) {
                bottomNavigationView.setSelectedItemId(R.id.describe);  // Set the selected item to 'describe'
            }


        });



        return view;
    }



    private void loadDescriptionFragment(String cardName) {
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cardName", cardName);
        descriptionFragment.setArguments(bundle);

        // Navigate to DescriptionFragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, descriptionFragment)
                .addToBackStack(null)
                .commit();
    }

    private void updateBottomNavigationView() {
        // Get the BottomNavigationView from the Activity and update the selected item
        BottomNavigationView bottomNavigationView = ((IncidentActivity) getActivity()).getBottomNavigationView();
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.describe);  // Set the selected item to 'describe'
        }
    }
}