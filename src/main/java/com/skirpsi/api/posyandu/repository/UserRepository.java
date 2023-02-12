package com.skirpsi.api.posyandu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.UserInterface;

@Repository
public interface UserRepository extends JpaRepository<UserPosyandu, Integer>{
	
	@Query(value ="select iduser ,namauser ,nikuser ,tanggallahir ,noteleponuser ,alamatuser ,usertype  from userposyandu u ", nativeQuery = true)
	List<UserInterface> findAllWithourPassword();
	
	@Query(value ="select iduser ,namauser ,nikuser ,tanggallahir ,noteleponuser ,alamatuser ,usertype  from userposyandu u where iduser=?1", nativeQuery = true)
	UserInterface findOneWithId(Integer id);

}
