package com.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HealthArticlesDetailsActivity extends AppCompatActivity {

    TextView tvTitle, tvContent;
    ImageView img;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articles_details);

        btnBack = findViewById(R.id.buttonHADBack);
        tvTitle = findViewById(R.id.textViewHADTitle);
        tvContent = findViewById(R.id.textViewHADContent);
        img = findViewById(R.id.imageViewHAD);

        Intent intent = getIntent();
        String title = intent.getStringExtra("text1");
        tvTitle.setText(title);
        
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int resId = bundle.getInt("text2");
            img.setImageResource(resId);
        }

        // Set article content based on the title
        if (title.equals("Walking Daily")) {
            tvContent.setText("Walking is a great way to improve or maintain your overall health. Just 30 minutes every day can increase cardiovascular fitness, strengthen bones, reduce excess body fat, and boost muscle power and endurance.");
        } else if (title.equals("Home Care of COVID-19")) {
            tvContent.setText("Most people who get sick with COVID-19 will have only mild illness and should recover at home. Care at home can help stop the spread of COVID-19 and protect others in your household.");
        } else if (title.equals("Stop Smoking")) {
            tvContent.setText("Quitting smoking is one of the most important things you can do for your health. No matter how long you have smoked, quitting can reduce your risk for health problems and improve your breathing.");
        } else if (title.equals("Menstrual Cramps")) {
            tvContent.setText("Menstrual cramps are throbbing or cramping pains in the lower abdomen. Many women have menstrual cramps just before and during their menstrual periods.");
        } else if (title.equals("Healthy Gut")) {
            tvContent.setText("A healthy gut contains healthy bacteria and immune cells that ward off infectious agents. It also communicates with the brain through nerves and hormones, which helps maintain general health.");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthArticlesDetailsActivity.this, HealthArticlesActivity.class));
            }
        });
    }
}
