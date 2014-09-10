package ca.kklee.resistorcalc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import ca.kklee.resistorcalc.ResistorCalculator.BandType;
import ca.kklee.resistorcalc.ResistorCalculator.Colour;

/**
 * Created by Keith on 02/06/2014.
 */
public class ColourViewPagerAdapter extends FragmentStatePagerAdapter {

    private Colour[] band;
    private Map<Colour, String> mapping;
    private BandType bandType;

    public ColourViewPagerAdapter(FragmentManager fm, ResistorCalculator rc, BandType bandType) {
        super(fm);
        this.bandType = bandType;
        switch (bandType) {
            case BAND1:
                this.band = rc.getBand1();
                this.mapping = rc.getBand();
                break;
            case BAND2:
                this.band = rc.getBand2();
                this.mapping = rc.getBand();
                break;
            case BAND3:
//                this.band = rc.getBand3();
//                this.mapping = rc.getBand();
                break;
            case MULTIPLIER:
                this.band = rc.getBandMulitpler();
                this.mapping = rc.getMulitplier();
                break;
            case TOLERANCE:
                this.band = rc.getBandTolerance();
                this.mapping = rc.getTolerance();
                break;
        }

    }

    @Override
    public int getCount() {
        return band.length;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("ID", position);

        ColorFragment colorFragment = new ColorFragment();
        colorFragment.setArguments(bundle);
        return colorFragment;
    }

    @SuppressLint("ValidFragment")
    private class ColorFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int id = getArguments().getInt("ID");
            Colour colour = band[id];

            View rootView = inflater.inflate(R.layout.color_item, container, false);
            rootView.setBackgroundColor(getResources().getColor(ResistorCalculator.getColourResource(colour)));

            TextView text = (TextView) rootView.findViewById(R.id.colour_text);
            String value = mapping.get(colour);
            text.setText(value);
            text.setTextColor(getResources().getColor(ResistorCalculator.getComplimentColourResource(colour)));
            if (bandType.equals(BandType.MULTIPLIER)) {
                text.setTextSize(18);
            }
            return rootView;
        }

    }
}
