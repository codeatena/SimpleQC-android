package com.virtusventures.simpleqc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.virtusventures.simpleqc.R;
import com.virtusventures.simpleqc.entity.Project;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InspectActivity extends Activity {

    @BindView(R.id.name_text)
    TextView nameText;

    @BindView(R.id.po_text)
    TextView poText;

    @BindView(R.id.job_text)
    TextView jobText;

    @BindView(R.id.order_text)
    TextView orderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect);
        ButterKnife.bind(this);

        Project model = (Project) getIntent().getSerializableExtra("project");
        nameText.setText(model.projectName);
        poText.setText(model.poNumber);
        jobText.setText(model.jobNumber);
        orderText.setText(model.orderNumber);

    }

    public void onBack(View view)
    {
        finish();
    }

    public void onInspect(View view)
    {

        Intent intent = new Intent(this , QuestionActivity.class);
        startActivity(intent);
    }
}
