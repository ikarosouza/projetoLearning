package br.jus.JsonReader.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity
public class Professor {
	@Id
	@GeneratedValue
	private Long id;
	
	private String nome;
	private Long idInterno;
	
	@OneToMany(mappedBy="professor")
	@MapKey(name="nomeDisciplina")
	private Map<String, DisciplinaProfessor> disciplinas;
	
	
	
	public Professor() {
		this.disciplinas = new HashMap<String, DisciplinaProfessor>();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getIdInterno() {
		return idInterno;
	}
	public void setIdInterno(Long idInterno) {
		this.idInterno = idInterno;
	}
	public Map<String, DisciplinaProfessor> getDisciplinas() {
		return disciplinas;
	}
	public void setDisciplinas(Map<String, DisciplinaProfessor> disciplinas) {
		this.disciplinas = disciplinas;
	}
}
