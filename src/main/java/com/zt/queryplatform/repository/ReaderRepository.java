package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Reader;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * created by linzj on 2018/12/21
 **/
public interface ReaderRepository extends CrudRepository<Reader,Long> {

    @Override
    Reader findOne(Long aLong);

    Reader findReaderByPeopleIdAndLibraryId(Long peopleId, Long libraryId);

    Reader findReaderByReaderCardNumberAndLibraryId(String readerCard, Long libraryId);


    @Query(value = "select  o.peopleId as peopleId from Reader o where o.libraryId =:libraryId")
    List<Long> findAllByLibraryId(@Param("libraryId") Long libraryId);

}
