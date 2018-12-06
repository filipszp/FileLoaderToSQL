/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.profsoft.lomboktest.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import pl.profsoft.lomboktest.config.HibernateConfig;
import pl.profsoft.lomboktest.entities.Client;

/**
 *
 * @author filip
 */
public class ClientService {

	SessionFactory factory = HibernateConfig.getSessionFactory();
	Session session = factory.getCurrentSession();

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public void createClients(Set<Client> clientList) throws ParseException {
		Client c1 = new Client();
		c1.setId(2L);
		c1.setFirstName("123fdfsfs");
		c1.setCreateDate(dateFormat.parse("2019-12-10"));

		try {
			session.getTransaction().begin();

			clientList.forEach(client -> {
				session.persist(client);
			});

			// Use session.flush () to actively push the changes to the DB.
			// It works for all changed Persistent objects.
			session.flush();

			System.out.println("- Flush OK");
			System.out.println("- Calling commit...");
			// Commit
			session.getTransaction().commit();
			System.out.println("- Commit OK");
		} catch (Exception e) {
			e.printStackTrace();
			//session.getTransaction().rollback();
		}

	}

}
