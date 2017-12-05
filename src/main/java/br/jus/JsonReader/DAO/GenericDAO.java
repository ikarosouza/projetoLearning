package br.jus.JsonReader.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.jus.JsonReader.model.Aluno;
import br.jus.JsonReader.model.DisciplinaProfessor;
import br.jus.JsonReader.model.Professor;

public class GenericDAO {
	protected EntityManager em;

	public void save(Object object) {
		em = Connection.getInstance();
		em.getTransaction().begin();
		em.persist(object);
		em.getTransaction().commit();
	}
	
	public void update(Object object) {
		em = Connection.getInstance();
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
	}

	public void remove(Object object) {
		em = Connection.getInstance();
		em.getTransaction().begin();
		em.remove(object);
		em.getTransaction().commit();
	}
	
	public boolean turmaExiste(Long id) {
		em = Connection.getInstance();
		String sql = "select t from Turma t where t.idTurma = :id ";
		Query query = em.createQuery(sql);
		query.setParameter("id", id);
		return query.getResultList().size() > 0 ? true : false;
	}
	
	public boolean profExiste(Long id) {
		em = Connection.getInstance();
		String sql = "select p from Professor p where p.idInterno = :id ";
		Query query = em.createQuery(sql);
		query.setParameter("id", id);
		return query.getResultList().size() > 0 ? true : false;
	}
	
	public boolean AlunoExiste(String cod) {
		em = Connection.getInstance();
		String sql = "select a from Aluno a where a.codigoAluno = :cod ";
		Query query = em.createQuery(sql);
		query.setParameter("cod", cod);
		return query.getResultList().size() > 0 ? true : false;
	}
	
	public Aluno getAlunoByCod(String cod) {
		em = Connection.getInstance();
		String sql = "select a from Aluno a where a.codigoAluno = :cod ";
		Query query = em.createQuery(sql);
		query.setParameter("cod", cod);
		return (Aluno) query.getSingleResult();
	}
	
	public Professor getProfById(Long id) {
		em = Connection.getInstance();
		String sql = "select p from Professor p where p.idInterno = :id ";
		Query query = em.createQuery(sql);
		query.setParameter("id", id);
		return (Professor) query.getSingleResult();
	}
	
	public List<Aluno> getAlunos(){
		em = Connection.getInstance();
		String sql = "select a from Aluno a";
		Query query = em.createQuery(sql);
		return query.getResultList();
	}
	
	public List<Professor> getProfessores(){
		em = Connection.getInstance();
		String sql = "select p from Professor p order by p.nome";
		Query query = em.createQuery(sql);
		return query.getResultList();
	}
	
	public List<DisciplinaProfessor> getDisciplinasByName(String nome){
		em = Connection.getInstance();
		String sql = "select d from DisciplinaProfessor d where d.nomeDisciplina = :nome order by d.nomeDisciplina";
		Query query = em.createQuery(sql);
		query.setParameter("nome", nome);
		return query.getResultList();
	}
	
	public List<DisciplinaProfessor> getDisciplinasByProfessor(Professor professor){
		em = Connection.getInstance();
		String sql = "select d from DisciplinaProfessor d where d.professor = :professor order by d.professor.nome";
		Query query = em.createQuery(sql);
		query.setParameter("professor", professor);
		return query.getResultList();
	}

}
