package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.dto;

import java.util.List;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.domain.Game;
import lombok.Data;

@Data
public class UserDTO {
	
	List <Game> games;
	String id;
	String username;

	

}
