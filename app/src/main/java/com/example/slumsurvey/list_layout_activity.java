package com.example.slumsurvey;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class list_layout_activity extends ArrayAdapter<appformfirebase> {

    private Activity context;
    private List<appformfirebase> submission_preview;

    public list_layout_activity(Activity context, List<appformfirebase> submission_preview) {
        super(context, R.layout.list_layout, submission_preview);
        this.context = context;
        this.submission_preview = submission_preview;
    }

    @NonNull
    @Override
    public View getView(int position, View counterView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewHof = (TextView) listViewItem.findViewById(R.id.hof_name);
        TextView textViewAadhar = (TextView) listViewItem.findViewById(R.id.hof_aadhar);

        appformfirebase aff = submission_preview.get(position);

        textViewHof.setText(aff.getHeadoffamily());
        textViewAadhar.setText(aff.getAadhar());

        return listViewItem;
    }
}
