package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Collection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * created by linzj on 2018/12/25
 **/
public interface CollectionRepository extends CrudRepository<Collection,Long> {


    List<Collection> findAllByPreBookIdIn(List<Long> prebookId);

    @Override
    <S extends Collection> S save(S s);
}
