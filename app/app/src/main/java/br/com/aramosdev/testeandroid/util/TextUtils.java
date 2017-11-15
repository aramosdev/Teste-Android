package br.com.aramosdev.testeandroid.util;

import java.util.List;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class TextUtils {

    public static boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty() || text.trim().length() == 0;
    }

    public static boolean isEmptyOrNull(List list) {
        return list == null || list.isEmpty();
    }
}
