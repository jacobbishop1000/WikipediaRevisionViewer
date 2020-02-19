package domain;

import java.util.HashMap;

public class WikipediaPage {

    private String pageTitle;
    private int pageId;
    private List<Edit> edits; //Figure out list type.

    public WikipediaPage(String aPageTitle, int aPageId, List<Edit> anEditList){
        pageTitle = aPageTitle;
        pageId = aPageId;
        edits = anEditList;
    }
}
