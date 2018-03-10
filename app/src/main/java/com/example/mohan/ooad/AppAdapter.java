
package com.example.mohan.ooad;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

public class AppAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<AppList> listStorage;
    private boolean isSelected[];
    Context context1;

    public AppAdapter(Context context, List<AppList> customizedListView) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
        isSelected = new boolean[listStorage.size()];
        this.context1 = context;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.row, parent, false);

            listViewHolder.relativeLayout = convertView.findViewById(R.id.container);
            listViewHolder.textInListView = convertView.findViewById(R.id.list_app_name);
            listViewHolder.imageInListView = convertView.findViewById(R.id.app_icon);
            listViewHolder.checkedImage = convertView.findViewById(R.id.row_list_checkbox_image);

            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) convertView.getTag();
        }

        listViewHolder.textInListView.setText(listStorage.get(position).getName());
        listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
        listViewHolder.checkedImage.setImageDrawable(ContextCompat.getDrawable(context1, R.drawable.ic_check_box_outline_blank));

        listViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set the check text view
                boolean flag = listViewHolder.textInListView.isChecked();
                listViewHolder.textInListView.setChecked(!flag);
                isSelected[position] = !isSelected[position];

                if (listViewHolder.textInListView.isChecked()) {
                    listViewHolder.checkedImage.setImageDrawable(ContextCompat.getDrawable(context1, R.drawable.ic_check_box));
                } else {
                    listViewHolder.checkedImage.setImageDrawable(ContextCompat.getDrawable(context1, R.drawable.ic_check_box_outline_blank));
                }
            }
        });

        return convertView;
    }


    public boolean[] getSelectedFlags() {
        return isSelected;
    }

    static class ViewHolder {
        RelativeLayout relativeLayout;
        CheckedTextView textInListView;
        ImageView imageInListView;
        ImageView checkedImage;
    }
}