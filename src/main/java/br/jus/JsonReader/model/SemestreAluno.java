package br.jus.JsonReader.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SemestreAluno {
	@Id
	@GeneratedValue
	private Long id;
	
	private String anoPeriodo;
	
	private double score = 0.0;
	
	private int qtdApr = 0;	
	private int qtdRep = 0;
	private int qtdRepFalt = 0;
	private int qtdRepMedFalt = 0;
	private int qtdTranc = 0;
	private int qtdTurmas = 0;
	
	@ManyToOne
	private Aluno aluno;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAnoPeriodo() {
		return anoPeriodo;
	}
	public void setAnoPeriodo(String anoPeriodo) {
		this.anoPeriodo = anoPeriodo;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getQtdApr() {
		return qtdApr;
	}
	public void setQtdApr(int qtdApr) {
		this.qtdApr = qtdApr;
	}
	public int getQtdRep() {
		return qtdRep;
	}
	public void setQtdRep(int qtdRep) {
		this.qtdRep = qtdRep;
	}
	public int getQtdRepFalt() {
		return qtdRepFalt;
	}
	public void setQtdRepFalt(int qtdRepFalt) {
		this.qtdRepFalt = qtdRepFalt;
	}
	public int getQtdRepMedFalt() {
		return qtdRepMedFalt;
	}
	public void setQtdRepMedFalt(int qtdRepMedFalt) {
		this.qtdRepMedFalt = qtdRepMedFalt;
	}
	public int getQtdTranc() {
		return qtdTranc;
	}
	public void setQtdTranc(int qtdTranc) {
		this.qtdTranc = qtdTranc;
	}
	public int getQtdTurmas() {
		return qtdTurmas;
	}
	public void setQtdTurmas(int qtdTurmas) {
		this.qtdTurmas = qtdTurmas;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}	
}
