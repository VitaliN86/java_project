import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

/**
 *
 */
public class CheckURLTask implements Runnable {
    private final URLInformation urlInfo;

    public CheckURLTask(URLInformation urlInfo) {
        this.urlInfo = urlInfo;
    }

    @Override
    public void run() {
        LocalDate nowDate = LocalDate.now();
        try {
            URL url = new URL(urlInfo.getURL());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            System.out.println(urlInfo.getID() + ". " + code + " вернул URL=" + urlInfo.getURL());
            DBWorker.getInstance().changeURLInfo(urlInfo.getID(), code, nowDate);
        } catch (Exception e) {
            try {
                DBWorker.getInstance().changeURLInfo(urlInfo.getID(), null, nowDate);
            } catch (Exception e2) {
                System.out.println("Failed to work with DB");
            }
            System.out.println("Нет доступа к URL=" + urlInfo.getURL());
        }
    }
}
