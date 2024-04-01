package ru.emelianov;

import java.util.List;

public class SearchResult {

    private String search;
    private List<Integer> result;
    private long time;

    public SearchResult(String search, List<Integer> result, long time) {
        this.search = search;
        this.result = result;
        this.time = time;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<Integer> getResult() {
        return result;
    }

    public void setResult(List<Integer> result) {
        this.result = result;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
