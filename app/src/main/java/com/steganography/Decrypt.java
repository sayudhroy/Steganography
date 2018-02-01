package com.steganography;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sayudh on 3/26/2017.
 */
public class Decrypt extends Activity implements View.OnClickListener {

    private TextView decrypt_txt, extracted_txt;
    private EditText outputKey;
    private Button bDecodeMsg;
    private String decryptMsg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decrypt);
        init();
    }

    private void init() {
        extracted_txt=(TextView)findViewById(R.id.extracted_txt);
        decrypt_txt=(TextView)findViewById(R.id.decrypt_txt);
        outputKey = (EditText) findViewById(R.id.outputKey);
        bDecodeMsg=(Button)findViewById(R.id.bDecodeMsg);
        extracted_txt.setText(Data.extractedMsg);
        bDecodeMsg.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bDecodeMsg)
        {
            Log.e("Extracted Message: ", ""+Data.extractedMsg);
            Data.key = outputKey.getText().toString();
            Log.e("Extracted Key: ", ""+Data.key);
            Stego obj=new Stego();
            decryptMsg=obj.decrypt();
            decrypt_txt.setText(decryptMsg);
        }
    }
}
