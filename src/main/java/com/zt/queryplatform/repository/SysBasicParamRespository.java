package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.SysBasicParam;
import org.springframework.data.repository.CrudRepository;

/**
 * created by linzj on 2018/12/24
 **/
public interface SysBasicParamRespository extends CrudRepository<SysBasicParam,Long> {

    SysBasicParam getSysBasicParamById(Long id);
}
