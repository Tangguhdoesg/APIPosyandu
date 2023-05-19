package com.skirpsi.api.posyandu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skirpsi.api.posyandu.entity.Kegiatan;
import com.skirpsi.api.posyandu.entity.intfc.KegiatanInterface;

public interface KegiatanRepository extends JpaRepository<Kegiatan, Integer>{
	
	@Query(value="select idkegiatan , namakegiatan ,tanggalkegiatan ,lokasikegiatan ,posterkegiatan, namaposterkegiatan  from kegiatan k where idkegiatan =?1", nativeQuery = true)
	KegiatanInterface findByIdKegiatan(Integer id);
	
	@Query(value="select idkegiatan , iduser,namakegiatan ,tanggalkegiatan ,lokasikegiatan ,posterkegiatan, namaposterkegiatan  from kegiatan k", nativeQuery = true)
	List<KegiatanInterface> findAllWihoutUser();
	
	@Query(value="select idkegiatan , iduser,namakegiatan ,tanggalkegiatan ,lokasikegiatan ,posterkegiatan, namaposterkegiatan  from kegiatan k where iduser = ?1", nativeQuery = true)
	List<KegiatanInterface> findAllByUserId(Integer id);
	
	@Query(value="select idkegiatan , namakegiatan ,tanggalkegiatan ,lokasikegiatan ,posterkegiatan, namaposterkegiatan,iduser  from kegiatan k where tanggalkegiatan > current_date + interval '1 day' limit 1", nativeQuery = true)
	List<KegiatanInterface> getDataforReminderKegiatan();
	
	

}
