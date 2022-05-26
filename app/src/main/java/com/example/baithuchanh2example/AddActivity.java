package com.example.baithuchanh2example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.baithuchanh2example.dal.SQLiteHelper;
import com.example.baithuchanh2example.model.Item;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btUpdate, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    private void initView() {
        sp = findViewById(R.id.spinnerCategory);
        eTitle = findViewById(R.id.textTitle);
        ePrice = findViewById(R.id.textPrice);
        eDate = findViewById(R.id.textDate);
        btUpdate = findViewById(R.id.buttonUpdate);
        btCancel = findViewById(R.id.buttonCancel);

        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));
    }

    @Override
    public void onClick(View view) {
        if(view == eDate){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "", mm = "", dd = "";
                    if(m > 8){
                        mm = (m+1)+"";
                    } else {
                        mm = "0" + (m+1);
                    }
                    if(d > 8){
                        dd = d+"";
                    } else {
                        dd = "0"+d;
                    }
                    date = dd+"/"+mm+"/"+y;
                    eDate.setText(date);
                }
            }, year, month, day);
            dialog.show();
        }
        if(view == btCancel){
            finish();
        }
        if(view == btUpdate){
            String t = eTitle.getText().toString();
            String p = ePrice.getText().toString();
            String c = sp.getSelectedItem().toString();
            String d = eDate.getText().toString();

            if(!t.isEmpty() && p.matches("\\d+")){
                Item i = new Item(t, c, p, d);
                SQLiteHelper db = new SQLiteHelper(this);
                db.addItem(i);
                finish();
            }
        }
    }
}
