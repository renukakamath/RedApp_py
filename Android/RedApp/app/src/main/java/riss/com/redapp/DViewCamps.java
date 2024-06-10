package riss.com.redapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class DViewCamps extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv_camps;
    String[] details, latitudes, longitudes,organ_id,no_of_units,group,group_id;
    public static String tlati,tlongi,oid,gid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_camps);

        lv_camps = findViewById(R.id.lv_camps);
        lv_camps.setOnItemClickListener(this);

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) DViewCamps.this;
        String q = "/view_camps/";
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {
            if (jo.getString("status").equalsIgnoreCase("success")) {
                JSONArray ja = jo.getJSONArray("data");
                if (ja.length() > 0) {
                    details = new String[ja.length()];
                    latitudes = new String[ja.length()];
                    longitudes = new String[ja.length()];
                    organ_id = new String[ja.length()];
                    no_of_units = new String[ja.length()];
                    group = new String[ja.length()];
                    group_id= new String[ja.length()];
                    for (int i = 0; i < (ja.length()); i++) {
                        details[i] = "\nOrganized by : " + ja.getJSONObject(i).getString("organization_name")
                                + "\nPhone : " + ja.getJSONObject(i).getString("phone")
                                + "\nemail : " + ja.getJSONObject(i).getString("email")
                                + "\nDate " + ja.getJSONObject(i).getString("date")
                                + "\nTime " + ja.getJSONObject(i).getString("time")
                              + "\nno_of_units " + ja.getJSONObject(i).getString("no_of_units")
                        + "\ngroup " + ja.getJSONObject(i).getString("group");

                        latitudes[i] = ja.getJSONObject(i).getString("latitude");
                        longitudes[i] = ja.getJSONObject(i).getString("longitude");
                        organ_id[i] = ja.getJSONObject(i).getString("organization_id");
                        group[i] = ja.getJSONObject(i).getString("group");
                        group_id[i] = ja.getJSONObject(i).getString("group_id");
                    }
                    lv_camps.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, details));
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
        tlati=latitudes[i];
        tlongi=longitudes[i];
        oid=organ_id[i];
        gid=group_id[i];

        final CharSequence[] items = {"Map","View Organisation","View  Blood details","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DViewCamps.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Map")) {

                    //                    startActivity(new Intent(getApplicationContext(),UserHotelRoomBooking.class));
                    String url = "http://www.google.com/maps?saddr=" + LocationService.lati + "" + "," + LocationService.logi + "" + "&&daddr=" + tlati + "," + tlongi;

                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);


                }
                else if (items[item].equals("View Organisation")) {
                    startActivity(new Intent(getApplicationContext(),DVieworganisation.class));


                }

                else if (items[item].equals("View  Blood details")) {
                    startActivity(new Intent(getApplicationContext(),DAvailableBloods.class));


                }


                else if (items[item].equals("Cancel")) {


                    dialog.dismiss();
                }
            }

        });
        builder.show();

    }
}