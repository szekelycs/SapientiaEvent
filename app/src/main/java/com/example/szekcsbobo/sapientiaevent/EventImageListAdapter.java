package com.example.szekcsbobo.sapientiaevent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by szekc on 30/12/2017.
 */

public class EventImageListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private List<String> imageUrls;

    public EventImageListAdapter(Context context, List<String> imageUrls) {
        super(context, R.layout.event_image, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.event_image, parent, false);
        }

        GlideApp.with(context)
                .load(imageUrls.get(position))
                .into((ImageView) convertView);


        return convertView;
    }

}
