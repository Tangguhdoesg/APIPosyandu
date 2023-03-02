package com.skirpsi.api.posyandu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.Imunisasi;
import com.skirpsi.api.posyandu.entity.intfc.ImunisasiInterface;

public interface ImunisasiRepository extends JpaRepository<Imunisasi, Integer>{

	
	@Query("select i from Imunisasi i where i.idBalita = ?1")
	List<Imunisasi> findAllImunisasiByBalita(Balita x);
	
	@Query(value = "select idimunisasi, idbalita, namaimunisasi ,tanggalimunisasi ,catatanimunisasi  from imunisasi i where idbalita = ?1", nativeQuery = true)
	List<ImunisasiInterface> findByIdBalitaWithoutBalitaObj(Integer id);
	
	@Query(value = "select idimunisasi, idbalita, namaimunisasi ,tanggalimunisasi ,catatanimunisasi  from imunisasi i", nativeQuery = true)
	List<ImunisasiInterface> findAllWithourBalita();
	
	@Query(value = "select idimunisasi, idbalita, namaimunisasi ,tanggalimunisasi ,catatanimunisasi  from imunisasi i where tanggalimunisasiberikutnya > current_date + interval '1 day' ", nativeQuery = true)
	List<ImunisasiInterface> getDataForReminderImunisasi();
	
}
