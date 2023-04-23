package com.skirpsi.api.posyandu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skirpsi.api.posyandu.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	
	Optional<User> findByIduser(Integer id);

	Boolean existsByUsername(String username);
}
