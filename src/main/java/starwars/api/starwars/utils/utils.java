package starwars.api.starwars.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class utils {
    public static Date formaterDate(String string) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
        return formatter.parse(string);
    }

}
