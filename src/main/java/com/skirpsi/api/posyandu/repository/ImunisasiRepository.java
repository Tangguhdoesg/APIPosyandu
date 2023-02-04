package com.skirpsi.api.posyandu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.Imunisasi;

public interface ImunisasiRepository extends JpaRepository<Imunisasi, Integer>{

	
	@Query("select i from Imunisasi i where i.balita = ?1")
	List<Imunisasi> findAllImunisasiByBalita(Balita x);
}
