package  com.Utility;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class MyDate {
    public static boolean dateValide(java.time.LocalDateTime date) {
        return date.isBefore(java.time.LocalDateTime.now());
    }
    public static boolean dateValideReservation(java.time.LocalDateTime date) {
        return date.isAfter(java.time.LocalDateTime.now());
    }

    public static boolean dateValide(java.time.LocalDate date) {
        return date.isBefore(java.time.LocalDate.now());
    }

    public static String getString(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static Date getDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate = sdf.parse(dateString);
        return new Date(utilDate.getTime());
    }
}
