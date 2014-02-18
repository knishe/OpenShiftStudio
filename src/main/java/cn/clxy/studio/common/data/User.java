package cn.clxy.studio.common.data;

import java.io.Serializable;

/**
 * ユーザー。
 * @author clxy
 */
public class User implements Serializable {

	private Integer id;
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;

	public User() {
	}

	public User(Integer id, String firstName) {
		this.id = id;
		this.firstName = firstName;
	}

	public boolean isLogined() {
		// check userId because of gae.
		return id != null || userId != null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private static final long serialVersionUID = 1L;
}
