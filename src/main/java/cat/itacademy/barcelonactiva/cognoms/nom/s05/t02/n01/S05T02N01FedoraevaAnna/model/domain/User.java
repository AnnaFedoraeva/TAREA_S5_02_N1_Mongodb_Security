package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.domain;

import java.io.Serializable;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "users")
public class User implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@MongoId(FieldType.OBJECT_ID)
	private String id;

	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@DBRef
	private Set<Role> roles = new HashSet<>();
	@DBRef
	private ArrayList <Game> games;

	private LocalDateTime date;

	public void setDate(LocalDateTime date) {
		this.date = LocalDateTime.now();
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;

	}
}
