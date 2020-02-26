package domain;

import java.util.List;

public class WikipediaPage {

    private String pageTitle;
    private int pageId;
    private List<Editor> pageEditors; //ArrayList > LinkedList
    private Redirect redirect;

    public WikipediaPage(String aPageTitle, int aPageId, List<Editor> anEditorList){
        pageTitle = aPageTitle;
        pageId = aPageId;
        pageEditors = anEditorList;
    }

    public WikipediaPage(String aPageTitle, int aPageId, List<Editor> anEditorList, Redirect aRedirect){
        pageTitle = aPageTitle;
        pageId = aPageId;
        pageEditors = anEditorList;
        redirect = aRedirect;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public int getPageId() {
        return pageId;
    }

    public List<Editor> getPageEditors() {
        return pageEditors;
    }

    public Redirect getRedirect() {
        return redirect;
    }

    @Override
    public String toString() {
        return "WikipediaPage{" +
                "pageTitle='" + pageTitle + '\'' +
                ", pageId=" + pageId +
                ", pageEditors=" + pageEditors +
                ", redirect=" + redirect +
                '}';
    }
}