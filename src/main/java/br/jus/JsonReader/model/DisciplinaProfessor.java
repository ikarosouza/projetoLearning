package br.jus.JsonReader.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DisciplinaProfessor {
	@Id
	@GeneratedValue
	private Long id;
	private String nomeDisciplina;
	
	private int qtdMinistradas;
	
	private double score;
	
	@ManyToOne
	private Professor professor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public int getQtdMinistradas() {
		return qtdMinistradas;
	}

	public void setQtdMinistradas(int qtdMinistradas) {
		this.qtdMinistradas = qtdMinistradas;
	}
}
