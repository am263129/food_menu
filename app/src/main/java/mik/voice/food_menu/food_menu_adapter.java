package mik.voice.food_menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class food_menu_adapter extends ArrayAdapter<food_menu_item> {
    TextView food_name, food_price;
    ImageView food_photo, food_rating;
    ArrayList<food_menu_item> array_foods = new ArrayList<>();
    public food_menu_adapter(Context context, int textViewResourceId, ArrayList<food_menu_item> objects) {
        super(context, textViewResourceId, objects);
        array_foods = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_food_menu, null);

        food_name = v.findViewById(R.id.food_name);
        food_price = v.findViewById(R.id.food_price);
        food_photo = v.findViewById(R.id.food_photo);
        food_rating = v.findViewById(R.id.img_rating);
        food_name.setSelected(true);
        food_name.setText(array_foods.get(position).getName());
        food_price.setText(array_foods.get(position).getPrice() + " $");

        String base64 = array_foods.get(position).getPhoto();
        String imageDataBytes = base64.substring(base64.indexOf(",")+1);
        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        food_photo.setImageBitmap(bitmap);

        switch (array_foods.get(position).getRating()){
            case "1.0":
                food_rating.setImageResource(R.drawable.one);
                break;
            case "2.0":
                food_rating.setImageResource(R.drawable.two);
                break;
            case "3.0":
                food_rating.setImageResource(R.drawable.three);
                break;
            case "4.0":
                food_rating.setImageResource(R.drawable.four);
                break;
            case "5.0":
                food_rating.setImageResource(R.drawable.five);
                break;
        }
        return v;

    }
}
