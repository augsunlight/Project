package com.example.baithuchanh2example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btUpdate, btCancel, btRemove;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        eDate.setOnClickListener(this);

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");

        eTitle.setText(item.getTitle());
        ePrice.setText(item.getPrice());
        eDate.setText(item.getDate());

        int p = 0;
        for (int i=0; i<sp.getCount(); i++){
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())){
                p = i;
                break;
            }
        }
        sp.setSelection(p);
    }

    private void initView() {
        sp = findViewById(R.id.spinnerCategory);
        eTitle = findViewById(R.id.textTitle);
        ePrice = findViewById(R.id.textPrice);
        eDate = findViewById(R.id.textDate);
        btUpdate = findViewById(R.id.buttonUpdate);
        btCancel = findViewById(R.id.buttonCancel);
        btRemove = findViewById(R.id.buttonDetele);

        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));
    }

    @Override
    public void onClick(View view) {
        SQLiteHelper db = new SQLiteHelper(this);
        if(view == eDate){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                int id = item.getId();
                Item i = new Item(id, t, c, p, d);
                db.update(i);
                finish();
            }
        }
        if(view == btRemove){
            int id = item.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Thong bao xoa");
            builder.setMessage("Ban co chac muon xoa " + item.getTitle() + " khong?");
            builder.setIcon(R.drawable.ic_baseline_delete_24);
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.delete(id);
                    finish();
                }
            });
            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
