package br.com.aramosdev.testeandroid.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class DateUtils {

    private static final Locale ptBR = new Locale("pt", "BR");
    private static final java.text.DateFormat formatter
            = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public static String getFormattedDate(String dateStr) {
        Date date;
        try {
            date = DateUtils.formatter.parse(dateStr);
        } catch (Exception e) {
            return null;
        }

        return DateUtils.getFormattedString("dd/MM/yyyy", date);
    }

    public static String getFormattedString(String format, Date date){
        java.text.DateFormat formatterOut = new SimpleDateFormat(format, DateUtils.ptBR);

        return date != null ? String.valueOf(formatterOut.format(date)).toUpperCase() : null;
    }
}
