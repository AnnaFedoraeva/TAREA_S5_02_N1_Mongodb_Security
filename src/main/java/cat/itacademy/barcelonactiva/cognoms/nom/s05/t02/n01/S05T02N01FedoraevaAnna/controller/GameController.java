package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.request.UpdateUsername;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.service.UserService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.util.AvSuccesRate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/players")
public class GameController {

	@Autowired
	UserService userService;

	@PutMapping("/{id}")
	@PreAuthorize("#id == principal.id or hasRole('ADMIN')")
	public ResponseEntity<?> updatePlayerName(@RequestBody UpdateUsername update, @PathVariable String id) {
		return userService.updatePlayerName(update, id);
	}

	@PostMapping("/{id}/games")
	@PreAuthorize("#id == authentication.principal.id")
	public ResponseEntity<?> playGame(@PathVariable String id) {

		return userService.rollDice(id);
	}

	@DeleteMapping("/{id}/games")
	@PreAuthorize("#id == principal.id or hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable String id) {
		return userService.delete(id);
	}

	@GetMapping("/{id}/games")
	@PreAuthorize("#id == principal.id or hasRole('ADMIN')")
	public ResponseEntity<?> getGamesByUser(@PathVariable String id) {
		return userService.getGamesByUser(id);

	}

	// GET /players/: returns the list of all players in the system with their
	// average success rate.
	@GetMapping("/rate")
	public List<AvSuccesRate> getUserByAvSucRate() {
		return userService.listRate();
	}

	// GET /players/ranking: returns the average ranking of all players in the
	// system. That is, the average percentage of achievements.
	@GetMapping("/ranking")
	public ResponseEntity<?> getRanking() {
		return userService.getAverageRanking();
	}

	// GET /players/ranking/loser: returns the player with the worst success rate.
	@GetMapping("/loser")
	public ResponseEntity<?> getLoser() {
		return userService.getLoser();

	}

	// GET /players/ranking/winner: returns the player with the best success rate.
	@GetMapping("/winner")
	public ResponseEntity<?> getWinner() {
		return userService.getWinner();

	}

}
