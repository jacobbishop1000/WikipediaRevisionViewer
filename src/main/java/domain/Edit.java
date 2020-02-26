package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Edit {

    private String user;
    private Date timestamp;

    public Edit(String aUser, String aTimestamp) throws ParseException {
        user = aUser;
        timestamp = parseDate(aTimestamp);
    }

    public Date parseDate(String aDate) throws ParseException {
        SimpleDateFormat primitiveDate = new SimpleDateFormat("yyyy-M-ddThh:mm:ssZ");
        String dateInString = aDate;
        Date date = primitiveDate.parse(dateInString);
        return date;
    }

    public String getUser() {
        return user;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
