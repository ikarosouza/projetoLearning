package br.jus.JsonReader.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity
public class Aluno {
	@Id
	@GeneratedValue
	private Long id;
	
	private String codigoAluno;	
	
	@OneToMany(mappedBy="aluno")
	@MapKey(name="anoPeriodo")
	private Map<String, SemestreAluno> semestresAluno;
	
	private double scoreTotal;

	public Aluno() {
		this.semestresAluno = new HashMap<String, SemestreAluno>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoAluno() {
		return codigoAluno;
	}

	public void setCodigoAluno(String codigoAluno) {
		this.codigoAluno = codigoAluno;
	}

	public Map<String, SemestreAluno> getSemestresAluno() {
		return semestresAluno;
	}

	public void setSemestresAluno(Map<String, SemestreAluno> semestresAluno) {
		this.semestresAluno = semestresAluno;
	}

	public double getScoreTotal() {
		return scoreTotal;
	}

	public void setScoreTotal(double scoreTotal) {
		this.scoreTotal = scoreTotal;
	}
}
