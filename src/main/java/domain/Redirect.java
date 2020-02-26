package domain;

public class Redirect {

    private String from;
    private String to;

    public Redirect(String aFrom, String aTo){
        from = aFrom;
        to = aTo;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
