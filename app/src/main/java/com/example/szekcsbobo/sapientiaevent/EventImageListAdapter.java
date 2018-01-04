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
 * <b>EventImageListAdapter</b> is our adapter for the ListView after we opened an event from our recyclerview on the main screen
 * This adapter loads the matching images for the events and displays them in a vertical list
 * It uses the glide library
 *
 * @author: Szekely Csongor 30/12/2017.
 */

public class EventImageListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    /**
     * contains the image urls
     */

    private List<String> imageUrls;

    public EventImageListAdapter(Context context, List<String> imageUrls) {
        super(context, R.layout.event_image, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }


    /**
     *
     * @method With the help of the Glide library displays the images
     * @param position - the actual cursor position
     * @param convertView
     * @param parent
     * @return the view element
     */
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
