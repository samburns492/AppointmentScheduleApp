package schedulingapp.utilities;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

public class Translator {
        
        public static LocalDateTime timestampUTCtoLDT(Timestamp ts) throws ParseException {
            if (ts!=null) {
                LocalDateTime ldt = ts.toLocalDateTime();
                ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
                ZonedDateTime zdtlocal = zdt.withZoneSameInstant(ZoneId.systemDefault());
                LocalDateTime processed = zdtlocal.toLocalDateTime();
                       
                return processed;
                
            }
            
            return null;
        }
       
       public static String calendarToString(GregorianCalendar cal) {
           
            Date date = cal.getTime();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MMM-dd");
            fmt.setCalendar(cal);
            String datex = fmt.format(date);
            
           return datex;
       }
}

