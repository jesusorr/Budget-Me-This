package com.mostro.rangel.budgetmethis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SearchParametersPage extends AppCompatActivity {

    private static String url_get_expenses_search = "http://192.168.0.10/get_expenses_search.php";
    List<String> months = Arrays.asList("None", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
    //TODO: Add a query to the pi that return all the years for all expenses
    List<String> years = Arrays.asList("None", "2017");
    List<String> users = Arrays.asList("None", "ORI", "JORR");
    List<String> days;
    List<String> categories;
    List<String> subcategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_parameters_page);

        days = new ArrayList<>();
        days.add("None");
        for(int i = 1; i <= 31; i++) {
            days.add(i + "");
        }

        Spinner dayInput = (Spinner) findViewById(R.id.searchDayInput);
        dayInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, days));
        Spinner monthInput = (Spinner) findViewById(R.id.searchMonthInput);
        monthInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, months));
        Spinner yearInput = (Spinner) findViewById(R.id.searchYearInput);
        yearInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, years));

        Spinner categoriesInput = (Spinner) findViewById(R.id.searchCategoryInput);
        categoriesInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Categories.values()));
        categoriesInput.setSelection(0);
        Spinner subcategoriesInput = (Spinner) findViewById(R.id.searchSubcategoryInput);
        subcategoriesInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Subcategories.values()));
        subcategoriesInput.setSelection(0);
        Spinner usersInput = (Spinner) findViewById(R.id.searchUserInput);
        usersInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users));
        usersInput.setSelection(0);


    }

}
