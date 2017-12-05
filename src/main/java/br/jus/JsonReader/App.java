package br.jus.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.jus.JsonReader.DAO.GenericDAO;
import br.jus.JsonReader.model.Aluno;
import br.jus.JsonReader.model.DisciplinaProfessor;
import br.jus.JsonReader.model.Professor;
import br.jus.JsonReader.model.SemestreAluno;
import br.jus.JsonReader.model.Turma;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		Set<String> situacoes = new HashSet<String>();
		Set<String> nomesDisciplinas = new HashSet<String>();
		try {
			JSONArray array = (JSONArray) parser.parse(new FileReader("arquivos\\turmas.json"));
			JSONArray alunosArray;
			GenericDAO dao = new GenericDAO();
			for (Object object : array) {
				JSONObject jsonObject = (JSONObject) object;
				
				Long id = jsonObject.get("id_turma") != null ? Long.parseLong((String) (jsonObject.get("id_turma")))
						: null;
				String situacao = jsonObject.get("situacao_turma") != null
						? jsonObject.get("situacao_turma").toString()
						: null;
				alunosArray = (JSONArray) jsonObject.get("alunos");
				if (!dao.turmaExiste(id) && situacao.equals("CONSOLIDADA") && alunosArray.size() > 0) {

					String nome = jsonObject.get("nome_componente") != null
							? jsonObject.get("nome_componente").toString()
							: null;
					nomesDisciplinas.add(nome);
					int ano = jsonObject.get("ano") != null ? Integer.parseInt(jsonObject.get("ano").toString()) : 0;
					int periodo = jsonObject.get("periodo") != null
							? Integer.parseInt(jsonObject.get("periodo").toString())
							: 0;
					String codigo = jsonObject.get("codigo_turma") != null ? jsonObject.get("codigo_turma").toString()
							: null;
					
					String tipo = jsonObject.get("tipo") != null ? jsonObject.get("tipo").toString() : null;
					String nivel = jsonObject.get("nivel_ensino") != null ? jsonObject.get("nivel_ensino").toString()
							: null;
					int capacidade = jsonObject.get("capacidade_aluno") != null
							? Integer.parseInt(jsonObject.get("capacidade_aluno").toString())
							: 0;

					Turma turma = new Turma();
					turma.setIdTurma(id);
					turma.setNomeComponente(nome);
					turma.setAno(ano);
					turma.setPeriodo(periodo);
					turma.setCodigo(codigo);
					turma.setSituacao(situacao);
					turma.setTipo(tipo);
					turma.setNivel(nivel);
					turma.setCapacidade(capacidade);
					
					
					for(Object alunoObject : alunosArray) {
						JSONObject alunoJson = (JSONObject) alunoObject;
						String codigoAluno = alunoJson.get("discente") != null
								? alunoJson.get("discente").toString()
								: null;
						String anoPeriodo = ano + "." + periodo;
						SemestreAluno semestreAluno = new SemestreAluno();
						int unidade = alunoJson.get("unidade") != null
								? Integer.parseInt(alunoJson.get("unidade").toString())
										: 0;
						Aluno aluno = new Aluno();
						if(!dao.AlunoExiste(codigoAluno)) {
							
							aluno.setCodigoAluno(codigoAluno);
							String situacaoAluno = alunoJson.get("descricao") != null
									? alunoJson.get("descricao").toString()
									: null;
							situacoes.add(situacaoAluno);
							
							
							aluno.getSemestresAluno().put(anoPeriodo, semestreAluno);
							dao.save(aluno);
							semestreAluno.setAluno(aluno);
							semestreAluno.setAnoPeriodo(anoPeriodo);
							verificarSituacao(situacaoAluno, semestreAluno, turma);
							dao.save(semestreAluno);
						} else if(dao.AlunoExiste(codigoAluno) && unidade == 1) {
							aluno = dao.getAlunoByCod(codigoAluno);
							if(aluno.getSemestresAluno().containsKey(anoPeriodo)) {
								semestreAluno = aluno.getSemestresAluno().get(anoPeriodo);
							} else {
								semestreAluno.setAluno(aluno);
								semestreAluno.setAnoPeriodo(anoPeriodo);
							}
							String situacaoAluno = alunoJson.get("descricao") != null
									? alunoJson.get("descricao").toString()
									: null;
							situacoes.add(situacaoAluno);
							verificarSituacao(situacaoAluno, semestreAluno, turma);
							dao.update(semestreAluno);
						}
						if(semestreAluno.getAnoPeriodo() == null && semestreAluno.getQtdTurmas() > 0) {
							System.out.println("nulo");
						}
					}
					
					double turmaScore = turma.getQtdAlunos() > 0 ? (double)turma.getQtdApr()/turma.getQtdAlunos() : 0.0;
					turma.setScore(turmaScore*100.0);
					
					Long idProf = jsonObject.get("id_docente_interno") != null ? Long.parseLong((String) (jsonObject.get("id_docente_interno")))
							: null;
					Professor professor = new Professor();
					DisciplinaProfessor disciplinaProfessor = new DisciplinaProfessor();
					if(!dao.profExiste(idProf)) {						
						professor.setIdInterno(idProf);
						String nomeProf = jsonObject.get("nome_professor") != null
								? jsonObject.get("nome_professor").toString()
								: null;
						professor.setNome(nomeProf);
						professor.getDisciplinas().put(nome, disciplinaProfessor);
						dao.save(professor);
						
						double scoreDisciplina = turma.getScore();
						scoreDisciplina = BigDecimal.valueOf(scoreDisciplina).setScale(2, RoundingMode.HALF_UP)
							    .doubleValue();
						
						disciplinaProfessor.setNomeDisciplina(nome);
						disciplinaProfessor.setProfessor(professor);
						disciplinaProfessor.setQtdMinistradas(1);
						disciplinaProfessor.setScore(scoreDisciplina);
						dao.save(disciplinaProfessor);
					} else {
						professor = dao.getProfById(idProf);
						if(professor.getDisciplinas().containsKey(nome)) {
							
							disciplinaProfessor = professor.getDisciplinas().get(nome);
							
							int qtdDisciplina = disciplinaProfessor.getQtdMinistradas() + 1;
							disciplinaProfessor.setQtdMinistradas(qtdDisciplina);
							
							double scoreDisciplina = (disciplinaProfessor.getScore() + turma.getScore())/qtdDisciplina;
							
							scoreDisciplina = BigDecimal.valueOf(scoreDisciplina).setScale(2, RoundingMode.HALF_UP)
								    .doubleValue();
							
							disciplinaProfessor.setScore(scoreDisciplina);
							dao.update(disciplinaProfessor);
						} else {
							double scoreDisciplina = turma.getScore();
							scoreDisciplina = BigDecimal.valueOf(scoreDisciplina).setScale(2, RoundingMode.HALF_UP)
								    .doubleValue();
							
							disciplinaProfessor.setNomeDisciplina(nome);
							disciplinaProfessor.setProfessor(professor);
							disciplinaProfessor.setQtdMinistradas(1);
							disciplinaProfessor.setScore(scoreDisciplina);
							professor.getDisciplinas().put(nome, disciplinaProfessor);
							dao.save(disciplinaProfessor);
						}
					}
					
					turma.setProfessor(professor);
					turma.setNomeProfessor(professor.getNome());
					dao.save(turma);
				}		
				
			}
			
			for(Aluno aluno : dao.getAlunos()) {
				int qtdSemestres = aluno.getSemestresAluno().size();
				double scoreAux = 0.0;
				for(String anoPeriodo : aluno.getSemestresAluno().keySet()) {
					scoreAux += aluno.getSemestresAluno().get(anoPeriodo).getScore();
				}
				aluno.setScoreTotal(scoreAux/qtdSemestres);
				dao.update(aluno);
			}
			File relatorioDisciplinas = new File("arquivos\\relatorioDisciplinas.txt");
			FileWriter writer = new FileWriter(relatorioDisciplinas);
			
			List<String> disciplinasOrdenadas = new ArrayList<String>(nomesDisciplinas);
			Collections.sort(disciplinasOrdenadas);
			for(String nomeDisciplina : disciplinasOrdenadas) {
				String texto = "Nome Disciplina: " + nomeDisciplina + "\n";
				for(DisciplinaProfessor disciplinaProfessor : dao.getDisciplinasByName(nomeDisciplina)) {
					int qtdMinistrada = disciplinaProfessor.getQtdMinistradas();
					texto += "Professor: " + disciplinaProfessor.getProfessor().getNome()  + "\n" +
							"Score: " +  disciplinaProfessor.getScore() + " - Ministrou : " +
							(qtdMinistrada > 1 ? qtdMinistrada + " vezes" : qtdMinistrada + " vez") + "\n";
				}
				texto += "\n";
				writer.write(texto);
			}
			
			writer.close();
			
			File relatorioProfessores = new File("arquivos\\relatorioProfessores.txt");
			writer = new FileWriter(relatorioProfessores);
			for(Professor professor : dao.getProfessores()) {
				String texto = "Professor: " + professor.getNome() + "\n";
				for(DisciplinaProfessor disciplinaProfessor : dao.getDisciplinasByProfessor(professor)) {
					int qtdMinistrada = disciplinaProfessor.getQtdMinistradas();
					texto += "Nome Disciplina: " + disciplinaProfessor.getNomeDisciplina()  + "\n" +
							"Score: " +  disciplinaProfessor.getScore() + " - Ministrou : " +
							(qtdMinistrada > 1 ? qtdMinistrada + " vezes" : qtdMinistrada + " vez") + "\n";
				}
				texto += "\n";
				writer.write(texto);
			}
			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String s : situacoes) {
			System.out.println(s);
		}
		System.out.println("Hello World!");
	}
	
	public static void verificarSituacao(String situacao, SemestreAluno semestreAluno, Turma turma) {
		if(situacao.toLowerCase().equals("trancado")) {
			semestreAluno.setQtdTranc(semestreAluno.getQtdTranc() + 1);
			semestreAluno.setQtdTurmas(semestreAluno.getQtdTurmas() + 1);
			
			turma.setQtdTranc(turma.getQtdTranc() + 1);
			turma.setQtdAlunos(turma.getQtdAlunos() + 1);
			
			atualizaScoreAluno(semestreAluno);
			
		} else if(situacao.toLowerCase().contains("aprovado")) {
			semestreAluno.setQtdApr(semestreAluno.getQtdApr() + 1);
			semestreAluno.setQtdTurmas(semestreAluno.getQtdTurmas() + 1);
			
			turma.setQtdApr(turma.getQtdApr() + 1);
			turma.setQtdAlunos(turma.getQtdAlunos() + 1);
			
			atualizaScoreAluno(semestreAluno);
			
		} else if(situacao.toLowerCase().equals("reprovado") || situacao.toLowerCase().equals("reprovado por nota")) {
			semestreAluno.setQtdRep(semestreAluno.getQtdRep() + 1);
			semestreAluno.setQtdTurmas(semestreAluno.getQtdTurmas() + 1);

			turma.setQtdRep(turma.getQtdRep() + 1);
			turma.setQtdAlunos(turma.getQtdAlunos() + 1);
			
			atualizaScoreAluno(semestreAluno);
			
		} else if(situacao.toLowerCase().equals("reprovado por mdia e por faltas") || situacao.toLowerCase().equals("reprovado por nota e falta")) {
			semestreAluno.setQtdRepMedFalt(semestreAluno.getQtdRepMedFalt() + 1);
			semestreAluno.setQtdTurmas(semestreAluno.getQtdTurmas() + 1);

			turma.setQtdRepMedFalt(turma.getQtdRepMedFalt() + 1);
			turma.setQtdAlunos(turma.getQtdAlunos() + 1);
			
			atualizaScoreAluno(semestreAluno);
		} else if(situacao.toLowerCase().equals("reprovado por faltas")) {
			semestreAluno.setQtdRepFalt(semestreAluno.getQtdRepFalt() + 1);
			semestreAluno.setQtdTurmas(semestreAluno.getQtdTurmas() + 1);

			turma.setQtdRepMedFalt(turma.getQtdRepMedFalt() + 1);
			turma.setQtdAlunos(turma.getQtdAlunos() + 1);
			
			atualizaScoreAluno(semestreAluno);
		}
		
		
	}
	
	private static void atualizaScoreAluno(SemestreAluno semestreAluno) {
		double scoreAluno = (double)semestreAluno.getQtdApr()/semestreAluno.getQtdTurmas();
		semestreAluno.setScore(scoreAluno*100.0);
	}
}
