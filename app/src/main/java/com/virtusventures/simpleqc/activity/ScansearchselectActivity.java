package com.virtusventures.simpleqc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.virtusventures.simpleqc.R;
import com.virtusventures.simpleqc.control.APICallback;
import com.virtusventures.simpleqc.control.APIService;
import com.virtusventures.simpleqc.entity.Project;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

public class ScansearchselectActivity extends AppCompatActivity{

    @BindView(R.id.number_input)
    EditText numberInput;

    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scansearchselect_main);
        ButterKnife.bind(this);

        numberInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    fetchProject(numberInput.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {

        if (subscription != null) subscription.unsubscribe();
        super.onDestroy();
    }

    public void onSelectType(View view)
    {

        new MaterialDialog.Builder(this)
                .title(R.string.search)
                .items(R.array.single_choice)
                .positiveColor(R.color.color_gray)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        switch (which)
                        {
                            case 0:
                                numberInput.setHint("PO Number");
                                break;
                            case 1:
                                numberInput.setHint("Job Number");
                                break;
                        }
                        return true;
                    }
                })
                .positiveText(R.string.ok)
                .show();
    }

    public void onCamera(View view)
    {
        Intent intent = new Intent(this , QRActivity.class);
        startActivityForResult(intent ,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                // request with scan result
                String result=data.getStringExtra("result");
                fetchProject1(result);
            }

        }
    }

    void fetchProject(String str)
    {

        final KProgressHUD hud = showHUD();

        APICallback callback = new APICallback() {
            @Override
            public void doNext(JsonElement jsonObject) {

                hud.dismiss();
                if (jsonObject instanceof JsonArray)
                    parseJson((JsonArray) jsonObject);
                else
                {
                    Toast.makeText(ScansearchselectActivity.this ,((JsonObject)jsonObject).get("state").getAsString() ,Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void doCompleted() {

            }

            @Override
            public void doError(Throwable e) {

                hud.dismiss();
                Toast.makeText(ScansearchselectActivity.this ,e.getLocalizedMessage() ,Toast.LENGTH_SHORT).show();

            }
        };

        if (numberInput.getHint().equals("PO Number"))
            subscription = APIService.getInstance().searchByPoNumber(str);
        else
            subscription = APIService.getInstance().searchByJobNumber(str);

        APIService.getInstance().setOnCallback(callback);

    }

    void fetchProject1(String str)
    {
        final KProgressHUD hud = showHUD();
        APICallback callback = new APICallback() {
            @Override
            public void doNext(JsonElement jsonObject) {

                hud.dismiss();
                if (jsonObject instanceof JsonArray)
                    parseJson((JsonArray) jsonObject);
                else
                {
                    Toast.makeText(ScansearchselectActivity.this ,((JsonObject)jsonObject).get("state").getAsString() ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void doCompleted() {

            }

            @Override
            public void doError(Throwable e) {

                hud.dismiss();
                Toast.makeText(ScansearchselectActivity.this ,e.getLocalizedMessage() ,Toast.LENGTH_SHORT).show();

            }
        };

        subscription = APIService.getInstance().searchByPoNumber(str);
        APIService.getInstance().setOnCallback(callback);

    }

    private void parseJson(JsonArray jsonArray)
    {
        if (jsonArray.size() == 0)
        {
            Toast.makeText(ScansearchselectActivity.this ,R.string.project_search_null ,Toast.LENGTH_SHORT).show();
        }
        else
        {
            JsonObject jsonObject = (JsonObject) jsonArray.get(0);
            Project project = new Project(jsonObject);

            Intent intent = new Intent(ScansearchselectActivity.this , ReceiverActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("project", project);
            intent.putExtras(mBundle);
            startActivity(intent);
        }
    }

    private KProgressHUD showHUD()
    {
        KProgressHUD hud =KProgressHUD.create(ScansearchselectActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();

        return hud;
    }
}
