package pro.sketchware.activities.resourceseditor.components.utils;

import android.content.Context;
import android.app.Activity;
import java.util.ArrayList;
import java.util.HashMap;
import pro.sketchware.activities.resourceseditor.components.models.ColorModel;

public class ColorsEditorManager {
    public String defaultHexColor = "#000000";
    public String contentPath = "";

    public ColorsEditorManager() {}
    public String getColorValue(Context context, String key, int type) {
        return "#009688";
    }
    public String getColorValue(Context context, String key, int type, boolean useNightVariant) {
        return "#009688";
    }
    public String getColorValueFromAttrs(Activity activity, String input, int type, boolean night) {
        return "#000000";
    }
    public void parseColorsXML(ArrayList<ColorModel> list, String xml) {
    }
}
