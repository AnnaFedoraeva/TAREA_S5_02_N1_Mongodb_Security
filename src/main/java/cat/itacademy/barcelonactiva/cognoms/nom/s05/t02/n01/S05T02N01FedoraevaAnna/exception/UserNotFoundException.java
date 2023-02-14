package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.exception;

import java.text.MessageFormat;

public class UserNotFoundException  extends RuntimeException {
	
	private static final long serialVersionUID = 1L;



	public UserNotFoundException(String id) {
		super(MessageFormat.format("Could not find user with id: " + id, id));
	}

}
