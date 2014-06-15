package com.whatnow.serverside.login;

import com.eaio.uuid.UUID;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usersession")
public class UserSession implements Serializable {

	private int id;
	private String name;
        private UUID token;

	public UserSession() {
		// this form used by Hibernate
	}

	public UserSession(int id, String name, UUID token) {
		this.id = id;
		this.name = name;
                this.token = token;
	}

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
        
        @Column(name = "token")
        public UUID getToken() {
		return token;
	}

	public void setToken(UUID token) {
		this.token = token;
	}
}
