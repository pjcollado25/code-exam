package com.example.codeexam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private EditText emailAddressEditText;
    private EditText mobileNumberEdiTText;
    private TextView birthdate;
    private TextView age;
    private TextView gender;
    int ageCalculated = 0;

    private  ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout showCalendar = findViewById(R.id.calendarView);
        birthdate = findViewById(R.id.selected_date);
        Spinner spinner = findViewById(R.id.genderSpinner);
        age = findViewById(R.id.agetext);
        Button submit = findViewById(R.id.submitButton);
        gender = findViewById(R.id.gender);
        fullNameEditText = findViewById(R.id.fullName);
        emailAddressEditText = findViewById(R.id.emailAddress);
        mobileNumberEdiTText = findViewById(R.id.mobileNumber);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Calendar calendar = Calendar.getInstance();

        showCalendar.setOnClickListener(view -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DATE);

            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, (datePicker, yearPick, monthPick, dayPick) -> {
                String selectedDate = dayPick + "/" + monthPick + "/" + yearPick;
                int calculation = year - yearPick;
                String ageCalculate = String.valueOf(calculation);
                if (calculation >= 18) {
                    birthdate.setText(selectedDate);
                    age.setText(String.format("Age: %s years old", ageCalculate));
                    ageCalculated = calculation;

                } else {
                    birthdate.setText(String.format(day + "/" + month + "/" + year));
                    Toast.makeText(MainActivity.this, "Age must be 18 and above", Toast.LENGTH_SHORT).show();
                }
            }, year, month, day);
            datePickerDialog.show();
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Perform action when an item is selected
                String selectedGender = (String) parentView.getItemAtPosition(position);
                gender.setText(selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
                String selectedGender = (String) parentView.getItemAtPosition(0);
                gender.setText(selectedGender);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            final String fullName = fullNameEditText.getText().toString();
           final String emailAddress = emailAddressEditText.getText().toString();
           final String mobileNumber = mobileNumberEdiTText.getText().toString();
            @Override
            public void onClick(View view) {
                if (ageCalculated < 18 && fullName.isEmpty() && emailAddress.isEmpty() && mobileNumber.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Submit", Toast.LENGTH_SHORT).show();
                    DataModel dataModel = new DataModel(fullName,emailAddress,mobileNumber,ageCalculated);
                    sendUserData(dataModel);
                }
            }
        });

    }

    private void sendUserData(DataModel dataModel) {

        Call<ApiService.ResponseData> call = apiService.createUser(dataModel);
        call.enqueue(new Callback<ApiService.ResponseData>() {
            @Override
            public void onResponse(Call<ApiService.ResponseData> call, Response<ApiService.ResponseData> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    if (message != null) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to create user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiService.ResponseData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to submit user", Toast.LENGTH_SHORT).show();
            }

        });
    }

}
