package com.jiuxian.framework.db;

import java.util.ArrayList;
import java.util.List;

public class DataSourceKey implements java.io.Serializable {

    private String master;

    private List<String> slaves = new ArrayList<>();

    public DataSourceKey() {
    }

    public DataSourceKey(String master, List<String> slaves) {
        this.master = master;
        this.slaves = slaves;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public List<String> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<String> slaves) {
        this.slaves = slaves;
    }

    public String getRandomSlave() {
        if (this.slaves != null && slaves.size() > 0) {
            int idx = (int) (Math.random() * slaves.size());
            return slaves.get(idx);
        } else {
            return null;
        }
    }
}
