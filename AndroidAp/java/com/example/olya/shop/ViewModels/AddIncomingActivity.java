package com.example.olya.shop.ViewModels;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.olya.shop.Controllers.IncomingCont;
import com.example.olya.shop.Controllers.ItemCardCont;
import com.example.olya.shop.Controllers.VendorCont;
import com.example.olya.shop.DAL.DbHelper;
import com.example.olya.shop.Models.Incoming;
import com.example.olya.shop.Models.Vendor;
import com.example.olya.shop.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by Olya on 17.12.2016.
 */

public class AddIncomingActivity extends Activity {
    //об'єкт для доступу до таблиць
    private SQLiteDatabase database;
    //об'єкт для доступу до бд
    private DbHelper dbHelper;
    ItemCardCont iCont;
    VendorCont venCont;
    ArrayAdapter<String> adapter4;
    IncomingCont incCont;

    AutoCompleteTextView item;
    Spinner vendor;
    EditText date, amount;


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_incoming);


        //item = (AutoCompleteTextView) findViewById(R.id.spinner_itemid);
        item = (AutoCompleteTextView) findViewById(R.id.spinner_itemid);
        vendor = (Spinner) findViewById(R.id.spinner_vendorid);
        date = (EditText) findViewById(R.id.date);
        amount = (EditText) findViewById(R.id.amount_edittext);
        item.setThreshold(1);

        iCont = new ItemCardCont(getApplicationContext());
        //відкриваєм бд
        iCont.open();
        List<String> itemsNames = iCont.getItemValuesInStringList();
        //List<String> itemsNames = iCont.getItemsNames();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        item.setAdapter(adapter);

        iCont.close();

        VendorCont vendorCont;
        vendorCont = new VendorCont(getApplicationContext());
        vendorCont.open();
        List<String> vendorsNames = vendorCont.getVendorsNames();
        ArrayAdapter adapter3 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, vendorsNames);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vendor.setAdapter(adapter3);


        item.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                String name = item.getText().toString();
                iCont = new ItemCardCont(getApplicationContext());
                //відкриваєм бд
                iCont.open();
                Toast.makeText(getApplicationContext(), name + " selected", Toast.LENGTH_LONG).show();
    /*            List<String> itemsNomNums = iCont.getNomNumsByName(name);
                //Toast.makeText(getApplicationContext(), item1 + " selected", Toast.LENGTH_LONG).show();
                ArrayAdapter adapter4 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, itemsNomNums);
                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                nomnum.setAdapter(adapter4);
    */          iCont.close();
            }
        });
    }

    public void addItem(View view) {
        Intent intent3 = new Intent(this, AddItemActivity.class);
        startActivity(intent3);
    }

    public void addIncoming(View view) {
        String itemaname_input = item.getText().toString();
        iCont.open();
        long iditem_input = iCont.findItemCard(itemaname_input);
        iCont.close();

        String text_input = vendor.getSelectedItem().toString();
        //String amount = amount.getText().toString();
        long amount_input = Long.parseLong(amount.getText().toString());
        IncomingCont incCont = new IncomingCont(getApplicationContext());
        incCont.open();
        Date date = new Date();
        incCont.addIncoming(new Incoming(1, date, amount_input, 1));
        incCont.close();
        finish();
    }

    public void openDialog(View view)
    {
        iCont = new ItemCardCont(getApplicationContext());
        //відкриваєм бд
        iCont.open();
        List<String> itemsNames = iCont.getItemValuesInStringList();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddIncomingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.custom2, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("List");
        ListView lv = (ListView) convertView.findViewById(R.id.listView1);
        adapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemsNames);
        lv.setAdapter(adapter4);
        final EditText input = new EditText(this);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String result = input.getText().toString();
            }
        });
        alertDialog.setNegativeButton("CANCEL", null);

        alertDialog.show();


    }

}