package br.com.gasoutapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gasoutapp.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByLoginAndPassword(String login, String password);

	public User findByLogin(String login);

	public User findByPassword(String password);

	public List<User> findAllByEmail(String login);

	public User findByEmail(String email);

}