package riss.com.redapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewrequirement extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] name, place, phone,unit_required,group, value,user_id,statu;
    public static String phn, uid, tlati, tlongi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrequirement);

        l1 = (ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) Viewrequirement.this;
        String q = "/Viewrequirement?log_id=" +sh.getString("log_id", "");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                name = new String[ja1.length()];

                place = new String[ja1.length()];

                unit_required = new String[ja1.length()];
                group = new String[ja1.length()];
                phone = new String[ja1.length()];
                user_id = new String[ja1.length()];
               





                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    name[i] = ja1.getJSONObject(i).getString("bankname");


                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    place[i] = ja1.getJSONObject(i).getString("place");
                    unit_required[i] = ja1.getJSONObject(i).getString("unit_required");
                    user_id[i] = ja1.getJSONObject(i).getString("donar_id");




                    group[i] = ja1.getJSONObject(i).getString("group");






                    value[i] = "name:" + name[i]  + "\nphone: " + phone[i]+ "\nplace: " + place[i]  + "\nunit_required: " + unit_required[i]+ "\ngroup: " + group[i] ;

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        uid=user_id[i];
        final CharSequence[] items = {"availability","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewrequirement.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("availability")) {

                    JsonReq JR = new JsonReq(getApplicationContext());
                    JR.json_response = (JsonResponse) Viewrequirement.this;
                    String q = "/availability?log_id=" +sh.getString("log_id", "")+"&uid="+uid;
                    JR.execute(q);

                }




                else if (items[item].equals("Cancel")) {


                    dialog.dismiss();
                }
            }

        });
        builder.show();

    }
}