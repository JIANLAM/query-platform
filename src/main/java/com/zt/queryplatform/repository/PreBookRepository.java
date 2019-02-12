package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Prebook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * created by linzj on 2018/12/25
 **/
public interface PreBookRepository extends CrudRepository<Prebook,Long> {

    List<Prebook> findAllByIsbnAndTitle(String isbn,String title);

    @Override
    <S extends Prebook> S save(S s);
}
