package by.epam.onlinetraining.controller;

import by.epam.onlinetraining.command.ActionCommand;
import by.epam.onlinetraining.command.CommandFactory;
import by.epam.onlinetraining.content.NavigationType;
import by.epam.onlinetraining.content.RequestContent;
import by.epam.onlinetraining.content.ActionResult;
import by.epam.onlinetraining.exception.CommandException;
import by.epam.onlinetraining.dao.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class FrontController extends HttpServlet {
    private static Logger LOGGER = LogManager.getLogger(FrontController.class);
    private static CommandFactory commandFactory = new CommandFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        RequestContent requestContent = new RequestContent();
        requestContent.extractValues(request);

        ActionCommand actionCommand = commandFactory.initCommand(requestContent);
        if(actionCommand != null){
            try{
                ActionResult actionResult = actionCommand.execute(requestContent);

                requestContent.insertAttributes(request);
                String page = actionResult.getPage();
                NavigationType navigationType = actionResult.getNavigationType();

                if (navigationType == NavigationType.FORWARD) {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
                    requestDispatcher.forward(request, response);
                } else if (navigationType == NavigationType.REDIRECT) {
                    response.sendRedirect(page);
                }

            } catch(CommandException e){
                LOGGER.log(Level.FATAL, "Exception during executing command: ", e);
                throw new ServletException("Problem when process request: ", e);
            }
        } else {
            LOGGER.log(Level.FATAL, "Exception during executing command: wrong or empty incoming command name");
            throw new ServletException("Wrong or empty command name.");
        }
    }

    @Override
    public void destroy(){
        ConnectionPool poolInstance = ConnectionPool.getInstance();
        poolInstance.terminatePool();
        super.destroy();
    }
}
