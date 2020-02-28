package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Editor {

    private String user;
    private Date timestamp;
    private int numEdits;

    public Editor(String aUser, String aJsonTimestamp, int aNumEdits) throws ParseException {
        user = aUser;
        timestamp = parseDate(aJsonTimestamp);
        numEdits = aNumEdits;
    }

    public Date parseDate(String aDate) throws ParseException {
        aDate = aDate.replaceAll("T|Z", " ");
        SimpleDateFormat primitiveDate = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
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

    public int getNumEdits() {
        return numEdits;
    }

    public void setNumEdits(int numEdits) {
        this.numEdits = numEdits;
    }

    public void addNumEdits(){
        this.numEdits += 1;
    }

    @Override
    public String toString() {
        return "Editor{" +
                "user='" + user + '\'' +
                ", timestamp=" + timestamp +
                ", numEdits=" + numEdits +
                '}';
    }
}
