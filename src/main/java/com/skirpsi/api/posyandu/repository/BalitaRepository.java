package com.skirpsi.api.posyandu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.UserPosyandu;

public interface BalitaRepository extends JpaRepository<Balita, Integer>{

	@Query("select b from Balita b where b.user = ?1")
	List<Balita> findAllBalitaByUser(UserPosyandu s);
}
