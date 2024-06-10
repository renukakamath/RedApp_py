package riss.com.redapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestDetails extends AppCompatActivity implements JsonResponse {

    SharedPreferences sh;
    TextView tv_by, tv_place, tv_phone, tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        tv_by = findViewById(R.id.tv_by);
        tv_place = findViewById(R.id.tv_place);
        tv_phone = findViewById(R.id.tv_phone);
        tv_email = findViewById(R.id.tv_email);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) RequestDetails.this;
        String q = "/request_details/?request_id=" + sh.getString("request_id", "0");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {
            if (jo.getString("status").equalsIgnoreCase("success")) {
                JSONArray ja = jo.getJSONArray("data");
                if (ja.length() > 0) {
                    tv_by.setText(ja.getJSONObject(0).getString("name"));
                    tv_place.setText(ja.getJSONObject(0).getString("place"));
                    tv_phone.setText(ja.getJSONObject(0).getString("phone"));
                    tv_email.setText(ja.getJSONObject(0).getString("email"));
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MyRequests.class));
    }
}
