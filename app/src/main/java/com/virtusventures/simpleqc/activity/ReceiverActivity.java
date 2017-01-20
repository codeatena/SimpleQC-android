package com.virtusventures.simpleqc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.virtusventures.simpleqc.R;
import com.virtusventures.simpleqc.control.APICallback;
import com.virtusventures.simpleqc.control.APIService;
import com.virtusventures.simpleqc.entity.Project;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Subscription;

public class ReceiverActivity extends AppCompatActivity {

    @BindView(R.id.name_text)
    TextView nameText;

    @BindView(R.id.po_text)
    TextView poText;

    @BindView(R.id.job_text)
    TextView jobText;

    @BindView(R.id.order_text)
    TextView orderText;

    @BindView(R.id.status_text)
    TextView statusText;

    @BindView(R.id.receive_button)
    FancyButton receiveBtn;

    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        ButterKnife.bind(this);

        Project project = (Project) getIntent().getSerializableExtra("project");
        setProject(project);
    }

    @Override
    protected void onDestroy() {

        if (subscription != null) subscription.unsubscribe();
        super.onDestroy();
    }

    public void onBack(View view)
    {
        finish();
    }

    public void onReceived(View view)
    {
        final KProgressHUD hud =KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();

        Project model = (Project) getIntent().getSerializableExtra("project");
        subscription = APIService.getInstance().updateProjectReceiver(model.projectId);
        APIService.getInstance().setOnCallback(new APICallback() {
            @Override
            public void doNext(JsonElement jsonObject) {

                hud.dismiss();
                if (((JsonObject)jsonObject).get("state").getAsString().equals("success"))
                {
                    JsonObject json = (JsonObject)((JsonObject)jsonObject).get("result");
                    Project project = new Project(json);
                    setProject(project);

                    receiveBtn.setVisibility(View.GONE);
                }
                else
                {
                    Toast.makeText(ReceiverActivity.this ,((JsonObject)jsonObject).get("result").getAsString() ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void doCompleted() {

            }

            @Override
            public void doError(Throwable e) {

                hud.dismiss();
                Toast.makeText(ReceiverActivity.this ,e.getLocalizedMessage() ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setProject(Project project)
    {
        nameText.setText(project.projectName);
        poText.setText(project.poNumber);
        jobText.setText(project.jobNumber);
        orderText.setText(project.orderNumber);

        int status = Integer.parseInt(project.status);
        String[] statusArray = getResources().getStringArray(R.array.project_status);
        statusText.setText(statusArray[status]);
    }
}
