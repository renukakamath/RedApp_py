package riss.com.redapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements JsonResponse {

    TextView tv_signup;
    EditText ed_username, ed_password;
    Button bt_login;
    SharedPreferences sh;

    public static String login_id,usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed_username = findViewById(R.id.ed_username);
        ed_password = findViewById(R.id.ed_password);
        tv_signup = findViewById(R.id.tv_signup);
        bt_login = findViewById(R.id.bt_login);

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ed_username.getText().toString();
                String password = ed_password.getText().toString();

                if (username.equalsIgnoreCase("")|| username.length()==8)
                    ed_username.setError("Username please");
                else if (password.equalsIgnoreCase("") || password.length()==8)
                    ed_password.setError("Password please");
                else {
                    JsonReq JR = new JsonReq(getApplicationContext());
                    JR.json_response = (JsonResponse) Login.this;
                    String q = "/login/?username=" + username + "&password=" + password;
                    JR.execute(q);
                }
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                login_id = ja1.getJSONObject(0).getString("login_id");
                usertype = ja1.getJSONObject(0).getString("usertype");

                SharedPreferences.Editor e = sh.edit();
                e.putString("log_id", login_id);
                e.commit();

                if(usertype.equals("receiver"))
                {
                    Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), UserHome.class));
                }


                else if(usertype.equals("donor"))
                {
                    Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), Donerhome.class));
                }

            }
            else {
                Toast.makeText(getApplicationContext(), "Login failed invalid username and password", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
