package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {

    public static void main(String[] args) {
        UserDao userDao = new UserDaoJDBCImpl();
        UserDao userDaoH = new UserDaoHibernateImpl();

        userDao.dropUsersTable();
        userDao.createUsersTable();


        userDaoH.saveUser("Name1", "LastName1", (byte) 20);
        System.out.println("User c именем - Name1 добавлен в базу данных");
        //userDaoH.saveUser("Name4", "LastName4", (byte) 38);
        //System.out.println("User c именем - Name4 добавлен в базу данных");

        System.out.println(userDao.getAllUsers().toString());
        userDaoH.removeUserById(1);
        System.out.println(userDao.getAllUsers().toString());
        userDao.dropUsersTable();
    }
}
