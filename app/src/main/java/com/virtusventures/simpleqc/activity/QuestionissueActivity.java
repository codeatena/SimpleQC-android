package com.virtusventures.simpleqc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.virtusventures.simpleqc.R;
import com.virtusventures.simpleqc.database.DatabaseManager;
import com.virtusventures.simpleqc.database.Issue;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionissueActivity extends Activity {

    @BindView(R.id.issue_editText)
    EditText issueEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionissue);

        ButterKnife.bind(this);

        Issue issue = (Issue) getIntent().getSerializableExtra("issue");
        issueEdit.setText(String.valueOf(issue.getIssueCount()));

    }

    public void onBack(View view)
    {
        finish();
    }

    public void onDone(View view)
    {
        Issue issue = (Issue) getIntent().getSerializableExtra("issue");
        DatabaseManager.getInstance().updateIssue(issue , Integer.parseInt(issueEdit.getText().toString()));

        finish();
    }
}
