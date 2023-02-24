package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.domain.User;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.request.SignupRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.request.UpdateUsername;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.util.AvSuccesRate;

public interface UserService {

	public ResponseEntity<?> createUser(SignupRequest signUpRequest);

	public User findById(String id);

	public ResponseEntity<?> rollDice(String id);

	public ResponseEntity<?> delete(String id);

	public ResponseEntity<?> getGamesByUser(String id);

	public List<AvSuccesRate> listRate();

	public ResponseEntity<?> updatePlayerName(UpdateUsername update, String id);

	public ResponseEntity<?> getWinner();

	public ResponseEntity<?> getLoser();

	public ResponseEntity<?> getAverageRanking();

}
