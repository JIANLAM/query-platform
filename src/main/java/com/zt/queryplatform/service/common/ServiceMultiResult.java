package com.zt.queryplatform.service.common;

import java.util.List;

/**
 * 通用多结果Service返回结构
 * Created by linzj.
 */
public class ServiceMultiResult<T> {
    private long total;
    private int totalPages;
    private int currentPages;
    private List<T> result;


    public ServiceMultiResult(long total, List<T> result) {
        this.total = total;
        this.result = result;
    }

    public ServiceMultiResult(long total, int totalPages, int currentPages, List<T> result) {
        this.total = total;
        this.totalPages = totalPages;
        this.currentPages = currentPages;
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPages() {
        return currentPages;
    }

    public void setCurrentPages(int currentPages) {
        this.currentPages = currentPages;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getResultSize() {
        if (this.result == null) {
            return 0;
        }
        return this.result.size();
    }
}
