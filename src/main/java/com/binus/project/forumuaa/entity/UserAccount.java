package com.binus.project.forumuaa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user_account")
@TableGenerator(name = "user_account_id_generator",
		table = "id_generator",
		pkColumnName = "id_name",
		pkColumnValue = "user_account",
		valueColumnName = "id_value")
public class UserAccount implements Serializable {


	@Id
	@GeneratedValue(generator = "user_account_id_generator")
	private Long id;

	@Column(nullable=false)
	private String name;

	@Column(nullable=false, unique=true)
	private String email;

	@Column(name ="password",nullable=false)
	private String password;

	@Column(name ="profile_picture")
	private String profilePicture;

	@Column(insertable = true, updatable = false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@Column(insertable = false, updatable = true)
	protected LocalDateTime updatedAt = LocalDateTime.now();


	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();


	public UserAccount() {
	}

	@PrePersist
	public void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
