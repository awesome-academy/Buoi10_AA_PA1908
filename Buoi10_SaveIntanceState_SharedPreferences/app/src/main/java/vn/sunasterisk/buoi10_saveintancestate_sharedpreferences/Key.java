package vn.sunasterisk.buoi10_saveintancestate_sharedpreferences;

import androidx.annotation.StringDef;

@StringDef({Key.COUNT_KEY, Key.COLOR_KEY})
public @interface Key {
    String COUNT_KEY = "count_key";
    String COLOR_KEY = "color_key";
}
