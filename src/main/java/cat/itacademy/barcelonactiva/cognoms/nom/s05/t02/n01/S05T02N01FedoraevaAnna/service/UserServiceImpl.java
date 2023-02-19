package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.exception.UserNotFoundException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.domain.ERole;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.domain.Role;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.domain.User;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.repository.RoleRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.repository.UserRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.request.SignupRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.request.UpdateUsername;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.response.MessageResponse;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.security.jwt.JwtUtils;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.util.AvSuccesRate;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	GameRepository gameRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	public ResponseEntity<?> createUser(SignupRequest signUpRequest) {
		
		if (signUpRequest.getUsername() == null)
			signUpRequest.setUsername("ANONYMOUS");

		if (userRepository.existsByUsername(signUpRequest.getUsername())
				&& (!signUpRequest.getUsername().equalsIgnoreCase("ANONYMOUS"))) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

//		User user = new User();
//		if (!signUpRequest.getUsername().equalsIgnoreCase("ANONYMOUS")) {

		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
//			user.setUsername(signUpRequest.getUsername());
//			user.setEmail(signUpRequest.getEmail());
//			user.setPassword(encoder.encode(signUpRequest.getPassword()));
//		}

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();
		ArrayList<Game> games = new ArrayList<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER);
				//.orElseThrow(() -> new RuntimeException("Role is not found."));
			roles.add(userRole);
		} else {
			//try {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin": 
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
							//.orElseThrow(() -> new RuntimeException("Role is not found."));
					roles.add(adminRole);
					break;
				case "user": 
					Role userRole = roleRepository.findByName(ERole.ROLE_USER);
						//.orElseThrow(() -> new RuntimeException("Role is not found."));
					roles.add(userRole);
				}
				
				});
		}
			
	
			if (roles.isEmpty()){
				return ResponseEntity
						.ok(new MessageResponse("Role is not found"));
				
			} else {
			user.setRoles(roles);
			user.setDate(LocalDateTime.now());
			user.setGames(games);
			userRepository.save(user);
			
			return ResponseEntity
					.ok(new MessageResponse("The user " + user.getUsername() + " has been successfully created."));
			}
