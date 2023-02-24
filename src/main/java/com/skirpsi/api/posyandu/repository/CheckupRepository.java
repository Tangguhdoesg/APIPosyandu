package com.skirpsi.api.posyandu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skirpsi.api.posyandu.entity.CheckUp;
import com.skirpsi.api.posyandu.entity.intfc.CheckupInterface;
import com.skirpsi.api.posyandu.entity.intfc.ReportInterface;

public interface CheckupRepository extends JpaRepository<CheckUp, Integer>{
	
	@Query(value="select idcheckup, tinggibadan ,beratbadan ,lingkarkepala ,lingkarlengan , tanggalcheckup ,tanggalcheckupberikutnya ,catatancheckup  from checkup c  where idbalita = ?1",nativeQuery = true)
	List<CheckupInterface> findByIdBalita(Integer x);
	
	@Query(value="select idcheckup, tinggibadan ,beratbadan ,lingkarkepala ,lingkarlengan , tanggalcheckup ,tanggalcheckupberikutnya ,catatancheckup  from checkup c ",nativeQuery = true)
	List<CheckupInterface> findAllWithoutBalita();
	
	@Query(value="select idcheckup, idbalita , tinggibadan ,beratbadan ,lingkarkepala ,lingkarlengan , tanggalcheckup ,tanggalcheckupberikutnya ,catatancheckup from checkup c  where tanggalcheckupberikutnya > current_date + interval '1 day' ",nativeQuery = true)
	List<CheckupInterface> getDataforReminder();
	
	@Query(value="select b.idbalita as idBalita , namabalita as namabalita, nikbalita as nikbalita, beratsaatlahir as beratsaatlahir, tinggisaatlahir as tinggisaatlahir, u.alamatuser as alamatuser , u.namauser as namauser, c.tanggalcheckup as tanggalcheckup, c.beratbadan as beratbadan, c.tinggibadan as tinggibadan  from balita b "
			+ "join userposyandu u on u.iduser =b.idorangtua "
			+ "join checkup c on c.idcheckup = b.idbalita "
			+ "where c.tanggalcheckup >= TO_TIMESTAMP(?1,'YYYY-MM-DD') and c.tanggalcheckup <= TO_TIMESTAMP(?2,'YYYY-MM-DD') ",nativeQuery = true)
	List<ReportInterface> getDataForReporting(String from, String to);
}
