package by.epam.onlinetraining.content;

public class ActionResult {
    private String page;
    private NavigationType navigationType;

    public ActionResult(String page, NavigationType navigationType) {
        this.page = page;
        this.navigationType = navigationType;
    }

    public String getPage() {
        return page;
    }

    public NavigationType getNavigationType() {
        return navigationType;
    }

    @Override
    public String toString() {
        return "ActionResult{" +
                "page='" + page + '\'' +
                ", navigationType=" + navigationType +
                '}';
    }
}
