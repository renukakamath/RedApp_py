package riss.com.redapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class DAvailableBloods extends AppCompatActivity implements JsonResponse {

    ListView lv_details;
    String[] ablood_ids, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_bloods);

        lv_details = findViewById(R.id.lv_details);

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) DAvailableBloods.this;
        String q = "/available_bloods?&oid="+DViewCamps.gid;
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {
            if (jo.getString("status").equalsIgnoreCase("success")) {
                JSONArray ja = jo.getJSONArray("data");
                if (ja.length() > 0) {
                    ablood_ids = new String[ja.length()];
                    details = new String[ja.length()];
                    for (int i = 0; i < (ja.length()); i++) {
                        ablood_ids[i] = ja.getJSONObject(i).getString("ablood_id");
                        details[i] = "\nBlood : " + ja.getJSONObject(i).getString("group")
                                + "\nAvailable : " + ja.getJSONObject(i).getString("stock")
//                                + "\nAt : " + ja.getJSONObject(i).getString("name")
                                + "\n   " + ja.getJSONObject(i).getString("type")
//                                + "\n   " + ja.getJSONObject(i).getString("place")
//                                + "\n   " + ja.getJSONObject(i).getString("phone")
                                + "\n   " + ja.getJSONObject(i).getString("date_time");
                    }
                    lv_details.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, details));
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Donerhome.class));
    }
}
