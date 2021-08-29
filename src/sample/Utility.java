package sample;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    public Utility() {

    }

    public boolean isValidUrl(String strUrl) {
        try {
            new URL(strUrl).toURI();
            System.out.println("Valid URL " + strUrl);
            return true;
        } catch (Exception exception) {
            System.out.println("Inavlid URL " + exception.getMessage());
            return false;
        }
    }

    public String encode(String url) {
        try {
            String encodeURL = URLEncoder.encode(url, "UTF-8");
            return encodeURL;
        } catch (UnsupportedEncodingException e) {
            return "Issue while encoding" + e.getMessage();
        }
    }

    public String genrateFilName() {
        String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        return fileName;
    }


}
