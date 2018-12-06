/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.profsoft.lomboktest.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author filip
 */
@Entity
@Table(name = "CLIENT")
public class Client implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Long id;
	@NotNull @Getter @Setter
	@Size(min = 3, max = 30, message = " should be between 3 and 30 characters")
	private String firstName;
	@Future @Getter @Setter
	private Date createDate;

	public Client() {
	}

	;
	public Client(String firstName, Date createDate) {
		this.firstName = firstName;
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Id: " + this.id + " First name: " + this.firstName + " create date: " + this.createDate;
	}
}
