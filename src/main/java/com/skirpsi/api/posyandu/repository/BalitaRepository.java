package com.skirpsi.api.posyandu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.BalitaInterface;

public interface BalitaRepository extends JpaRepository<Balita, Integer>{

	@Query("select b from Balita b where b.idUser = ?1")
	List<Balita> findAllBalitaByUser(UserPosyandu s);
	
//	@Query("")
//	List<BalitaInterface> findByUser(UserPosyandu user);
	
	@Query(value = "select idbalita,idorangtua,namabalita,nikbalita,tempatlahirbalita,tanggallahirbalita, jeniskelaminbalita, beratsaatlahirbalita, tinggisaatlahirbalita  from balita where idorangtua  = ?1", nativeQuery =  true)
	List<BalitaInterface> findByIdWithIdUser(Integer id);
	
	@Query(value = "select idbalita,idorangtua,namabalita,nikbalita,tempatlahirbalita,tanggallahirbalita, jeniskelaminbalita, beratsaatlahirbalita, tinggisaatlahirbalita  from balita ", nativeQuery =  true)
	List<BalitaInterface> findAllWithoutUser();
	
	@Query(value = "select idbalita,idorangtua,namabalita,nikbalita,tempatlahirbalita,tanggallahirbalita, jeniskelaminbalita, beratsaatlahirbalita, tinggisaatlahirbalita  from balita where idbalita = ?1", nativeQuery =  true)
	BalitaInterface findByIdBalitaInterface(Integer id);
	
	Optional<Balita> findByNikBalita(String nik);
}
