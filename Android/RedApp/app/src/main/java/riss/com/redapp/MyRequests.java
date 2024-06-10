package riss.com.redapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyRequests extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv_requests;
    SharedPreferences sh;
    String[] request_ids, statuss, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lv_requests = findViewById(R.id.lv_requests);
        lv_requests.setOnItemClickListener(this);

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) MyRequests.this;
        String q = "/my_requests/?login_id="+sh.getString("log_id","");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {
            if (jo.getString("status").equalsIgnoreCase("success")) {
                JSONArray ja = jo.getJSONArray("data");
                if (ja.length() > 0) {
                    request_ids = new String[ja.length()];
                    statuss = new String[ja.length()];
                    details = new String[ja.length()];
                    for (int i = 0; i < (ja.length()); i++) {
                        request_ids[i] = ja.getJSONObject(i).getString("request_id");
                        statuss[i] = ja.getJSONObject(i).getString("status");
                        details[i] = "\nBlood : " + ja.getJSONObject(i).getString("group")
                                + "\nUnit : " + ja.getJSONObject(i).getString("unit_required")
                                + "\nOn : " + ja.getJSONObject(i).getString("date_time")
                                + "\nStatus " + ja.getJSONObject(i).getString("status");
                    }
                    lv_requests.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, details));
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), UserHome.class));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences.Editor ed = sh.edit();
        ed.putString("request_id", request_ids[i]);
        ed.commit();

        if (statuss[i].equalsIgnoreCase("pending"))
            Toast.makeText(getApplicationContext(), "Not accepted yet.!", Toast.LENGTH_LONG).show();
        else
            startActivity(new Intent(getApplicationContext(), RequestDetails.class));
    }
}