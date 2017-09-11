package com.mostro.rangel.budgetmethis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

                ExpenseObject expenseObject = new ExpenseObject(
                        ((EditText) findViewById(R.id.titleInput)).getText().toString(),
                        ((EditText) findViewById(R.id.costInput)).getText().toString(),
                        ((Spinner) findViewById(R.id.categoriesInput)).getSelectedItem().toString(),
                        ((Spinner) findViewById(R.id.subcategoriesInput)).getSelectedItem().toString(),
                        ((EditText) findViewById(R.id.descriptionInput)).getText().toString(), null);

                StringRequest request = new StringRequest(Request.Method.POST,
                        url_create_expense,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Successful response
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String message = jsonResponse.getString("message");

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

                                QueuingExpenseService queuingExpenseService = new QueuingExpenseService(getApplicationContext());
                                queuingExpenseService.queueExpense(expenseObject);

                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<>();
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
}
