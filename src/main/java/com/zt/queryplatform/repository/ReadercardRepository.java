package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.ReaderCard;
import org.springframework.data.repository.CrudRepository;

/**
 * created by linzj on 2018/12/21
 **/

public interface ReadercardRepository extends CrudRepository<ReaderCard,Long> {

    ReaderCard findReaderCardByMuseumRule(Long readerType);
}
