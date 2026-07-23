package io.cordova.ifb.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.test.adapter.PersonAdapter;
import io.cordova.ifb.test.model.PersonModel;
import io.cordova.ifb.test.model.RangeModel;

public class MainTestActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    RecyclerView rvRecyclerView;
    ArrayList<PersonModel> personArray = new ArrayList<>();
    ArrayList<RangeModel> rangeArray = new ArrayList<>();
    ArrayList<String> rangeString = new ArrayList<>();
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        initView();
    }

    private void initView() {
        rvRecyclerView = findViewById(R.id.rvRecyclerView);
        btnSubmit = findViewById(R.id.btnSubmit);
        rvRecyclerView.setLayoutManager(new LinearLayoutManager(MainTestActivity.this));

        personArray.add(new PersonModel("Arpan","","75",""));
        personArray.add(new PersonModel("Rejaul Khan","","0",""));
        personArray.add(new PersonModel("Prabir","","0",""));


        personArray.add(new PersonModel("Akahnsha","A","90",""));
        personArray.add(new PersonModel("Chandan","B","80",""));
        personArray.add(new PersonModel("Saikat","C","75",""));


        personArray.add(new PersonModel("Arijit","A","90",""));
        personArray.add(new PersonModel("Saheli","B","80",""));
        personArray.add(new PersonModel("SK Piru","C","75",""));



        rangeArray.add(new RangeModel("Please Select",0));
        rangeArray.add(new RangeModel("A",90));
        rangeArray.add(new RangeModel("B",80));
        rangeArray.add(new RangeModel("C",70));
        rangeString.add("Select");
        rangeString.add("A");
        rangeString.add("B");
        rangeString.add("C");
        PersonAdapter personAdapter = new PersonAdapter(MainTestActivity.this,personArray,rangeArray,rangeString);
        rvRecyclerView.setAdapter(personAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    makeJsonObject();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void makeJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < personArray.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name",personArray.get(i).getName());
            object.put("text",personArray.get(i).getText());
            object.put("range",personArray.get(i).getRange());
            object.put("remask",personArray.get(i).getRemask());
            jsonArray.put(object);
        }

        jsonObject.put("final_array",jsonArray);
        Log.e(TAG, "makeJsonObject: "+jsonObject.toString(4));
    }
}