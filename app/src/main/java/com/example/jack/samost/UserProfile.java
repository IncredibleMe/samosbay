package com.example.jack.samost;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {

    boolean isLoggedIn;
    private TextView firnName, lastName, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_profile_screen_xml_ui_design);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
        String userId = "";

        if (isLoggedIn) {
            userId = Profile.getCurrentProfile().getId();

            CircleImageView imgvw = findViewById(R.id.user_profile_photo);
            TextView profileName = (TextView) findViewById(R.id.user_profile_name);

            Uri profilePictureUri = Profile.getCurrentProfile().getProfilePictureUri(160, 160);
            Glide.with(this).load(profilePictureUri)
                    .into(imgvw);
            profileName.setText(Profile.getCurrentProfile().getFirstName() + " "+ Profile.getCurrentProfile().getMiddleName()+ " " + Profile.getCurrentProfile().getLastName());

            firnName = findViewById(R.id.user_first_name);
            lastName = findViewById(R.id.user_last_name);
            userEmail = findViewById(R.id.user_email);

            getUserProfile(accessToken);
        }
        else {
            Intent myIntent = new Intent(UserProfile.this, MainActivity.class);
            UserProfile.this.startActivity(myIntent);
        }

    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            firnName.setText(first_name);
                            String last_name = object.getString("last_name");
                            lastName.setText(last_name);
                            String email = object.getString("email");
                            userEmail.setText(email);
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }
}
