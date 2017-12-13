package edu.coloradomesa.myproject;

/**
 * Created by Dakota on 11/9/2017.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final Vector<String> text;
    private final Vector<String> subText;
    public CustomList(Activity context,
                      Vector<String> text, Vector<String> subText) {
        super(context, R.layout.list_item, text);
        this.context = context;
        this.text = text;
        this.subText = subText;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.title_txt);
        TextView subTxtTitle = (TextView) rowView.findViewById(R.id.sub_txt);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(text.elementAt(position));
        subTxtTitle.setText(subText.elementAt(position));
        return rowView;
    }
}
