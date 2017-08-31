package com.speedOMeter.speedOMeter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Patrick Wissiak on 30.08.2017.
 */

public class ViewAdapter extends BaseAdapter {
    private List<View> content;

    ViewAdapter(List<View> content) {
        this.content = content;
    }

    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public Object getItem(int i) {
        return content.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return content.get(i);
    }
}
