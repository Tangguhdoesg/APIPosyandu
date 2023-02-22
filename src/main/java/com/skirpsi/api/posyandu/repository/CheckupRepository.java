package com.skirpsi.api.posyandu.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skirpsi.api.posyandu.entity.CheckUp;
import com.skirpsi.api.posyandu.entity.intfc.CheckupInterface;

public interface CheckupRepository extends JpaRepository<CheckUp, Integer>{
	
	@Query(value="select idcheckup, tinggibadan ,beratbadan ,lingkarkepala ,lingkarlengan , tanggalcheckup ,tanggalcheckupberikutnya ,catatancheckup  from checkup c  where idbalita = ?1",nativeQuery = true)
	List<CheckupInterface> findByIdBalita(Integer x);
	
	@Query(value="select idcheckup, tinggibadan ,beratbadan ,lingkarkepala ,lingkarlengan , tanggalcheckup ,tanggalcheckupberikutnya ,catatancheckup  from checkup c ",nativeQuery = true)
	List<CheckupInterface> findAllWithoutBalita();
	
	@Query(value="select idcheckup, idbalita , tinggibadan ,beratbadan ,lingkarkepala ,lingkarlengan , tanggalcheckup ,tanggalcheckupberikutnya ,catatancheckup from checkup c  where tanggalcheckupberikutnya > current_date + interval '1 day' ",nativeQuery = true)
	List<CheckupInterface> getDataforReminder();
}
