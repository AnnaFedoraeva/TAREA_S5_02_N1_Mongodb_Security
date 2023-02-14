package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

	boolean existsByUsername(String username);
	
	Optional<User> findByUsername(String username);

	boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);

}
