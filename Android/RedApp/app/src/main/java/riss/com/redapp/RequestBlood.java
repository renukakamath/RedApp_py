package riss.com.redapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestBlood extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener {

    EditText ed_unit_req;
    Spinner sp_group;
    Button bt_submit;
    String[] group_ids, groups;
    String blood = "0", unit = "0";
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed_unit_req = findViewById(R.id.ed_unit_req);
        sp_group = findViewById(R.id.sp_group);
        bt_submit = findViewById(R.id.bt_submit);
        sp_group.setOnItemSelectedListener(this);

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) RequestBlood.this;
        String q = "/get_groups/";
        JR.execute(q);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unit = ed_unit_req.getText().toString();
                int flg = 0;
                if (unit.equalsIgnoreCase("") || unit.equalsIgnoreCase("0")) {
                    flg++;
                    ed_unit_req.setError("Enter valid unit");
                }
                if (blood.equalsIgnoreCase("0")) {
                    flg++;
                    Toast.makeText(getApplicationContext(), "Choose group.!", Toast.LENGTH_LONG).show();
                }
                if (flg == 0) {
                    JsonReq JR = new JsonReq(getApplicationContext());
                    JR.json_response = (JsonResponse) RequestBlood.this;
                    String q = "/send_request/?blood=" + blood + "&unit=" + unit + "&login_id="+sh.getString("log_id","");
                    JR.execute(q);
                }
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            if (jo.getString("method").equalsIgnoreCase("get_groups")) {
                if (jo.getString("status").equalsIgnoreCase("success")) {
                    JSONArray ja = jo.getJSONArray("data");
                    if (ja.length() > 0) {
                        group_ids = new String[ja.length() + 1];
                        groups = new String[ja.length() + 1];
                        group_ids[0] = "0";
                        groups[0] = "Choose";
                        for (int i = 1; i < (ja.length() + 1); i++) {
                            group_ids[i] = ja.getJSONObject(i - 1).getString("group_id");
                            groups[i] = ja.getJSONObject(i - 1).getString("group");
                        }
                        sp_group.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, groups));
                    }
                }
            }
            if (jo.getString("method").equalsIgnoreCase("send_request")) {
                if (jo.getString("status").equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Success.!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MyRequests.class));
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exc : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        blood = group_ids[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), UserHome.class));
    }
}
