package com.skirpsi.api.posyandu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skirpsi.api.posyandu.entity.Kegiatan;
import com.skirpsi.api.posyandu.entity.intfc.KegiatanInterface;

public interface KegiatanRepository extends JpaRepository<Kegiatan, Integer>{
	
	@Query(value="select idkegiatan , namakegiatan ,tanggalkegiatan ,lokasikegiatan ,posterkegiatan, namaposter  from kegiatan k where iduser =?1", nativeQuery = true)
	List<KegiatanInterface> findByIdUser(Integer id);
	
	@Query(value="select idkegiatan , namakegiatan ,tanggalkegiatan ,lokasikegiatan ,posterkegiatan, namaposter  from kegiatan k", nativeQuery = true)
	List<KegiatanInterface> findAllWihoutUser();

}
