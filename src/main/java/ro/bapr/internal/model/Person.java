package ro.bapr.internal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 31.10.2015.
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {

	private static final long serialVersionUID = -6825661847491980785L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
	private Long id;
	
	@Email
	@NotNull
	@Column(name = "email")
	private String email;
	
	@NotNull
	@Column(name = "name")
    private String name;
    
    @NotNull
	@Column(name = "password")
	private String password;
    
    public Person() {
    }

    public Person(String email, String name, String password) {
    	this.email = email;
		this.name = name;
		this.password = password;
    }

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", email=" + email +
                '}';
    }
    
}
