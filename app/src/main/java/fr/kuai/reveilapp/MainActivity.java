package fr.kuai.reveilapp;

import android.Manifest;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button btnGoToFragment = (Button) findViewById(R.id.buttonAddAlarm);

        btnGoToFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGoToFragment.setVisibility(View.GONE);
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.MainActivity, new ClockFragment()).commit();
            }
        });
    }

    private void VerifPermission() {
        Log.d(TAG, "Vérification des permissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!(ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        VerifPermission();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}