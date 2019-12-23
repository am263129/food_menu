package mik.voice.food_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class add_food_item extends AppCompatActivity {

    RatingBar ratingBar;
    TextView rating_value;
    ImageView add_btn, food_photo;
    EditText food_name, description, price;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);

        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        rating_value = (TextView) findViewById(R.id.rating_num);
        add_btn = (ImageView)findViewById(R.id.add_btn);
        food_name = (EditText)findViewById(R.id.edit_name);
        description = (EditText)findViewById(R.id.edit_description);
        price = (EditText)findViewById(R.id.edit_price);
        food_photo = (ImageView)findViewById(R.id.add_photo);

        Intent intent = getIntent();
        try{
            String mode =intent.getStringExtra("Type");
            if (mode.equals("Show")){
                add_btn.setVisibility(View.GONE);
                food_name.setEnabled(false);
                food_photo.setClickable(false);
                description.setEnabled(false);
                price.setEnabled(false);
                ratingBar.setEnabled(false);
                food_name.setText(Global.new_item.getName().toString());
                String base64 = Global.new_item.getPhoto();
                String imageDataBytes = base64.substring(base64.indexOf(",")+1);
                InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                food_photo.setImageBitmap(bitmap);
                description.setText(Global.new_item.getDescription());
                price.setText(Global.new_item.getPrice());
                ratingBar.setRating(Float.parseFloat(Global.new_item.getRating()));
                rating_value.setText(Global.new_item.getRating());
            }
        }catch (Exception e){
            Log.e("Error", e.toString());
        }
        food_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crop.pickImage(add_food_item.this);
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(food_name.getText().toString().equals("") || description.getText().toString().equals("") || price.getText().toString().equals("")){
                    Toast.makeText(add_food_item.this,"Please input valid data", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Bitmap bitmap = ((BitmapDrawable) food_photo.getDrawable()).getBitmap();
                    String base64photo = getBase64String(bitmap);
                    food_menu_item new_item = new food_menu_item(food_name.getText().toString(), base64photo, price.getText().toString(), description.getText().toString(),rating_value.getText().toString());
                    Global.new_item = new_item;
                    setResult(Activity.RESULT_OK);
                    finish();
                }

            }
        });
        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                rating_value.setText(String.valueOf(rating));
            }
        });
    }

    private String getBase64String(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Crop.getOutput(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
            food_photo.setImageBitmap(bitmap);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
