package com.mostro.rangel.budgetmethis;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;

public class QueuingExpenseService {

    private static String FILENAME = "QueuedExpenses.txt";
    private Context context;
    private JSONArray jsonArray;

    public QueuingExpenseService(Context context) {
        jsonArray = readQueuedExpenses();
        this.context = context;
    }

    public void queueExpense(ExpenseObject expenseObject) {
        addExpenseToArray(expenseObject);

        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(jsonArray.toString().getBytes());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addExpenseToArray(ExpenseObject expenseObject) {
        if (jsonArray == null) {
            jsonArray = new JSONArray();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", expenseObject.getTitle());
            jsonObject.put("cost", expenseObject.getCost());
            jsonObject.put("category", expenseObject.getCategory());
            jsonObject.put("subcategory", expenseObject.getSubcategory());
            jsonObject.put("description", expenseObject.getDescription());
            jsonObject.put("date_added", expenseObject.getDate());

            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONArray readQueuedExpenses () {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            DataInputStream dis = new DataInputStream(fis);
            String json = dis.readUTF();
            dis.close();

            return new JSONArray(json);

        } catch (IOException | JSONException ignored) {
        }

        return  null;
    }



}
