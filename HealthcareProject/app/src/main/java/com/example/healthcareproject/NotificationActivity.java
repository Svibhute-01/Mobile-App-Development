package com.example.healthcareproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationActivity extends AppCompatActivity {

    private String[][] notification_details = {};
    HashMap<String, String> item;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    ListView lst;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btnBack = findViewById(R.id.buttonNotificationBack);
        lst = findViewById(R.id.listViewNotification);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationActivity.this, HomeActivity.class));
            }
        });

        Database db = new Database(getApplicationContext(), "healthcar", null, 1);
        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username", "").toString();
        
        ArrayList<String> appData = db.getAppointmentData(username);
        notification_details = new String[appData.size()][5];

        for (int i = 0; i < appData.size(); i++) {
            String strData = appData.get(i).toString();
            String[] strSplit = strData.split(java.util.regex.Pattern.quote("$"));
            notification_details[i][0] = "Reminder: Appointment with " + strSplit[0];
            notification_details[i][1] = "Location: " + strSplit[1];
            notification_details[i][2] = "Time: " + strSplit[4] + " at " + strSplit[5];
            notification_details[i][3] = "Status: Confirmed";
            notification_details[i][4] = "";
        }

        list = new ArrayList<>();
        for (int i = 0; i < notification_details.length; i++) {
            item = new HashMap<String, String>();
            item.put("line1", notification_details[i][0]);
            item.put("line2", notification_details[i][1]);
            item.put("line3", notification_details[i][2]);
            item.put("line4", notification_details[i][3]);
            item.put("line5", notification_details[i][4]);
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);
    }
}
