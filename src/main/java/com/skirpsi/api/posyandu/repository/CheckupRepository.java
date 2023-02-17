package com.skirpsi.api.posyandu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.CheckUp;
import com.skirpsi.api.posyandu.entity.intfc.CheckupInterface;

public interface CheckupRepository extends JpaRepository<CheckUp, Integer>{
	
	@Query(value="select idcheckup, tinggibadan ,beratbadan ,lingkarkepala ,lingkarlengan , tanggalcheckup ,tanggalcheckupberikutnya ,catatancheckup  from checkup c  where idbalita = ?1",nativeQuery = true)
	List<CheckupInterface> findByIdBalita(Integer x);
	
	@Query(value="select idcheckup, tinggibadan ,beratbadan ,lingkarkepala ,lingkarlengan , tanggalcheckup ,tanggalcheckupberikutnya ,catatancheckup  from checkup c ",nativeQuery = true)
	List<CheckupInterface> findAllWithoutBalita();
}
