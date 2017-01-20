package com.virtusventures.simpleqc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.virtusventures.simpleqc.R;
import com.virtusventures.simpleqc.adapter.ProjectAdapter;
import com.virtusventures.simpleqc.control.APICallback;
import com.virtusventures.simpleqc.control.APIService;
import com.virtusventures.simpleqc.entity.Global;
import com.virtusventures.simpleqc.entity.Project;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

public class ProjectActivity extends Activity {

    @BindView(R.id.project_list)
    ListView projectListView;
    ProjectAdapter adapter;

    List <Project> projectList = new ArrayList<>();
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        ButterKnife.bind(this);

        projectListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter = new ProjectAdapter(this , projectList);
        projectListView.setAdapter(adapter);
        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ProjectActivity.this , QuestionActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("project", projectList.get(i));
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        getProject();
    }

    @Override
    protected void onDestroy() {

        if (subscription != null) subscription.unsubscribe();
        super.onDestroy();
    }

    private void getProject()
    {

        if (Global.getInstance().getCurrentUser().type.equals("2")) // china inspector
            subscription = APIService.getInstance().getCNProject(Global.getInstance().getCurrentUser().dbid);
        else
            subscription = APIService.getInstance().getUSProject(Global.getInstance().getCurrentUser().dbid);

        APIService.getInstance().setOnCallback(new APICallback() {
            @Override
            public void doNext(JsonElement jsonObject) {

                projectList.clear();
                JsonArray jsonArray = (JsonArray)jsonObject;
                for (int i = 0 ; i < jsonArray.size() ; i ++)
                {
                    JsonObject projectJson = (JsonObject)jsonArray.get(i);
                    Project project = new Project(projectJson);
                    projectList.add(project);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void doCompleted() {

            }

            @Override
            public void doError(Throwable e) {

                Toast.makeText(ProjectActivity.this ,e.getLocalizedMessage() ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBack(View view)
    {
        finish();
    }

}
