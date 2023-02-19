package com.skirpsi.api.posyandu.security.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.skirpsi.api.posyandu.entity.User;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.repository.UserPosyanduRepository;
import com.skirpsi.api.posyandu.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserPosyanduRepository userPosRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
//	@Override
//	@Transactional
//	public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException{
//		UserPosyandu x = userPosRepo.findByNoTeleponUser(phone).orElseThrow(() -> new UsernameNotFoundException("User Not found with phone : " + phone));
//		
//		System.out.println(x.getNoTeleponUser());
//		System.out.println(phone);
//		System.out.println(x.getPasswordUser());
//		
//		return UserDetailsImpl.buildUserPosyandu(x);
//	}
}
