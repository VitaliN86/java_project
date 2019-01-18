import java.time.LocalDate;
import java.util.Date;

/**
 *
 */
public class URLInformation {
    private int id;
    private String url;
    private LocalDate date;
    private Integer status;

    public URLInformation(int id, String url, LocalDate date, Integer status) {
        this.id = id;
        this.url = url;
        this.date = date;
        this.status = status;
    }

    public int getID() {
        return id;
    }

    public String getURL() {
        return url;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getStatus() {
        return status;
    }
}
