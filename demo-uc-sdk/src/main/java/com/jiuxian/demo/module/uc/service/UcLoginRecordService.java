package com.jiuxian.demo.module.uc.service;

import com.jiuxian.demo.module.uc.entity.UcLoginRecord;
import com.jiuxian.demo.module.uc.entity.UcUser;
import com.jiuxian.framework.util.page.Pager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UcLoginRecordService {

    UcLoginRecord findById(Integer pkid);

    int update(UcLoginRecord ucLoginRecord);

    int delete(Integer pkid);

    Integer insert(UcLoginRecord ucLoginRecord);

    List<UcLoginRecord> findByIds(List<Integer> pkidList);

    default Map<Integer, UcLoginRecord> findMapByIds(List<Integer> pkidList){
        Map<Integer, UcLoginRecord> map = new HashMap();
        List<UcLoginRecord> ucLoginRecords = findByIds(pkidList);
        for(UcLoginRecord ucLoginRecord : ucLoginRecords){
            map.put(ucLoginRecord.getPkid(), ucLoginRecord);
        }
        return map;
    }

    Pager<UcLoginRecord> pageByProperties(Map<String, Object> properties, int page);
}