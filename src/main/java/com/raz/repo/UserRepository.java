package com.raz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.raz.entity.Client;
import com.raz.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmailAndPassword(String email, String password);

	@Query("FROM User as u WHERE role =:role")
	List<User> findAllByRole(int role);

	@Query("FROM User as u WHERE u.client =:client")
	User getByClient(Client client);


}
