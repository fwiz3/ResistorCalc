package ca.kklee.resistorcalc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;

import java.util.Map;

/**
 * Created by Keith on 09/09/2014.
 */
public class ColourPickerDialogFactory {

    public static Dialog createDialog(Context context, ResistorCalculator.Colour[] band, Map<ResistorCalculator.Colour, String> mapping, final ViewPager viewPager) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2); //2 is for theme
        builder.setAdapter(new ColourPickerAdapter(context, R.layout.color_list_item, band, mapping), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewPager.setCurrentItem(i, true);
            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }
}
