package hera.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeUtil {
	public static final Logger LOG = LoggerFactory.getLogger(TimeUtil.class);

	public static String getFormattedTime(long millis) {
		Duration dur = Duration.ofMillis(millis);
		String format;
		if (dur.getSeconds() >= 86400) {
			format = "u'y' D'd' H'h' m'm' s's'";
			//this has to be done since the formatter cant treat it as a duration. so if the duration is 1 day it would give out 2d since after a day it's the second day of the year.
			dur = dur.minus(1, ChronoUnit.DAYS);
		} else {
			format = "H'h' m'm' s's'";
		}
		String formatted = DateTimeFormatter.ofPattern(format).format(dur.addTo(LocalDateTime.of(0, 1, 1, 0, 0, 0)));
		String done =  formatted.replaceAll("(?<!\\d{1,100})0+\\w\\s?", "");
		return done.isEmpty() ? "0s" : done;
	}
}
