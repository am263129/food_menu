package mik.voice.food_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class food_menu extends AppCompatActivity {

    private Integer GET_NEW_FOOD = 9001;
    ImageView btn_add;
    ArrayList<food_menu_item> array_food = new ArrayList<>();
    ListView food_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        food_list= findViewById(R.id.food_menu);
        set_list();
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(food_menu.this, add_food_item.class);
                startActivityForResult(intent,GET_NEW_FOOD);
            }
        });
        food_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Global.new_item = array_food.get(position);
                Intent intent = new Intent(food_menu.this, add_food_item.class);
                intent.putExtra("Type","Show");
                startActivity(intent);
            }
        });
    }
    private void  set_list(){
        food_menu_adapter adapter = new food_menu_adapter(this, R.layout.item_food_menu, array_food);
        food_list.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_NEW_FOOD && resultCode == RESULT_OK) {
            array_food.add(Global.new_item);
            set_list();
        }

    }
}
