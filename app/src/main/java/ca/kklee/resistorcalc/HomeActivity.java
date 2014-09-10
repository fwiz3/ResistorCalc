package ca.kklee.resistorcalc;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.Map;

import ca.kklee.resistorcalc.ResistorCalculator.BandType;
import ca.kklee.resistorcalc.ResistorCalculator.Colour;

public class HomeActivity extends ActionBarActivity {

    private ResistorCalculator rc;
    private ViewPager band1, band2, band3, bandM, bandT;
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        display = (TextView) findViewById(R.id.display);
        rc = new ResistorCalculator(display);

        initBands();
        calculate();
    }

    private void initBands() {

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                calculate();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        //Band 1
        band1 = (ViewPager) findViewById(R.id.band_1);
        ColourViewPagerAdapter band1Adapter = new ColourViewPagerAdapter(getSupportFragmentManager(), rc, BandType.BAND1);
        band1.setAdapter(band1Adapter);
        final GestureDetector tapGestureDetector1 = new GestureDetector(this, new TapGestureListener(this, BandType.BAND1, band1));
        band1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tapGestureDetector1.onTouchEvent(motionEvent);
                return false;
            }
        });
        band1.setOnPageChangeListener(onPageChangeListener);

        //Band 2
        band2 = (ViewPager) findViewById(R.id.band_2);
        ColourViewPagerAdapter band2Adapter = new ColourViewPagerAdapter(getSupportFragmentManager(), rc, BandType.BAND2);
        band2.setAdapter(band2Adapter);
        final GestureDetector tapGestureDetector2 = new GestureDetector(this, new TapGestureListener(this, BandType.BAND2, band2));
        band2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tapGestureDetector2.onTouchEvent(motionEvent);
                return false;
            }
        });
        band2.setOnPageChangeListener(onPageChangeListener);

        //Band 3
//        band3 = (ViewPager) findViewById(R.id.band_3);
//        ColorViewPagerAdapter band3Adapter = new ColorViewPagerAdapter(getSupportFragmentManager(), rc, BandType.BAND3);
//        band3.setAdapter(band2Adapter);
//        final GestureDetector tapGestureDetector3 = new GestureDetector(this, new TapGestureListener(this, BandType.BAND3, band3));
//        band3.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                tapGestureDetector3.onTouchEvent(motionEvent);
//                return false;
//            }
//        });

        //Band M
        bandM = (ViewPager) findViewById(R.id.band_m);
        ColourViewPagerAdapter bandMAdapter = new ColourViewPagerAdapter(getSupportFragmentManager(), rc, BandType.MULTIPLIER);
        bandM.setAdapter(bandMAdapter);
        final GestureDetector tapGestureDetectorM = new GestureDetector(this, new TapGestureListener(this, BandType.MULTIPLIER, bandM));
        bandM.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tapGestureDetectorM.onTouchEvent(motionEvent);
                return false;
            }
        });
        bandM.setOnPageChangeListener(onPageChangeListener);

        //Band T
        bandT = (ViewPager) findViewById(R.id.band_t);
        ColourViewPagerAdapter bandTAdapter = new ColourViewPagerAdapter(getSupportFragmentManager(), rc, BandType.TOLERANCE);
        bandT.setAdapter(bandTAdapter);
        final GestureDetector tapGestureDetectorT = new GestureDetector(this, new TapGestureListener(this, BandType.TOLERANCE, bandT));
        bandT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tapGestureDetectorT.onTouchEvent(motionEvent);
                return false;
            }
        });
        bandT.setOnPageChangeListener(onPageChangeListener);
    }

    private void calculate() {
        if (band1 == null || band2 == null || bandM == null || bandT == null || rc == null) {
            return;
        }

        int band1Value = band1.getCurrentItem();
        int band2Value = band2.getCurrentItem();
//        int band3Value = band3.getCurrentItem();
        int bandMValue = bandM.getCurrentItem();
        int bandTValue = bandT.getCurrentItem();

        rc.calculate(band1Value, band2Value, bandMValue, bandTValue);
    }

    class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

        private Context context;
        private ViewPager viewPager;
        private Colour[] band;
        private Map<Colour, String> mapping;

        TapGestureListener(Context context, BandType bandType, ViewPager viewPager) {
            this.context = context;
            this.viewPager = viewPager;
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
        public boolean onSingleTapUp(MotionEvent e) {
            Dialog dialog = ColourPickerDialogFactory.createDialog(context, band, mapping, viewPager);
            dialog.show();
            return false;
        }
    }

}
