package util;

import java.sql.Date;
import java.text.ParseException;

public class DateConverter {
    public static java.sql.Date toSqlDate(String dateText) throws ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate = sdf.parse(dateText);
        return new Date(utilDate.getTime());
    }

    public static String toFormattedDate(java.sql.Date date) {
        return new java.text.SimpleDateFormat("dd/MM/yyyy").format(date);
    }
}
