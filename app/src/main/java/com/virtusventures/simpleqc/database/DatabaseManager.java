package com.virtusventures.simpleqc.database;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.virtusventures.simpleqc.entity.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 11/01/2017.
 */

public class DatabaseManager {

    private static DatabaseManager ourInstance = new DatabaseManager();
    public static DatabaseManager getInstance() {
        return ourInstance;
    }

    public Issue addIssue(Question question ,String pid)
    {
        Issue issue = new Issue();
        issue.id = pid + question.id;
        issue.questionid = question.id;
        issue.pid = pid;
        issue.questionEn = question.questionEn;
        issue.questionCn = question.questionCn;
        issue.issueCount = 0;
        issue.save();
        return issue;
    }

    public Issue getIssue(String id)
    {
        List<Issue> issues = SQLite.select().
                from(Issue.class).
                where(Issue_Table.id.eq(id)).
                queryList();
        return issues.get(0);
    }

    public boolean hasIssue(String id)
    {
        List<Issue> issues = SQLite.select().
                from(Issue.class).
                where(Issue_Table.id.eq(id)).
                queryList();
        return (issues.size() == 0) ? false : true;
    }

    public void updateIssue(Issue issue ,Integer count){

        issue.issueCount = count;
        issue.save();
    }

    public List <Issue> getIssus(List <Question> questions ,String pId)
    {
        List <Issue> issueList = new ArrayList<>();
        for (int i = 0 ; i < questions.size() ; i ++)
        {
            Question question = questions.get(i);
            String id = pId + question.id;
            if (hasIssue(id))
            {
                issueList.add(getIssue(id));
            }
            else
            {
                Issue issue = addIssue(question ,pId);
                issueList.add(issue);
            }
        }

        return issueList;
    }

    public void resetIssues(List <Issue> issues)
    {
        for (int i = 0 ; i < issues.size() ; i ++)
        {
            Issue issue = issues.get(i);
            issue.setIssueCount(0);
            issue.save();
        }
    }

}
