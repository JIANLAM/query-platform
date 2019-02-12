package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * created by linzj on 2018/12/26
 **/
public interface OrganizationRepository extends CrudRepository<Organization,Long> {
    @Override
    Organization findOne(Long aLong);


    @Query("SELECT o.id FROM Organization o where o.code = :code")
    Long findOrganizationByCode(@Param("code") String code);
}
