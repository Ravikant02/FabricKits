package com.example.rnd.rnd;

import android.content.Intent;
import android.icu.util.TimeZone;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.LoginEvent;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "Fi3WTUddcXz76lH9AupHibj4F";
    private static final String TWITTER_SECRET = "1y3ZZVEK8oy5eXrz1Yl5Cq1XCmDyuqEEOb8Up9aaYD5scJ4HCa";
    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        // Digits.Builder digitsBuilder = new Digits.Builder().withTheme(R.style.CustomDigitsTheme);
        //Fabric.with(this, new TwitterCore(authConfig), digitsBuilder.build());

        Fabric.with(this, new Answers(), new Crashlytics(), new Twitter(authConfig), new Digits.Builder().build());
        setContentView(R.layout.activity_main);

        /*TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        String tmp = now.toString().split("GMT")[1];
        String tmp1 = tmp.split(" ")[0];
        Log.e("TAG==", tmp1);*/

        /***FOR ANSWERS CODE HERE***/
        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Use your own attributes to track content views in your app
                /*Answers.getInstance().logContentView(new ContentViewEvent()
                        .putContentName("Tweet")
                        .putContentType("Video")
                        .putContentId("1234")
                        .putCustomAttribute("Favorites Count", 20)
                        .putCustomAttribute("Screen Orientation", "Landscape"));*/
                /*Answers.getInstance().logLogin(new LoginEvent()
                        .putCustomAttribute("Is Login", "Yes")
                        .putSuccess(true));*/
                Answers.getInstance().logCustom(new CustomEvent("B.One Hubs")
                        .putCustomAttribute("Hub Id", "123456789632")
                        .putCustomAttribute("Hub Id", "123456789633")
                        .putCustomAttribute("Hub Id", "123456789634")
                        .putCustomAttribute("Hub Id", "123456789635")
                        .putCustomAttribute("Hub Id", "123456789636")
                        .putCustomAttribute("Hub Id", "123456789637")
                        .putCustomAttribute("Hub Name", "My Hub"));
                Answers.getInstance().logCustom(new CustomEvent("Tweet Viewed")
                        .putCustomAttribute("Media Type", "Image")
                        .putCustomAttribute("Length", 120));
            }
        });

        /***FOR LOGIN WITH TWITTER CODE HERE***/

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        /***FOR DIGITS CODE HERE***/
        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // TODO: associate the session userID with your user model
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
