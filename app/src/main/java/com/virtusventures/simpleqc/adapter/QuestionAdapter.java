package com.virtusventures.simpleqc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.virtusventures.simpleqc.R;
import com.virtusventures.simpleqc.database.Issue;

import java.util.List;

/**
 * Created by mac on 26/12/2016.
 */

public class QuestionAdapter extends ArrayAdapter<Issue> {

    private final Context context;
    private final List <Issue> values;

    public QuestionAdapter(Context context, List <Issue> values) {

        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_question, parent, false);
        Issue issue = values.get(position);

        TextView questionText = (TextView) rowView.findViewById(R.id.row_questiontext);
        questionText.setText(issue.getQuestionEn());

        TextView issueText = (TextView) rowView.findViewById(R.id.row_issuetext);
        issueText.setText(String.valueOf(issue.getIssueCount()));

        return rowView;
    }

}
