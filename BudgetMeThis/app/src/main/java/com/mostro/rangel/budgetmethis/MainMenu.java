package com.mostro.rangel.budgetmethis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(this, SettingsPage.class);
        startActivity(intent);
    }

    public void goToAddExpense(View view) {
        Intent intent = new Intent(this, AddExpensePage.class);
        startActivity(intent);
    }

    public void goToSummary(View view) {
        Intent intent = new Intent(this, SummaryPage.class);
        startActivity(intent);
    }

    public void goToCounters(View view) {
        Intent intent = new Intent(this, CountersPage.class);
        startActivity(intent);
    }

    public void goToSearchParameters(View view) {
        Intent intent = new Intent(this, SearchParametersPage.class);
        startActivity(intent);
    }
}
