package com.virtusventures.simpleqc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.virtusventures.simpleqc.R;
import com.virtusventures.simpleqc.entity.Project;

import java.util.List;

/**
 * Created by mac on 28/12/2016.
 */

public class ProjectAdapter extends ArrayAdapter<Project> {

    private final Context context;
    private final List<Project> projects;

    public ProjectAdapter(Context context, List<Project> values) {

        super(context, -1, values);
        this.context = context;
        this.projects = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_project, parent, false);
        TextView nameText = (TextView) rowView.findViewById(R.id.name_text);
        Project project = projects.get(position);
        nameText.setText(project.projectName);
        return rowView;
    }

}