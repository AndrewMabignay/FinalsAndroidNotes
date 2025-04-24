package com.example.finalsandroidnotes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.HashMap;
import java.util.TreeMap;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText firstName, lastName;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddElement();
        Interaction();
    }

    private void AddElement() {
        firstName = (EditText) findViewById(R.id.FirstName);
        lastName = (EditText) findViewById(R.id.LastName);
        submit = (Button) findViewById(R.id.Submit);
    }

    private void Interaction() {
        submit.setOnClickListener(e -> {
            String firstname = firstName.getText().toString();
            String lastname = lastName.getText().toString();

            if (firstname.isEmpty()){
                firstName.setError("must not be empty");
                return;
            }

            if (lastname.isEmpty()){
                lastName.setError("must not be empty");
                return;
            }

//            SaveRecordInteraction(firstname,lastname);
            SaveRecordInteractionOne(firstname, lastname);
        });
    }

    private void SaveRecordInteraction(String firstNameField, String lastNameField){
        TreeMap<String, Object> data = new TreeMap<>();
        data.put("first name", firstNameField);
        data.put("last name", lastNameField);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        String key = myRef.push().getKey();
        data.put("key", key);

        myRef.child(key).setValue(data).addOnCompleteListener(e -> {
            Toast.makeText(MainActivity.this, "added", Toast.LENGTH_SHORT).show();
            firstName.getText().clear();
            lastName.getText().clear();
        });
    }

    // ADDITIONAL SAVE RECORD (UNSECURED)
    private void SaveRecordInteractionOne(String firstNameField, String lastNameField) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message").child("user");

        DatabaseReference myRef = database.getReference("message").child(firstNameField + "_" + lastNameField);


        myRef.child("first name").setValue(firstNameField);
        myRef.child("last name").setValue(lastNameField);

        Toast.makeText(MainActivity.this, "added", Toast.LENGTH_SHORT).show();

        firstName.getText().clear();
        lastName.getText().clear();
    }
}