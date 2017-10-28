package com.spittr.pojo;

public class Page {
    private int totalNumber;    // total number
    private int currentPage;    // current page number
    private int totalPage;      // total page number
    private int pageNumber = 5; // how many items for each page
    private int dbIndex;        // 'limit' parameters in db
    private int dbNumber;

    public void count() {
        int totalPageTemp = totalNumber / pageNumber;
        int plus = (totalNumber % pageNumber) == 0 ? 0 : 1;
        totalPageTemp = totalPageTemp + plus;
        if (totalPageTemp <= 0) {
            totalPageTemp = 1;
        }
        totalPage = totalPageTemp;

        if (totalPage < currentPage) {
            currentPage = totalPage;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        dbIndex = (currentPage - 1) * pageNumber;
        dbNumber = pageNumber;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
        count();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getDbIndex() {
        return dbIndex;
    }

    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }

    public int getDbNumber() {
        return dbNumber;
    }

    public void setDbNumber(int dbNumber) {
        this.dbNumber = dbNumber;
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalNumber=" + totalNumber +
                ", currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
