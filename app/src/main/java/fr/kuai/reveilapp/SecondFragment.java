package fr.kuai.reveilapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

public class SecondFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        Songs song = new Songs();
        List<Songs> listOfAllMusic = song.getAllAudioFromDevice(getContext());

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, listOfAllMusic);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        TextView textPAth = view.findViewById(R.id.PathText);
        textPAth.setText(song.getaPath());

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button BoutonConfirmer = (Button) view.findViewById(R.id.Confirm_Button);
        BoutonConfirmer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        Button BouttonAnnuler = (Button) view.findViewById(R.id.Cancel_Button);
        BouttonAnnuler.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}