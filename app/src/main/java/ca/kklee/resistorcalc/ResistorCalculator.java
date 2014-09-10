package ca.kklee.resistorcalc;

import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keith on 15/06/2014.
 */
public class ResistorCalculator {

    private final String BANDCOLOURS = "Black:0,Brown:1,Red:2,Orange:3,Yellow:4,Green:5,Blue:6,Violet:7,Grey:8,White:9";
    private final String MULTIPLIERCOLOURS = "Black:x1,Brown:x10,Red:x100,Orange:x1K,Yellow:x10K,Green:x100K,Blue:x1M";
    private final String TOLERANCECOLOURS = "Gold:5%,Silver:10%,None:20%";
    private final String BAND1 = "Brown,Red,Orange,Yellow,Green,Blue,Violet,Grey,White";
    private final String BAND2 = "Black,Brown,Red,Orange,Yellow,Green,Blue,Violet,Grey,White";
    private final String BANDMULTIPLIER = "Black,Brown,Red,Orange,Yellow,Green,Blue";
    private final String BANDTOLERANCE = "Gold,Silver,None";
    private Colour[] band1, band2, bandM, bandT = null;
    private Map<Colour, String> band = null;
    private Map<Colour, String> mulitplier = null;
    private Map<Colour, String> tolerance = null;
    private TextView display;
    public ResistorCalculator(TextView display) {
        this.display = display;
        band = map(BANDCOLOURS);
        mulitplier = map(MULTIPLIERCOLOURS);
        tolerance = map(TOLERANCECOLOURS);
        band1 = split(BAND1);
        band2 = split(BAND2);
        bandM = split(BANDMULTIPLIER);
        bandT = split(BANDTOLERANCE);
    }

    public static int getColourResource(Colour colour) {
        switch (colour) {
            case BLACK:
                return R.color.black;
            case BROWN:
                return R.color.brown;
            case RED:
                return R.color.red;
            case ORANGE:
                return R.color.orange;
            case YELLOW:
                return R.color.yellow;
            case GREEN:
                return R.color.green;
            case BLUE:
                return R.color.blue;
            case VIOLET:
                return R.color.violet;
            case GREY:
                return R.color.grey;
            case WHITE:
                return R.color.white;
            case GOLD:
                return R.color.gold;
            case SILVER:
                return R.color.silver;
            case NONE:
            default:
                return android.R.color.transparent;
        }
    }

    public static int getComplimentColourResource(Colour colour) {
        switch (colour) {
            case BLACK:
                return R.color.black_c;
            case BROWN:
                return R.color.brown_c;
            case RED:
                return R.color.red_c;
            case ORANGE:
                return R.color.orange_c;
            case YELLOW:
                return R.color.yellow_c;
            case GREEN:
                return R.color.green_c;
            case BLUE:
                return R.color.blue_c;
            case VIOLET:
                return R.color.violet_c;
            case GREY:
                return R.color.grey_c;
            case WHITE:
                return R.color.white_c;
            case GOLD:
                return R.color.gold_c;
            case SILVER:
                return R.color.silver_c;
            case NONE:
            default:
                return R.color.black;
        }
    }

    private HashMap<Colour, String> map(String raw) {
        HashMap<Colour, String> map = new HashMap<Colour, String>();
        String[] pairs = raw.split(",");
        for (int i = 0; i < pairs.length; i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            map.put(Colour.valueOf(keyValue[0].toUpperCase()), keyValue[1]);
        }
        return map;
    }

    private Colour[] split(String raw) {
        String[] items = raw.split(",");
        Colour[] array = new Colour[items.length];
        for (int i = 0; i < items.length; i++) {
            array[i] = Colour.valueOf(items[i].toUpperCase());
        }
        return array;
    }

    public Map<Colour, String> getBand() {
        return band;
    }

    public Map<Colour, String> getMulitplier() {
        return mulitplier;
    }

    public Map<Colour, String> getTolerance() {
        return tolerance;
    }

    public Colour[] getBand1() {
        return band1;
    }

    public Colour[] getBand2() {
        return band2;
    }

    public Colour[] getBandMulitpler() {
        return bandM;
    }

    public Colour[] getBandTolerance() {
        return bandT;
    }

    public void calculate(int band1Value, int band2Value, int bandMValue, int bandTValue) {
        int value1 = Integer.valueOf(band.get(band1[band1Value]));
        int value2 = Integer.valueOf(band.get(band2[band2Value]));
        int valueM = (int) Math.pow(10, Integer.valueOf(band.get(bandM[bandMValue])));
        double value = (value1 * 10 + value2) * valueM;

        String shortValue = "";
        if (value >= 1000000) {
            shortValue = "M";
            value /= 1000000;
        } else if (value >= 1000) {
            shortValue = "K";
            value /= 1000;
        }
        String string = "";
        if (value == (int) value) {
            string = (int) value + shortValue + " \u2126 \u00B1" + tolerance.get(bandT[bandTValue]);
        } else {
            string = value + shortValue + " \u2126 \u00B1" + tolerance.get(bandT[bandTValue]);
        }

        display.setText(string);
    }

    public enum BandType {BAND1, BAND2, BAND3, MULTIPLIER, TOLERANCE}

    public enum Colour {BLACK, BROWN, RED, ORANGE, YELLOW, GREEN, BLUE, VIOLET, GREY, WHITE, GOLD, SILVER, NONE}
}
