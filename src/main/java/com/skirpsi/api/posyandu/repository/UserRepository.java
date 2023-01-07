package com.skirpsi.api.posyandu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skirpsi.api.posyandu.entity.UserPosyandu;

@Repository
public interface UserRepository extends JpaRepository<UserPosyandu, Integer>{

}
