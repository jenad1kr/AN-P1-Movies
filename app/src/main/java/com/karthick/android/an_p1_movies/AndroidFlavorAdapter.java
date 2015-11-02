package com.karthick.android.an_p1_movies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by karthick.jenadoss on 11/1/2015.
 */


    public class AndroidFlavorAdapter extends ArrayAdapter<AndroidFlavor> {
        private static final String LOG_TAG = AndroidFlavorAdapter.class.getSimpleName();


        public AndroidFlavorAdapter(Activity context, List<AndroidFlavor> androidFlavors)
        {
            super(context, 0, androidFlavors);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AndroidFlavor androidFlavor = getItem(position);


            // Adapters recycle views to AdapterViews.
            // If this is a new View object we're getting, then inflate the layout.
            // If not, this view already has the layout inflated from a previous call to getView,
            // and we modify the View widgets as usual.
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_flavor, parent, false);
            }

            ImageView iconView = (ImageView) convertView.findViewById(R.id.list_item_icon);
            iconView.setImageResource(androidFlavor.image);
            /*
            TextView versionNameView = (TextView) convertView.findViewById(R.id.list_item_version_name);
            versionNameView.setText(androidFlavor.versionName);

            TextView versionNumberView = (TextView) convertView.findViewById(R.id.list_item_versionnumber_textview);
            versionNumberView.setText(androidFlavor.versionNumber);
            */
            return convertView;
        }
}
