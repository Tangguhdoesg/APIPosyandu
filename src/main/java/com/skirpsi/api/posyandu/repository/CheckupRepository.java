package com.skirpsi.api.posyandu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.CheckUp;

public interface CheckupRepository extends JpaRepository<CheckUp, Integer>{
	
	@Query("select c from CheckUp c where c.balita = ?1")
	List<CheckUp> findAllCheckupByBalita(Balita x);

}
