package com.jiuxian.framework.util.page;

import com.jiuxian.framework.util.json.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pager<T> implements java.io.Serializable {

    private static final long serialVersionUID = 1764877813066386998L;

    private int totalRows;// 总行数
    private int pageSize;// 每页显示的行数
    private int currentPage;// 当前页号
    private int totalPages;// 总页数
    private int startRow;// 当前页在数据库中的起始行
    private List<T> list = new ArrayList(); //列表中的数据内容
    private Map<Object, Object> extend = new HashMap<>(); //扩展数据

    /**
     * @param totalRows   总记录数
     * @param currentPage 当前页码
     */
    public Pager(int totalRows, int currentPage) {
        this(totalRows, currentPage, 0);
    }

    /**
     * @param totalRows   总记录数
     * @param currentPage 当前页码
     * @param pageSize    每页显示记录数
     */
    public Pager(int totalRows, int currentPage, int pageSize) {
        this.totalRows = totalRows;
        this.pageSize = pageSize <= 0 ? 10 : pageSize;
        this.currentPage = currentPage <= 1 ? 1 : currentPage;
        totalPages = (this.totalRows - 1) / this.pageSize + 1;
        this.currentPage = totalPages < this.currentPage ? 1 : this.currentPage;
        startRow = this.pageSize * (this.currentPage - 1);
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = totalPages < currentPage ? 1 : currentPage;
        this.startRow = pageSize * (this.currentPage - 1);
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getStartRow() {
        return startRow;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Map<Object, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<Object, Object> extend) {
        this.extend = extend;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pager<?> pager = (Pager<?>) o;

        if (totalRows != pager.totalRows) return false;
        if (pageSize != pager.pageSize) return false;
        if (currentPage != pager.currentPage) return false;
        if (totalPages != pager.totalPages) return false;
        if (startRow != pager.startRow) return false;
        if (!list.equals(pager.list)) return false;
        return extend.equals(pager.extend);

    }

    @Override
    public int hashCode() {
        int result = totalRows;
        result = 31 * result + pageSize;
        result = 31 * result + currentPage;
        result = 31 * result + totalPages;
        result = 31 * result + startRow;
        result = 31 * result + list.hashCode();
        result = 31 * result + extend.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "totalRows=" + totalRows +
                ", pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                ", startRow=" + startRow +
                ", list=" + JsonUtils.objectToJson(list) +
                ", extend=" + JsonUtils.objectToJson(extend) +
                '}';
    }
}
