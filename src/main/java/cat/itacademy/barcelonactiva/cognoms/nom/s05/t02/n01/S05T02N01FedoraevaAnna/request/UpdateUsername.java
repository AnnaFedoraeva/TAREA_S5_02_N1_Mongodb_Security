package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data

public class UpdateUsername {
	
	@NotBlank
	private String username;

}
