package com.steganography;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Sayudh on 3/25/2017.
 */
public class Menu extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }
    public void onButtonClick(View v)
    {
        if(v.getId()==R.id.bEncode)
        {
            Intent i=new Intent(Menu.this, Encode.class);
            startActivity(i);
        }
        else if(v.getId()==R.id.bDecode)
        {
            Intent i=new Intent(Menu.this, Decode.class);
            startActivity(i);
        }
    }
}
