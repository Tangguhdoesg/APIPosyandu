package com.skirpsi.api.posyandu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.UserInterface;

@Repository
public interface UserPosyanduRepository extends JpaRepository<UserPosyandu, Integer>{
	
	@Query(value ="select iduser as IdUser ,namauser as NamaUser ,nikuser as NIKUser,tanggallahir as TanggalLahirUser,noteleponuser as NoTeleponUser,alamatuser as AlamatUser,tipeuser as tipeuser  from userposyandu u ", nativeQuery = true)
	List<UserInterface> findAllWithourPassword();
	
	@Query(value ="select iduser as IdUser ,namauser as NamaUser ,nikuser as NIKUser,tanggallahir as TanggalLahirUser,noteleponuser as NoTeleponUser,alamatuser as AlamatUser,tipeuser as tipeuser from userposyandu u where iduser=?1", nativeQuery = true)
	UserInterface findOneWithId(Integer id);
	
	@Query(value ="select iduser as IdUser ,namauser as NamaUser ,nikuser as NIKUser,tanggallahir as TanggalLahirUser,noteleponuser as NoTeleponUser,alamatuser as AlamatUser,tipeuser as tipeuser  from userposyandu u where tipeuser = 2", nativeQuery = true)
	List<UserInterface> getAllOrangTua();
	
	Boolean existsByNoTeleponUser(String notelp);
	
	Optional<UserPosyandu> findByNoTeleponUser(String noTelp);
	
	Optional<UserPosyandu> findByNikUser(String nik);

}
