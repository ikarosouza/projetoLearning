package br.jus.JsonReader.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Turma {
	@Id
	@GeneratedValue
	private long id;
	
	private long idTurma;
	@Column(nullable = true)
	private String nomeComponente;
	@Column(nullable = true)
	private int ano;
	@Column(nullable = true)
	private int periodo;
	@Column(nullable = true)
	private String codigo;
	@Column(nullable = true)
	private String situacao;
	@Column(nullable = true)
	private String tipo;
	@Column(nullable = true)
	private String nivel;
	@Column(nullable = true)
	private int capacidade;
	
	private int qtdApr = 0;	
	private int qtdRep = 0;
	private int qtdRepFalt = 0;
	private int qtdRepMedFalt = 0;
	private int qtdTranc = 0;
	private int qtdAlunos = 0;
	
	@ManyToOne
	private Professor professor;
	
	private String nomeProfessor;
	
	private double score;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(long idTurma) {
		this.idTurma = idTurma;
	}
	public String getNomeComponente() {
		return nomeComponente;
	}
	public void setNomeComponente(String nomeComponente) {
		this.nomeComponente = nomeComponente;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public int getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public String getNomeProfessor() {
		return nomeProfessor;
	}
	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
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
	public int getQtdAlunos() {
		return qtdAlunos;
	}
	public void setQtdAlunos(int qtdAlunos) {
		this.qtdAlunos = qtdAlunos;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
}
