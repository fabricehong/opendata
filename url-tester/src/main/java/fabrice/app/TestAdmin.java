package fabrice.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class TestAdmin {
    private Logger logger = LoggerFactory.getLogger(TestAdmin.class);

    private void loopOnPage() {
        for (int a = 0; a<20; a++) {
            checkValidity("je-d-17-02-"+getStringFromNumber(a));
            for (int i = 0; i<20; i++) {
                checkValidity("je-d-17-02-"+getStringFromNumber(a)+"-"+getStringFromNumber(i));
                for (int j=0; j<20; j++) {
                    checkValidity("je-d-17-02-"+getStringFromNumber(a)+"-"+getStringFromNumber(i) + "-" + getStringFromNumber(j));
                }
            }

        }

    }

    private String getStringFromNumber(int number) {
        if (number < 10) {
            return "0" + Integer.toString(number);
        } else return Integer.toString(number);
    }
    private void checkValidity(String file) {
        try {
            String urlAdress = "http://opendata.admin.ch/fr/dataset/" + file;
            //System.out.println(String.format("testing '%s'", urlAdress));
            URL url = new URL(urlAdress);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Authorization", basicAuthParameter);
            connection.connect();

            int code = connection.getResponseCode();
            if (code==200) {
                System.out.println(urlAdress);
            }

        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public static void main(String[] args) {
        new TestAdmin().loopOnPage();
    }

}
