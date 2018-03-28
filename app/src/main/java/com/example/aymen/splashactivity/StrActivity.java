package com.example.aymen.splashactivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class StrActivity extends AppCompatActivity {
    ImageView mainFrame;
    Bitmap startImage;
    Button VersPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_str);
        mainFrame= (ImageView) findViewById(R.id.imageView1);
       VersPage =(Button)findViewById(R.id.b1);
        VersPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });
    }
    private void  loadImage()
    {
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(i,"select Picture"),1);
    }
   /* public String getPath(Uri uri)
    {
        Cursor c=managedQuery(uri,new String[]{"_data"},null,null,null);
        int i=c.getColumnIndexOrThrow("_data");
        c.moveToFirst();
        return c.getString(i);
    }*/
    public void onActivityResult(int p1,int p2,Intent data)
    {
        if((p2==-1)&&(p1==1)&&(data!=null))
        {
            Intent i=new Intent(getBaseContext(),MED_APP.class);
            Uri uri=data.getData();
            mainFrame.setImageURI(uri);
            startImage=((BitmapDrawable)mainFrame.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            startImage.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
            i.putExtra("IMG", byteArrayOutputStream.toByteArray());
            startActivity(i);

        }
    }

}
