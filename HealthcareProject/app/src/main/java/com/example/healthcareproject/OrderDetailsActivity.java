package com.example.healthcareproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderDetailsActivity extends AppCompatActivity {

    private String[][] order_details = {};
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    ListView lst;
    Button btn;
    Spinner spinner;
    String[] categories = {"All", "Lab Test", "Medicine", "Appointment"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        btn = findViewById(R.id.buttonODBack);
        lst = findViewById(R.id.listViewOD);
        spinner = findViewById(R.id.spinnerODCategory);

        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderDetailsActivity.this, HomeActivity.class));
            }
        });

        loadOrderData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterData(categories[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadOrderData() {
        Database db = new Database(getApplicationContext(), "healthcar", null, 1);
        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username", "");

        ArrayList<String> dbData = db.getOrderData(username);
        ArrayList<String> appData = db.getAppointmentData(username);

        order_details = new String[dbData.size() + appData.size()][5];

        int k = 0;
        for (int i = 0; i < dbData.size(); i++) {
            String strData = dbData.get(i);
            String[] strSplit = strData.split(java.util.regex.Pattern.quote("$"));
            order_details[k][0] = strSplit[0]; // fullname
            order_details[k][1] = strSplit[1]; // address
            
            String type = strSplit[7].equals("lab") ? "Lab Test" : "Medicine";
            order_details[k][3] = type;
            
            if (type.equals("Medicine")) {
                order_details[k][2] = ""; // Price removed for Medicine
            } else {
                order_details[k][2] = "Rs." + strSplit[6]; // amount for Lab Test
            }

            order_details[k][4] = strSplit[4] + " " + strSplit[5]; // date time
            k++;
        }

        for (int i = 0; i < appData.size(); i++) {
            String strData = appData.get(i);
            String[] strSplit = strData.split(java.util.regex.Pattern.quote("$"));
            order_details[k][0] = strSplit[0]; // doctor name
            order_details[k][1] = strSplit[1]; // address
            order_details[k][2] = "Rs." + strSplit[3]; // fees
            order_details[k][3] = "Appointment"; // type
            order_details[k][4] = strSplit[4] + " " + strSplit[5]; // date time
            k++;
        }
    }

    private void filterData(String category) {
        list = new ArrayList<>();
        for (int i = 0; i < order_details.length; i++) {
            if (category.equals("All") || order_details[i][3].equals(category)) {
                HashMap<String, String> item = new HashMap<>();
                item.put("line1", order_details[i][0]);
                item.put("line2", order_details[i][1]);
                item.put("line3", order_details[i][2]);
                item.put("line4", order_details[i][3]);
                item.put("line5", order_details[i][4]);
                list.add(item);
            }
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);
    }
}
