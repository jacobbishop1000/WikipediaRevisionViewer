package domain;

import java.util.List;

public class WikipediaPage {

    private String pageTitle;
    private int pageId;
    private List<Editor> pageEditorsByTimestamp; //ArrayList > LinkedList
    private List<Editor> pageEditorsByEdits;
    private Redirect redirect;

    public WikipediaPage(String aPageTitle, int aPageId, List<Editor> anEditorTimestampList, List<Editor> anEditorEditsList){
        pageTitle = aPageTitle;
        pageId = aPageId;
        pageEditorsByTimestamp = anEditorTimestampList;
        pageEditorsByEdits = anEditorEditsList;
    }

    public WikipediaPage(String aPageTitle, int aPageId, List<Editor> anEditorTimestampList, List<Editor> anEditorEditsList, Redirect aRedirect){
        pageTitle = aPageTitle;
        pageId = aPageId;
        pageEditorsByTimestamp = anEditorTimestampList;
        pageEditorsByEdits = anEditorEditsList;
        redirect = aRedirect;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public int getPageId() {
        return pageId;
    }

    public List<Editor> getPageEditorsByTimestamp() {
        return pageEditorsByTimestamp;
    }

    public List<Editor> getPageEditorsByEdits(){
        return pageEditorsByEdits;
    }

    public Redirect getRedirect() {
        return redirect;
    }

    @Override
    public String toString() {
        return "WikipediaPage{" +
                "pageTitle='" + pageTitle + '\'' +
                ", pageId=" + pageId +
                ", pageEditorsByTimestamp=" + pageEditorsByTimestamp +
                ", pageEditorsByEdits=" + pageEditorsByEdits +
                ", redirect=" + redirect +
                '}';
    }
}