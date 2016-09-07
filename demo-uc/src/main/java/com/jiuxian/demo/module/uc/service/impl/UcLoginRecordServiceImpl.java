package com.jiuxian.demo.module.uc.service.impl;

import com.jiuxian.demo.module.uc.entity.UcLoginRecord;
import com.jiuxian.demo.module.uc.service.UcLoginRecordService;
import com.jiuxian.framework.util.page.Pager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by luqingrun on 16/8/23.
 */
@Service("ucLoginRecordService")
public class UcLoginRecordServiceImpl implements UcLoginRecordService {
    @Override
    public UcLoginRecord findById(Integer pkid) {
        return null;
    }

    @Override
    public int update(UcLoginRecord ucLoginRecord) {
        return 0;
    }

    @Override
    public int delete(Integer pkid) {
        return 0;
    }

    @Override
    public Integer insert(UcLoginRecord ucLoginRecord) {
        return null;
    }

    @Override
    public List<UcLoginRecord> findByIds(List<Integer> pkidList) {
        return null;
    }

    @Override
    public Map<Integer, UcLoginRecord> findMapByIds(List<Integer> pkidList) {
        return null;
    }

    @Override
    public Pager<UcLoginRecord> pageByProperties(Map<String, Object> properties, int page) {
        return null;
    }
}
