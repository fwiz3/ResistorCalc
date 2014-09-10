package ca.kklee.resistorcalc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Map;

import ca.kklee.resistorcalc.ResistorCalculator.Colour;

/**
 * Created by Keith on 09/09/2014.
 */
public class ColourPickerAdapter extends ArrayAdapter<Colour> {

    private int resource;
    private Colour[] items;
    private LayoutInflater inflater;
    private Map<Colour, String> mapping;

    public ColourPickerAdapter(Context context, int resource, Colour[] items, Map<Colour, String> mapping) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
        this.mapping = mapping;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ColourItem holder;
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            holder = new ColourItem();
            holder.text = (TextView) convertView.findViewById(R.id.colour_list_text);
            convertView.setTag(holder);
        } else {
            holder = (ColourItem) convertView.getTag();
        }
        Colour colour = items[position];
        convertView.setBackgroundColor(convertView.getResources().getColor(ResistorCalculator.getColourResource(colour)));

        String value = mapping.get(colour);
        holder.text.setText(value);
        holder.text.setTextColor(convertView.getResources().getColor(ResistorCalculator.getComplimentColourResource(colour)));

        return convertView;
    }

    static private class ColourItem {
        TextView text;
    }

}
