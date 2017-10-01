package com.mostro.rangel.budgetmethis;

import android.graphics.Color;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SummaryPage extends AppCompatActivity {

    private static String url_get_all_expenses = "http://192.168.0.10/get_all_expenses.php";
    private static String url_get_expenses_bymonthyear = "http://192.168.0.10/get_expenses_bymonthyear.php";
    List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    //TODO: CHeck current year and add it to the list;
    List<String> years = Arrays.asList("2017");

    private int fullSalary = 5780;
    private Double billsPortionPan = fullSalary * 0.48;
    private Double housingPortionPan = fullSalary * 0.15;
    private Double entertainmentPortionPan = fullSalary * 0.07;
    private Double savingsPortionPan = fullSalary * 0.1;
    private Double extraPortionPan = fullSalary * 0.1;
    private Double oriPersonalPortionPan = fullSalary * 0.025;
    private Double jesusPersonalPortionPan = fullSalary * 0.025;
    private Double vacationSavingsPortionPan = fullSalary * 0.05;

    Spinner monthInput;
    Spinner yearInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_page);

        /**
         * Adding it to the spinner
         */
        monthInput = (Spinner) findViewById(R.id.monthInput);
        monthInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, months));
        yearInput = (Spinner) findViewById(R.id.yearInput);
        yearInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, years));
        setSpinnersToCurrentDate();

        monthInput.setOnItemSelectedListener(new MYonItemSelectItem());
        yearInput.setOnItemSelectedListener(new MYonItemSelectItem());

    }

    /**
     * Posting the request
     */
    private void postRequest(final int month, final int year) {

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        List<ExpenseObject> myExpenses = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.POST, url_get_expenses_bymonthyear,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            readJsonResponse(response, myExpenses);

                            processAndPublishStatistics(myExpenses);

                        }
                        catch (JSONException exception) {
                            exception.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("month", month+"");
                parameters.put("year", year+"");
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    private void processAndPublishStatistics(List<ExpenseObject> myExpenses) {
        List<ExpenseObject> housing = myExpenses.stream().filter(e -> e.getCategory().toLowerCase().equals("housing")).collect(Collectors.toList());
        List<ExpenseObject> bills = myExpenses.stream().filter(e -> e.getCategory().toLowerCase().equals("bills")).collect(Collectors.toList());
        List<ExpenseObject> entertainment = myExpenses.stream().filter(e -> e.getCategory().toLowerCase().equals("entertainment")).collect(Collectors.toList());
        List<ExpenseObject> savings = myExpenses.stream().filter(e -> e.getCategory().toLowerCase().equals("savings")).collect(Collectors.toList());
        List<ExpenseObject> extras = myExpenses.stream().filter(e -> e.getCategory().toLowerCase().equals("extra")).collect(Collectors.toList());
        List<ExpenseObject> oriPersonal = myExpenses.stream().filter(e -> e.getCategory().toLowerCase().equals("oriexpenses")).collect(Collectors.toList());
        List<ExpenseObject> jesusPersonal = myExpenses.stream().filter(e -> e.getCategory().toLowerCase().equals("jesusexpenses")).collect(Collectors.toList());
        List<ExpenseObject> vacationSavings = myExpenses.stream().filter(e -> e.getCategory().toLowerCase().equals("vacationsavings")).collect(Collectors.toList());

        calculateStatisticsAndGraph(R.id.billsPieChart, bills, billsPortionPan, new int[] {ColorIntegers.billsColor1, ColorIntegers.billsColor2});
        calculateStatisticsAndGraph(R.id.housingPieChart, housing, housingPortionPan, new int[] {ColorIntegers.housingColor1, ColorIntegers.housingColor2});
        calculateStatisticsAndGraph(R.id.entertainmentPieChart, entertainment, entertainmentPortionPan, new int[] {ColorIntegers.entertainmentColor1, ColorIntegers.entertainmentColor2});
        calculateStatisticsAndGraph(R.id.savingsPieChart, savings, savingsPortionPan, new int[] {ColorIntegers.savingsColor1, ColorIntegers.savingsColor2});
        calculateStatisticsAndGraph(R.id.extrasPieChart, extras, extraPortionPan, new int[] {ColorIntegers.extraColor1, ColorIntegers.extraColor2});
        calculateStatisticsAndGraph(R.id.oriPersonalPieChart, oriPersonal, oriPersonalPortionPan, new int[] {ColorIntegers.oriPersonalColor1, ColorIntegers.oriPersonalColor2});
        calculateStatisticsAndGraph(R.id.jesusPersonalPieChart, jesusPersonal, jesusPersonalPortionPan, new int[] {ColorIntegers.jesusPersonalColor1, ColorIntegers.jesusPersonalColor2});
        calculateStatisticsAndGraph(R.id.vacationSavingsPieChart, vacationSavings, vacationSavingsPortionPan, new int[] {ColorIntegers.vacationSavingsColor1, ColorIntegers.vacationSavingsColor2});
    }

    private void readJsonResponse(String response, List<ExpenseObject> myExpenses) throws JSONException {
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray expenses = jsonResponse.getJSONArray("expenses");
        for(int i = 0; i< expenses.length(); i++) {
            JSONObject expense = expenses.getJSONObject(i);

            myExpenses.add(new ExpenseObject(expense.getString("id"),
                    expense.getString("user_name"), expense.getString("title"),
                    expense.getString("cost"), expense.getString("category"),
                    expense.getString("subcategory"), expense.getString("description"),
                    expense.getString("date_added")));
        }
    }

    // It calculates the statistics and the prints in graph
    private void calculateStatisticsAndGraph(int graphId, List<ExpenseObject> expenses, Double portion,int[] colorArray ) {
        Double total = 0.0;
        for(ExpenseObject e : expenses) {
            total += Double.parseDouble(e.getCost());
        }
        Double rest = portion - total;

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(total.floatValue(), "Expended"));
        entries.add(new PieEntry(rest.floatValue(), "Left"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colorArray);
        dataSet.setValueTextColor(Color.rgb(255, 255, 255));
        dataSet.setValueTextSize(14);
        dataSet.setSliceSpace(7);

        PieData data = new PieData(dataSet);
        PieChart pieChart = (PieChart) findViewById(graphId);
        pieChart.setData(data);
        pieChart.animateY(3000);
        pieChart.getDescription().setEnabled(false);

        Legend legends = pieChart.getLegend();
        legends.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legends.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legends.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legends.setEnabled(true);
    }

    /**
     * Sets spinners to current date
     */
    public void setSpinnersToCurrentDate() {

        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        monthInput.setSelection(month);
        yearInput.setSelection(years.indexOf(year+""));
    }

    private class MYonItemSelectItem implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            postRequest(monthInput.getSelectedItemPosition() + 1, Integer.parseInt(yearInput.getSelectedItem().toString()));

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }



}
