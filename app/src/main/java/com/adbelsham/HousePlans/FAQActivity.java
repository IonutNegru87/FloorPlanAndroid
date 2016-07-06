package com.adbelsham.HousePlans;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.FaqAdapterExpList;
import CustomControls.FaqDataProvideList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FAQActivity extends AppCompatActivity {

    @InjectView(R.id.faqExpandableList)
    ExpandableListView faqExpandableList;
    List<String> title;
    HashMap<String, List<String>> faqList;
    FaqAdapterExpList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        ButterKnife.inject(this);

        faqList = FaqDataProvideList.getInfo(this);
        title = new ArrayList<String>();
        title.add(getResources().getString(R.string.faq_ques1));
        title.add(getResources().getString(R.string.faq_ques2));
        title.add(getResources().getString(R.string.faq_ques3));
        title.add(getResources().getString(R.string.faq_ques4));
        title.add(getResources().getString(R.string.faq_ques5));
        title.add(getResources().getString(R.string.faq_ques6));
        title.add(getResources().getString(R.string.faq_ques7));
        adapter = new FaqAdapterExpList(this, faqList, title);
        faqExpandableList.setAdapter(adapter);
    }

    @OnClick(R.id.backArrow)
    public void backClick() {
        this.finish();
    }

}
