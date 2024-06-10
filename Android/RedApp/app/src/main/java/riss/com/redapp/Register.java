package riss.com.redapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


public class Register extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener {

    EditText ed_fname, ed_lname, ed_age, ed_phone, ed_email, ed_username, ed_password;
    Spinner sp_bloodgroup;
    RadioButton r1,r2;
    Button bt_register;
    String first_name, last_name, age, blood = "0", phone, email, username, password,type;
    String[] group_ids, groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_fname = findViewById(R.id.ed_fname);
        ed_lname = findViewById(R.id.ed_lname);
        r1=(RadioButton) findViewById(R.id.male);
        r2=(RadioButton) findViewById(R.id.female);
        ed_age = findViewById(R.id.ed_age);
        ed_phone = findViewById(R.id.ed_phone);
        ed_email = findViewById(R.id.ed_email);
        ed_username = findViewById(R.id.ed_uname_reg);
        ed_password = findViewById(R.id.ed_pass_reg);
        sp_bloodgroup = findViewById(R.id.sp_group);
        bt_register = findViewById(R.id.bt_signup);
        sp_bloodgroup.setOnItemSelectedListener(this);

        JsonReq JR = new JsonReq(getApplicationContext());
        JR.json_response = (JsonResponse) Register.this;
        String q = "/get_groups/";
        JR.execute(q);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name = ed_fname.getText().toString();
                last_name = ed_lname.getText().toString();
                age = ed_age.getText().toString();
                phone = ed_phone.getText().toString();
                email = ed_email.getText().toString();
                username = ed_username.getText().toString();
                password = ed_password.getText().toString();

                if (r1.isChecked()){
                    type="donor";
                }else  if (r2.isChecked()){
                    type="receiver";
                }

                int flg = 0;
                if (first_name.equals("")) {
                    flg++;
                    ed_fname.setError("Fill the field");
                }
                if (last_name.equals("")) {
                    flg++;
                    ed_lname.setError("Fill the field");
                }
                if (age.equalsIgnoreCase("") || age.length() > 2) {
                    flg++;
                    ed_age.setError("Valid age please");
                }
                if (email.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    flg++;
                    ed_email.setError("Enter valid email ID");
                }
                if (phone.equals("") || phone.length() != 10) {
                    flg++;
                    ed_phone.setError("Enter valid phone number");
                }
                if (blood.equalsIgnoreCase("0")) {
                    flg++;
                    Toast.makeText(getApplicationContext(), "Choose group.!", Toast.LENGTH_LONG).show();
                }
                if (username.equalsIgnoreCase("")|| username.length()==8)
                    ed_username.setError("Username please");
                if (password.equalsIgnoreCase("") || password.length()==8)
                    ed_password.setError("Password please");


                if (flg == 0) {
                    JsonReq JR = new JsonReq(getApplicationContext());
                    JR.json_response = (JsonResponse) Register.this;
                    String q = "/register/?first_name=" + first_name + "&last_name=" + last_name + "&age=" + age
                            + "&phone=" + phone + "&email=" + email + "&blood_group=" + blood
                            + "&username=" + username + "&password=" + password+"&type="+type;
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
                        sp_bloodgroup.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, groups));
                    }
                }
            }
            if (jo.getString("method").equalsIgnoreCase("register")) {
                if (jo.getString("status").equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Success.!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
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
}