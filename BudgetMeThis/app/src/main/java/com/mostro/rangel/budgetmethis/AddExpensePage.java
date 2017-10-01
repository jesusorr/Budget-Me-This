package com.mostro.rangel.budgetmethis;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddExpensePage extends AppCompatActivity {

    private ExpenseObject expenseObject;

    // url to create new product
    private static String url_create_expense = "http://192.168.0.10/create_expense.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_page);

        // Setting spinners
        Spinner categoriesSpinner = (Spinner) findViewById(R.id.categoriesInput);
        categoriesSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Categories.values()));
        Spinner subcategoriesSpinner = (Spinner) findViewById(R.id.subcategoriesInput);
        subcategoriesSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Subcategories.values()));

        // Handling the Add Expense button
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Button addExpenseButton = (Button) findViewById(R.id.addExpenseButton);
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                boolean valid = validateAndExtractExpense();

                if (!valid) return;

                StringRequest request = new StringRequest(Request.Method.POST,
                        url_create_expense,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Successful response
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String message = jsonResponse.getString("message");

                                    showMessageToast("Expense was added to Budget");

                                    Intent intent = new Intent(view.getContext(), MainMenu.class);
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("user_name", expenseObject.getUserName());
                        parameters.put("title", expenseObject.getTitle());
                        parameters.put("cost", expenseObject.getCost());
                        parameters.put("category", expenseObject.getCategory());
                        parameters.put("subcategory", expenseObject.getSubcategory());
                        parameters.put("description", expenseObject.getDescription());
                        return parameters;
                    }

                };
                requestQueue.add(request);
            }

        });

    }

    private boolean validateAndExtractExpense() {

        EditText titleInput = (EditText) findViewById(R.id.titleInput);
        EditText costInput = (EditText) findViewById(R.id.costInput);
        Spinner categoriesInput = (Spinner) findViewById(R.id.categoriesInput);
        Spinner subcategoriesInput = (Spinner) findViewById(R.id.subcategoriesInput);
        EditText descriptionInput = (EditText) findViewById(R.id.descriptionInput);

        if (validateFields(titleInput, costInput, categoriesInput)) return false;

        expenseObject = new ExpenseObject( null, getLocalBluetoothName(),
                titleInput.getText().toString(),
                costInput.getText().toString(),
                categoriesInput.getSelectedItem().toString(),
                subcategoriesInput.getSelectedItem().toString(),
                descriptionInput.getText().toString(), null);

        return true;
    }

    private boolean validateFields(EditText titleInput, EditText costInput, Spinner categoriesInput) {
        boolean valid = true;
        if(titleInput.getText().toString().trim().equals("")) {
            titleInput.setError("A title is required");
            valid = false;
        }

        if(costInput.getText().toString().trim().equals("")) {
            costInput.setError("A cost is required");
            valid = false;
        }

        if(categoriesInput.getSelectedItem().toString().equals("None")) {
            TextView errorText = (TextView) categoriesInput.getSelectedView();
            errorText.setError("A category is required");
            valid = false;
        }
        if (!valid) return true;
        return false;
    }

    public String getLocalBluetoothName() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // if device does not support Bluetooth
        if(mBluetoothAdapter == null){
            Log.d("NOPE","device does not support bluetooth");
        }

        String name =  mBluetoothAdapter.getName();

        return name.substring(0, name.indexOf('\''));
    }

    public void showMessageToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
