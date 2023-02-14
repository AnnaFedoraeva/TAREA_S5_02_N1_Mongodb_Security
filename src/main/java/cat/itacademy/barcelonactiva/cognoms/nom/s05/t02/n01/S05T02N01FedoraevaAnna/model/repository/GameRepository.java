package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.domain.Game;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {

	Optional<Game> findAllByUsername(String username);

	void deleteByUsername(String username);

}
