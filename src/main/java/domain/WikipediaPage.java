package domain;

import java.util.List;

public class WikipediaPage {

    private String pageTitle;
    private int pageId;
    private List<Edit> pageEdits; //ArrayList > LinkedList
    private Redirect redirect;

    public WikipediaPage(String aPageTitle, int aPageId, List<Edit> anEditList){
        pageTitle = aPageTitle;
        pageId = aPageId;
        pageEdits = anEditList;
    }

    public WikipediaPage(String aPageTitle, int aPageId, List<Edit> anEditList, Redirect aRedirect){
        pageTitle = aPageTitle;
        pageId = aPageId;
        pageEdits = anEditList;
        redirect = aRedirect;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public int getPageId() {
        return pageId;
    }

    public List<Edit> getPageEdits() {
        return pageEdits;
    }

    public Redirect getRedirect() {
        return redirect;
    }

    @Override
    public String toString() {
        return "WikipediaPage{" +
                "pageTitle='" + pageTitle + '\'' +
                ", pageId=" + pageId +
                ", pageEdits=" + pageEdits +
                ", redirect=" + redirect +
                '}';
    }
}