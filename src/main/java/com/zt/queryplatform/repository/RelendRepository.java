package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Relend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * created by linzj on 2018/12/24
 **/
public interface RelendRepository extends JpaRepository<Relend,Long> {

    @Override
    <S extends Relend> S save(S s);

    int countByLendId(Long lendId);
}
