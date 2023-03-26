package com.skirpsi.api.posyandu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.Imunisasi;
import com.skirpsi.api.posyandu.entity.intfc.ImunisasiInterface;

public interface ImunisasiRepository extends JpaRepository<Imunisasi, Integer>{

	
	@Query(value = "select * from imunisasi i where idBalita = ?1",nativeQuery =true)
	List<Imunisasi> findAllImunisasiByIdBalita(Integer id);
	
	@Query(value = "select * from imunisasi i "
			+ "join balita b on b.idbalita = i.idbalita "
			+ "join userposyandu u ON u.iduser = b.idorangtua "
			+ "where b.idorangtua  = ?1",nativeQuery =true)
	List<Imunisasi> findAllImunisasiByIdOrtu(Integer id);
	
	@Query(value = "select idimunisasi, idbalita, namaimunisasi ,tanggalimunisasi ,catatanimunisasi  from imunisasi i where idbalita = ?1", nativeQuery = true)
	List<ImunisasiInterface> findByIdBalitaWithoutBalitaObj(Integer id);
	
	@Query(value = "select idimunisasi, idbalita, namaimunisasi ,tanggalimunisasi ,catatanimunisasi  from imunisasi i", nativeQuery = true)
	List<ImunisasiInterface> findAllWithourBalita();
	
	@Query(value = "select idimunisasi, idbalita, namaimunisasi ,tanggalimunisasi ,catatanimunisasi  from imunisasi i where tanggalimunisasiberikutnya > current_date + interval '1 day' ", nativeQuery = true)
	List<ImunisasiInterface> getDataForReminderImunisasi();
	
}
