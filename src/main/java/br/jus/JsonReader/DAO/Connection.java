package br.jus.JsonReader.DAO;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Connection {
	private static EntityManager em;

	public Connection() {
	}
	
	public static EntityManager getInstance(){
		
		if(em == null){
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("learning");
			
			em = emf.createEntityManager();
			em.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
			em.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
		}
		
		return em;
	}
}