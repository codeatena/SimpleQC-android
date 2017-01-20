package com.virtusventures.simpleqc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.virtusventures.simpleqc.BuildConfig;
import com.virtusventures.simpleqc.R;
import com.virtusventures.simpleqc.control.APICallback;
import com.virtusventures.simpleqc.control.APIService;
import com.virtusventures.simpleqc.entity.Global;
import com.virtusventures.simpleqc.entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username_input)
    EditText usernameEdit;

    @BindView(R.id.password_input)
    EditText passwordEdit;

    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {

        if (subscription != null) subscription.unsubscribe();
        super.onDestroy();
    }

    public void onLogin(View view)
    {

        final KProgressHUD hud =KProgressHUD.create(LoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Logging in...")
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();

        subscription = APIService.getInstance().authoLogin(usernameEdit.getText().toString() ,passwordEdit.getText().toString());
        APIService.getInstance().setOnCallback(new APICallback() {
            @Override
            public void doNext(JsonElement jsonObject) {

                hud.dismiss();
                if (((JsonObject)jsonObject).get("state").getAsString().equals("success"))
                {
                    User user = new User((JsonObject)((JsonObject) jsonObject).get("result"));
                    Global.getInstance().setCurrentUser(user);
                    gotoMain();
                }
                else
                {
                    Toast.makeText(LoginActivity.this ,((JsonObject)jsonObject).get("result").getAsString() ,Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void doCompleted() {

            }

            @Override
            public void doError(Throwable e) {
                hud.dismiss();
                Toast.makeText(LoginActivity.this ,e.getLocalizedMessage() ,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void  gotoMain(){

        if (BuildConfig.FLAVOR.equals(getString(R.string.receiver)))
        {
            Intent intent = new Intent(this , ScansearchselectActivity.class);
            startActivity(intent);

        }
        else
        {
            Intent intent = new Intent(this , ProjectActivity.class);
            startActivity(intent);

        }
        finish();
    }
}
