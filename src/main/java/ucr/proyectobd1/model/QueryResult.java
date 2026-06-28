package ucr.proyectobd1.model;

import java.util.Map;

public class QueryResult {
    private String title;
    private Map<String, Object> data;

    public QueryResult(String title, Map<String, Object> data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
