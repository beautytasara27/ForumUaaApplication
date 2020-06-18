package com.binus.project.forumuaa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="roles")
@Setter
@Getter
public class Role implements Serializable
{

	@Id
	@SequenceGenerator(name = "role_id_generator", sequenceName = "role_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "role_id_generator")
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String name;

	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private List<UserAccount> userAccounts;

	@JsonProperty("created_at")
	@Column(insertable = true, updatable = false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@JsonProperty("updated_at")
	@Column(insertable = false, updatable = true)
	protected LocalDateTime updatedAt = LocalDateTime.now();

	public Role() {
	}

	public Role(String name) {
		this.name = name;
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

	public List<UserAccount> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(List<UserAccount> userAccounts) {
		this.userAccounts = userAccounts;
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
}
