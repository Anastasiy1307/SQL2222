package com.example.sql;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class Adapter extends BaseAdapter {
    private Context nContext;
    List<mask> maskList;

    public Adapter(Context nContext, List<mask> ListMotos)
    {
        this.nContext=nContext;
        this.maskList=ListMotos;
    }

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return maskList.get(i).getID();
    }

    public static Bitmap loadContactPhoto(ContentResolver cr, long id, Context context) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input == null) {
            Resources res = context.getResources();
            return BitmapFactory.decodeResource(res, R.drawable.gluxo);
        }
        return BitmapFactory.decodeStream(input);
    }


    private Bitmap getUserImage(String encodedImg)
    {
        byte[] bytes;
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
        {

            return BitmapFactory.decodeResource(nContext.getResources(), R.drawable.gluxo);
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(nContext,R.layout.activity_mask_table, null);
        ImageView Image=v.findViewById(R.id.photo);
        TextView Name=v.findViewById(R.id.textMarks);
        TextView Power=v.findViewById(R.id.textPower);
        TextView Speed =v.findViewById(R.id.textSpeed);

        mask mask=maskList.get(i);
        Name.setText(mask.getName());
        Power.setText(mask.getPower());
        Speed.setText(mask.getSpeed());
        Image.setImageBitmap(getUserImage(mask.getImage()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenDetalis = new Intent(nContext,Upd.class);
                intenDetalis.putExtra("Moto",mask);
                nContext.startActivity(intenDetalis);

            }
        });


        return v;
    }
}

