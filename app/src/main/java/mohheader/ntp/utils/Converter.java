package mohheader.ntp.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by mohheader on 6/16/14.
 */
public class Converter {
    static public String milliSecToMinutes(int milliSeconds){
        return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds))
        );
    }
}
