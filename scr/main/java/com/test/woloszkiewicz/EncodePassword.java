package com.test.woloszkiewicz;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodePassword {

	public static void main(String[] args) {
		String password="user";
		BCryptPasswordEncoder passwordencoder = new BCryptPasswordEncoder();
		String hashpassword = passwordencoder.encode(password);
		System.out.println(hashpassword);
		
		password="admin";
		hashpassword= passwordencoder.encode(password);
		System.out.println(hashpassword);

	}

}
