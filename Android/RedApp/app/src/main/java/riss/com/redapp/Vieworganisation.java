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

public class Vieworganisation extends AppCompatActivity implements JsonResponse {
    ListView l1;
    SharedPreferences sh;
    String[] organization_name, phone, email, value;
    public static String phn, uid, tlati, tlongi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieworganisation);

        l1 = (ListView) findViewById(R.id.list);
//        l1.setOnItemClickListener(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) Vieworganisation.this;
        String q = "/Vieworganisation?login_id=" +sh.getString("login_id", "")+"&oid="+ViewCamps.oid;
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                organization_name = new String[ja1.length()];

                phone = new String[ja1.length()];

                email = new String[ja1.length()];


                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    organization_name[i] = ja1.getJSONObject(i).getString("organization_name");


                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    email[i] = ja1.getJSONObject(i).getString("email");





                    value[i] = "organization_name:" + organization_name[i]  + "\nphone: " + phone[i]+ "\nemail: " + email[i] ;

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