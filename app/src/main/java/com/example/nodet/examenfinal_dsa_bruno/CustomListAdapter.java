package com.example.nodet.examenfinal_dsa_bruno;

/**
 * Created by nodet on 1/6/17.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> itemname;
    private final List<String> imgid;



    public CustomListAdapter(Activity context, List<String> itemname, List<String> imgid) {
        super(context, R.layout.mylist, itemname);


        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname.get(position));
        Picasso.with(getContext()).load(imgid.get(position)).into(imageView);
        extratxt.setText("Followers de: "+itemname.get(position));
        return rowView;

    };
}