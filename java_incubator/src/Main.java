import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class Main {
    public static LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static void main(String[] args) throws SQLException, InterruptedException {
        LocalDate checkDate = parseDate("2019-01-09");
        int maxCountOfThreads = 100;
        ExecutorService pool = Executors.newFixedThreadPool(maxCountOfThreads);
        long start = System.nanoTime();
        List<URLInformation> urls = DBWorker.getInstance().getURLs();
        for (URLInformation urlInfo : urls) {
            LocalDate date = urlInfo.getDate();
            if (date != null && date.isAfter(checkDate)) {
                continue;
            }
            pool.submit(new CheckURLTask(urlInfo));
        }
        pool.shutdown();
        pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        // all tasks have now finished (unless an exception is thrown above)
        long stop = System.nanoTime();
        long elapsedTime = stop - start;
        System.out.println(elapsedTime/1000000000.0);
    }
}

