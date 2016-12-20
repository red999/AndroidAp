package com.example.olya.shop.ViewModels;

import android.app.Activity;
import android.os.Bundle;

import com.example.olya.shop.Controllers.VendorCont;
import com.example.olya.shop.Models.Vendor;
import com.example.olya.shop.R;

/**
 * Created by Olya on 12.12.2016.
 */

public class VendorActivity  extends Activity {
    VendorCont vendorCont;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        //getApplicationContext().deleteDatabase(DbHelper.DATABASE_NAME);
        vendorCont = new VendorCont(getApplicationContext());
        //відкриваєм бд
        vendorCont.open();
        //створ нового користув
        Vendor vendor = new Vendor("Com1", "EDRPOU", "lawyerPer");
        //додавання нов кор
        vendorCont.addVendor(vendor);
        vendorCont.addVendor(new Vendor("Com2", "EDRPOU", "lawyerPer"));
        vendorCont.addVendor(new Vendor("Com3", "EDRPOU", "lawyerPer"));

        vendorCont.close();
    }
}

