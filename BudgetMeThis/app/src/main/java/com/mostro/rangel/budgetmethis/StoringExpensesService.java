package com.mostro.rangel.budgetmethis;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class StoringExpensesService {

    private static String FILENAME = "StoredExpenses.txt";
    private Context context;
    private File filePath;

    public StoringExpensesService(Context context, File path) {
        this.context = context;
        this.filePath = path;
    }

    public void storeExpenses(String expenses) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(expenses);
            osw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readStoredExpenses() {


        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            DataInputStream dis = new DataInputStream(fis);
            String json = dis.readUTF();
            dis.close();

            return json;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
