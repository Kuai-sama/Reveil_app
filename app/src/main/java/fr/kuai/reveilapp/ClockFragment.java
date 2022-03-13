package fr.kuai.reveilapp;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClockFragment extends Fragment {

    private MaterialTimePicker picker;
    private Calendar calendar = Calendar.getInstance();

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.clock_fragment, container, false);

        Songs song = new Songs();
        List<Songs> listOfAllMusic = song.getAllAudioFromDevice(getActivity());

        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, listOfAllMusic);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        TextView chemin = (TextView) view.findViewById(R.id.PathText);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String data = (String) listOfAllMusic.get(pos).getaPath();
            chemin.setText(data);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        TextView horodatage = view.findViewById(R.id.selectedTime);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("H:mm");
        String heureCourante = format.format(date);

        horodatage.setText(heureCourante);

        Button heureSelectionner = view.findViewById(R.id.selectTimeBtn);
        heureSelectionner.setOnClickListener(view1 -> showTimePicker());

        createNotificationChannel();

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button BouttonAnnuler = view.findViewById(R.id.Cancel_Button);
        BouttonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        Button BoutonConfirmer = view.findViewById(R.id.Confirm_Button);
        BoutonConfirmer.setOnClickListener(v -> setAlarm());
    }

    private void showTimePicker() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("H");
        String heuresCourante = format.format(date);

        Date date2 = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("mm");
        String minutesCourante = format2.format(date2);

        picker = new MaterialTimePicker.Builder()
                .setTheme(R.style.CustomThemeOverlay_MaterialCalendar_Fullscreen)
                .setHour(Integer.parseInt(heuresCourante))
                .setMinute(Integer.parseInt(minutesCourante))
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Séléctionnez votre horodatage")
                .build();

        picker.show(getParentFragmentManager(), "tag");

        picker.addOnPositiveButtonClickListener(v -> {
            TextView selectedTime = getActivity().findViewById(R.id.selectedTime);
            selectedTime.setText(picker.getHour() + " : " + picker.getMinute());

            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
            calendar.set(Calendar.MINUTE, picker.getMinute());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        });
    }

    private void setAlarm() {
        TextView MusicPath = getView().findViewById(R.id.PathText);
        String Music_Path_String = MusicPath.getText().toString();

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("Music_Path_String", Music_Path_String);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast toast = Toast.makeText(getActivity(), "Votre alarme a été programmée", Toast.LENGTH_SHORT);
        toast.show();

        SystemClock.sleep(1000); // Pause de 1 sec

        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarme";
            String description = "Ceci est une alarme";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Alarme", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
