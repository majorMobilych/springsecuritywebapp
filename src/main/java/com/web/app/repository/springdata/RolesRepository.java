package com.web.app.repository.springdata;

import com.web.app.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, String> {

    RolesEntity findByRole(String user);
}