//			} catch (Exception e) {
//				return new ResponseEntity<>("Role is not found", HttpStatus.INTERNAL_SERVER_ERROR);
//			}
			
		
	}

	@Override
	public ResponseEntity<?> updatePlayerName(UpdateUsername update, String id) {
//		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
//				.getPrincipal();
//		User userFound = findById(userDetails.getId());
		User userFound = findById(id);
		String username = update.getUsername();
		if (userRepository.existsByUsername(username) && (!username.equalsIgnoreCase("ANONYMOUS"))) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));

		}
		userFound.setUsername(username);
		userRepository.save(userFound);

		return new ResponseEntity<>("Username has been successfully updated.", HttpStatus.OK);
	}

	@Override
	public User findById(String id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	@Override
	public ResponseEntity<?> rollDice(String id) {
		User user = findById(id);
		int dice1 = randomNumbers();
		int dice2 = randomNumbers();
		Game game = new Game(user.getUsername(), dice1, dice2, "0");
//		game.setDice1(dice1);
//		game.setDice2(dice2);
//		game.setUseranem(username);
		int sumDices = dice1 + dice2;

		if (sumDices == 7) {
			game.setResult("win");
		} else {
			game.setResult("lose");
		}
		gameRepository.save(game);
		user.getGames().add(game);
		userRepository.save(user);

		return ResponseEntity.status(HttpStatus.OK).body(game);
		// new ResponseEntity<>(game, HttpStatus.OK);
	}

	// generating random numbers via the ThreadLocalRandom class
	public int randomNumbers() {

		int randomNum = ThreadLocalRandom.current().nextInt(1, 6);

		return randomNum;
	}

	public ResponseEntity<?> delete(String id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		if (user.getGames().isEmpty()) {
			return new ResponseEntity<>("Player " + user.getUsername() + " has not played any game yet.",
					HttpStatus.OK);
		}
//		List<Game> games = gameRepository.findAllByUser(user);
//		if (games.isEmpty()) {
//			return new ResponseEntity<>("Player " + user.getUsername() + " has not played any game yet.", HttpStatus.OK);
//		}
//		
//		user.getGames().clear();
		ArrayList<Game> games = new ArrayList<>();
		user.setGames(games);
		gameRepository.deleteByUsername(user.getUsername());
//		user.getGames().removeAll(games);
		userRepository.save(user);
		// games.clear();

		return new ResponseEntity<>("All games of user: " + user.getUsername() + " has been deleted", HttpStatus.OK);

	}

	public ResponseEntity<?> getGamesByUser(String id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		List<Game> gamesDTO = new ArrayList<>();
		List<Game> games = user.getGames();
		if (user.getGames().isEmpty()) {
			return new ResponseEntity<>(
					"Player " + user.getUsername() + " has not played any game yet or the games were deleted.",
					HttpStatus.OK);
		}

		for (Game diceEntity : games) {
			gamesDTO.add(diceEntity);
		}
		return ResponseEntity.status(HttpStatus.OK).body(gamesDTO);
	}

//	public ResponseEntity<?> getGamesByUser(String id) {
//		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
//
//		Optional<Game> games = gameRepository.findAllByUsername(user.getUsername());
//
//
//		List<Game> gamesDTO = games.stream().collect(Collectors.toList());
//
//		List<User> allUsers = userRepository.findAll();
//		List<UserDTO> list = allUsers.stream().filter(user1 -> user1.getGames().size() > 0).map(temp -> {
//			UserDTO obj = new UserDTO();
//			obj.setId(temp.getId());
//			obj.setUsername(temp.getUsername());
//			obj.setGames(gamesDTO);
//			return obj;
//		}).collect(Collectors.toList());
//
//		return ResponseEntity.status(HttpStatus.OK).body(gamesDTO);
//
//	}

	public String getPlayerAvSucRate(User user) {
		String result = "The user has not played yet.";
		int numberGames = 0;
		// List<Game> games = new ArrayList<Game>();
		if (user.getGames() != null && user.getGames().size() > 0) {
			numberGames = user.getGames().size();
			// games = user.getGames();
			float gamesWon = user.getGames().stream().filter(g -> g.getDice1() + g.getDice2() == 7).count();
			float succesRate = (gamesWon / numberGames) * 100;
			result = String.valueOf(succesRate);
		}

		return result;

	}

	// java8:
	public List<AvSuccesRate> listRate() {
		List<User> allUsers = userRepository.findAll();
		List<AvSuccesRate> listRate = allUsers.stream().filter(user -> user.getGames().size() > 0).map(temp -> {
			AvSuccesRate obj = new AvSuccesRate();
			obj.setId(temp.getId());
			obj.setUsername(temp.getUsername());
			obj.setRate(getPlayerAvSucRate(temp));
			return obj;
		}).collect(Collectors.toList());
		return listRate;
	}

	public ResponseEntity<?> getAverageRanking() {
		List<User> users = userRepository.findAll().stream().toList();
		String message;

		if (users.isEmpty()) {
			message = "Zero users have played.";
		} else {
			double totalSum = users.stream().map(this::getPlayerAvSucRate)
					.filter(rate -> !rate.equals("The user has not played yet.")).mapToDouble(Double::parseDouble)
					.reduce(0d, Double::sum);
			long count = users.stream().filter(user -> user.getGames().size() > 0).count();
			if (count == 0) {
				message = "There are no games recorded yet";
			} else {
				message = String.valueOf(totalSum / count);

			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	public ResponseEntity<?> getLoser() {

		List<AvSuccesRate> users = listRate().stream().sorted((Comparator.comparing(AvSuccesRate::getRate)))
				.collect(Collectors.toList());

		List<AvSuccesRate> loser = new ArrayList<>();
		boolean isLow = true;
		while (isLow) {
			for (AvSuccesRate u : users) {
				if (users.get(0).getRate().equals(u.getRate())) {
					loser.add(u);
				}
				isLow = false;
			}

		}
		// java8
		// List<AvSuccesRate> loser = users.stream().filter(u1 ->
		// u1.getRate().equals(users.get(0).getRate())).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(loser);
	}

	public ResponseEntity<?> getWinner() {

		List<AvSuccesRate> users = listRate().stream().sorted((Comparator.comparing(AvSuccesRate::getRate).reversed()))
				.collect(Collectors.toList());

		List<AvSuccesRate> winner = new ArrayList<>();
		boolean isLow = true;
		while (isLow) {
			for (AvSuccesRate u : users) {
				if (users.get(0).getRate().equals(u.getRate())) {
					winner.add(u);
				}
				isLow = false;
			}
		}

		// java8
		// List<AvSuccesRate> winner = users.stream().filter(u1 ->
		// u1.getRate().equals(users.get(0).getRate())).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(winner);
	}
}
