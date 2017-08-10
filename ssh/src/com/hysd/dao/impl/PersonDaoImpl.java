package com.hysd.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hysd.dao.BaseDAO;
import com.hysd.dao.PersonDao;
import com.hysd.entity.Person;

@Repository
public class PersonDaoImpl implements PersonDao {

	@Autowired
	private BaseDAO<Person> baseDAO;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	public void insert() {
		
		Person person = new Person();
		person.setName("张三 ");
		person.setAge(20);
		person.setPassword("1234");
		baseDAO.save(person);
	}

	public static void main(String[] args) {
		ApplicationContext context =new  ClassPathXmlApplicationContext("applicationContext.xml");
		PersonDao persondao =context.getBean(PersonDao.class);
		persondao.insert();
	}

	
}
