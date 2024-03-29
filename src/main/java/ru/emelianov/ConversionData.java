package ru.emelianov;

import java.util.List;

public class ConversionData {

    private long initTime;
    private List<SearchResult> result;

    public ConversionData(long initTime, List<SearchResult> result) {
        this.initTime = initTime;
        this.result = result;
    }

    public long getInitTime() {
        return initTime;
    }

    public void setInitTime(long initTime) {
        this.initTime = initTime;
    }

    public List<SearchResult> getResult() {
        return result;
    }

    public void setResult(List<SearchResult> result) {
        this.result = result;
    }
}
