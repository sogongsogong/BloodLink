package sogongsogong.bloodlink;

public class ListViewItem {

    private int order;
    private String title ;
    private String writer ;
    private int goal;
    private String content;

    public String getContent() {   return content; }

    public void setContent(String content) { this.content = content; }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }
}
