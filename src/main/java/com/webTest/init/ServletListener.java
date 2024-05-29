package com.webTest.init;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ServletListener implements ServletContextListener {//отслеживает изменения в контексте

    @SuppressWarnings("unused")
    DataSource datasource;

    InitialContext initContext;

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // TODO document why this method is empty
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {//доступен для всех сервлетов из нашего проекта

        try {
            initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");//ищет окружение
            datasource = (DataSource) envContext.lookup("jdbc/databasecourse");//ищет в окружение по имени; добавляем соединения к сервлетам для дальнейшй работы
            event.getServletContext().setAttribute("datasource", datasource);

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}

