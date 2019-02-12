package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Reader;
import com.zt.queryplatform.entity.dto.ReaderInfoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * created by linzj on 2018/12/21
 **/
public interface ReaderRepository extends CrudRepository<Reader,Long> {

    @Override
    Reader findOne(Long aLong);

    Reader findReaderByPeopleIdAndLibraryId(Long peopleId,Long libraryId);

    Reader findReaderByReaderCardNumberAndLibraryId(String readerCard,Long libraryId);

}
