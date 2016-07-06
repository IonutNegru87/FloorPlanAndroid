package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.adbelsham.HousePlans.R;

import java.util.HashMap;
import java.util.List;


public class FaqAdapterExpList extends BaseExpandableListAdapter {
    private Context ctx;
    private HashMap<String, List<String>> childDataList;
    private List<String> aboutUsList;

    public FaqAdapterExpList(Context ctx, HashMap<String, List<String>> childDataList, List<String> aboutUsList) {
        this.ctx = ctx;
        this.childDataList = childDataList;
        this.aboutUsList = aboutUsList;
    }

    @Override
    public int getGroupCount() {
        return aboutUsList.size();
    }

    @Override
    public int getChildrenCount(int arg0) {
        return childDataList.get(aboutUsList.get(arg0)).size();
    }

    @Override
    public Object getGroup(int arg0) {
        return aboutUsList.get(arg0);
    }

    @Override
    public Object getChild(int parent, int child) {

        return childDataList.get(aboutUsList.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int arg0) {
        return arg0;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {
        String parent_title = (String) getGroup(parent);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.faqs_row_item, parentView, false);
        }
        TextView parent_txt = (TextView) convertView.findViewById(R.id.rowItemText);
        parent_txt.setText(parent_title);
        return convertView;
    }

    @Override
    public View getChildView(int parent, int child, boolean LastChild, View convertView, ViewGroup parentView) {
        String entity = (String) getChild(parent, child);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.faq_child_layout_exp_list, parentView, false);
        }
        TextView boldTextView = (TextView) convertView.findViewById(R.id.boldTextView);
        boldTextView.setText(entity);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
