package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.domain;




import java.io.Serializable;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
@Document(collection = "games")

public class Game implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@MongoId(FieldType.OBJECT_ID)
	private String id;

	private String username;

	private int dice1;

	private int dice2;

	private String result;

	public Game(String username, int dice1, int dice2, String result) {
		super();
		this.username = username;
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.result = result;
	}

//	@Override
//	public String toString() {
//		return "Game [username=" + username + ", dice1=" + dice1 + ", dice2=" + dice2 + ", result=" + result + "]";
//	}
//	
	

}
