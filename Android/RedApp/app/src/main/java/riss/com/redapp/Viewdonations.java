package riss.com.redapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewdonations extends AppCompatActivity implements JsonResponse {
    ListView l1;
    SharedPreferences sh;
    String[] donar_status, first_name, last_name,age,group, value,phone,email;
    public static String phn, uid, tlati, tlongi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdonations);
        l1 = (ListView) findViewById(R.id.list);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) Viewdonations.this;
        String q = "/Viewdonations?log_id=" +sh.getString("log_id", "");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                donar_status = new String[ja1.length()];

                first_name = new String[ja1.length()];

                last_name = new String[ja1.length()];


                age = new String[ja1.length()];
                group = new String[ja1.length()];

                phone = new String[ja1.length()];

                email = new String[ja1.length()];


                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    donar_status[i] = ja1.getJSONObject(i).getString("donar_status");


                    first_name[i] = ja1.getJSONObject(i).getString("first_name");
                    last_name[i] = ja1.getJSONObject(i).getString("last_name");
                    age[i] = ja1.getJSONObject(i).getString("age");


                    group[i] = ja1.getJSONObject(i).getString("group");
                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    email[i] = ja1.getJSONObject(i).getString("email");




                    value[i] = "donar_status:" + donar_status[i]  + "\nfirst_name: " + first_name[i]+ "\nlast_name: " + last_name[i]+ "\nage: " + age[i]+ "\ngroup: " + group[i]+ "\nphone: " + phone[i] + "\nemail: " + email[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                l1.setAdapter(ar);


            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }
}