package domainTests;

import domain.Editor;
import domain.Redirect;
import domain.WikipediaPage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WikipediaPageTest {

    @Test
    void getPageTitleMethodTest() {
        WikipediaPage wikiPage = new WikipediaPage("hello", 124214, new ArrayList<Editor>(), new ArrayList<Editor>());
        assertEquals("hello", wikiPage.getPageTitle());
    }

    @Test
    void getPageIdMethodTest() {
        WikipediaPage wikiPage = new WikipediaPage("hello", 124214, new ArrayList<Editor>(), new ArrayList<Editor>());
        assertEquals(124214, wikiPage.getPageId());
    }

    @Test
    void getRedirectMethodTest() {
        Redirect redirect = new Redirect("Obama", "Barack Obama");
        WikipediaPage wikiPage = new WikipediaPage("hello", 124214, new ArrayList<Editor>(), new ArrayList<Editor>());
        assertEquals(redirect, wikiPage.getRedirect());
    }
}