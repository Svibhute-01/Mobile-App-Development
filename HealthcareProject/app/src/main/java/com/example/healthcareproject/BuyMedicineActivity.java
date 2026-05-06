package com.example.healthcareproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BuyMedicineActivity extends AppCompatActivity {

    EditText edName, edDetails, edQuantity;
    Button btnBack, btnGoToCart, btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        edName = findViewById(R.id.editTextBMName);
        edDetails = findViewById(R.id.editTextBMDetails);
        edQuantity = findViewById(R.id.editTextBMQuantity);
        
        btnBack = findViewById(R.id.buttonBMBack);
        btnGoToCart = findViewById(R.id.buttonBMGoToCart);
        btnAddToCart = findViewById(R.id.buttonBMAddToCart);

        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineActivity.this, CartBuyMedicineActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class));
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edName.getText().toString();
                String details = edDetails.getText().toString();
                String qtyStr = edQuantity.getText().toString();

                if (name.length() == 0 || details.length() == 0 || qtyStr.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int quantity = Integer.parseInt(qtyStr);
                        
                        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        String username = sharedpreferences.getString("username", "");

                        Database db = new Database(getApplicationContext(), "healthcar", null, 1);
                        // Using the name and quantity in the product description
                        String productInfo = name + " (" + details + ") x" + quantity;
                        
                        // Adding with 0 price as requested
                        db.addCart(username, productInfo, 0, "medicine");
                        Toast.makeText(getApplicationContext(), "Medicine Added to Cart", Toast.LENGTH_SHORT).show();
                        
                        // Clear fields after adding
                        edName.setText("");
                        edDetails.setText("");
                        edQuantity.setText("");
                        
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Invalid Quantity", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
