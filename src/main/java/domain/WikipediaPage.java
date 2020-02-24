package domain;

import java.util.List;

public class WikipediaPage {

    private String pageTitle;
    private int pageId;
    private List<Edit> pageEdits; //ArrayList > LinkedList

    public WikipediaPage(String aPageTitle, int aPageId, List<Edit> anEditList){
        pageTitle = aPageTitle;
        pageId = aPageId;
        pageEdits = anEditList;
    }
}