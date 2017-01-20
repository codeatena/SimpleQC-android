package com.virtusventures.simpleqc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.virtusventures.simpleqc.R;
import com.virtusventures.simpleqc.adapter.QuestionAdapter;
import com.virtusventures.simpleqc.control.APICallback;
import com.virtusventures.simpleqc.control.APIService;
import com.virtusventures.simpleqc.database.DatabaseManager;
import com.virtusventures.simpleqc.database.Issue;
import com.virtusventures.simpleqc.entity.Project;
import com.virtusventures.simpleqc.entity.Question;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

public class QuestionActivity extends AppCompatActivity {

    @BindView(R.id.project_title_text)
    TextView titleText;

    @BindView(R.id.project_number_text)
    TextView numberText;

    @BindView(R.id.question_list)
    ListView questionListView;

    List <Issue> questionList = new ArrayList<>();
    QuestionAdapter adapter;

    Subscription subscription;
    String projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ButterKnife.bind(this);

        Project model = (Project) getIntent().getSerializableExtra("project");
        projectId  =  model.poNumber;

        titleText.setText(model.projectName);
        numberText.setText(model.poNumber);

        questionListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter = new QuestionAdapter(this , questionList);
        questionListView.setAdapter(adapter);
        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(QuestionActivity.this , QuestionissueActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("issue", questionList.get(i));
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {

        if (subscription != null) subscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Project model = (Project) getIntent().getSerializableExtra("project");
        getQuestion(model.projectId);

    }

    public void onDailyComplete(View view)
    {

        final KProgressHUD hud =KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();

        List <String> qIds = new ArrayList<>();
        List <String> issueCounts = new ArrayList<>();

        for (int i = 0 ; i < questionList.size() ; i ++)
        {
            Issue issue = questionList.get(i);
            qIds.add(issue.getQid());
            issueCounts.add(String.valueOf(issue.getIssueCount()));
        }

        String qidStr = TextUtils.join("," ,qIds);
        String issuecountStr = TextUtils.join("," ,issueCounts);

        subscription = APIService.getInstance().updateFailure(qidStr ,issuecountStr);
        APIService.getInstance().setOnCallback(new APICallback() {
            @Override
            public void doNext(JsonElement jsonObject) {

                hud.dismiss();
                // upload to server , reset issues once upload is completed
                DatabaseManager.getInstance().resetIssues(questionList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void doCompleted() {

            }

            @Override
            public void doError(Throwable e) {

                hud.dismiss();
                Toast.makeText(QuestionActivity.this ,e.getLocalizedMessage() ,Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onBack(View view)
    {
        finish();
    }

    private void getQuestion(String pId)
    {
        subscription = APIService.getInstance().getQuestion(pId);
        APIService.getInstance().setOnCallback(new APICallback() {
            @Override
            public void doNext(JsonElement jsonObject) {

                List <Question> questions = new ArrayList<>();
                questionList.clear();
                JsonArray jsonArray = (JsonArray)jsonObject;
                for (int i = 0 ; i < jsonArray.size() ; i ++)
                {
                    JsonObject questionJson = (JsonObject)jsonArray.get(i);
                    Question question = new Question(questionJson);
                    questions.add(question);
                }

                questionList.addAll(DatabaseManager.getInstance().getIssus(questions ,projectId));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void doCompleted() {

            }

            @Override
            public void doError(Throwable e) {

                Toast.makeText(QuestionActivity.this ,e.getLocalizedMessage() ,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
