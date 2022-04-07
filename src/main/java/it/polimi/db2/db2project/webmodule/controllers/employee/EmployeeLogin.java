package it.polimi.db2.db2project.webmodule.controllers.employee;

import it.polimi.db2.db2project.ejbmodule.entities.Employee;
import it.polimi.db2.db2project.ejbmodule.entities.User;
import it.polimi.db2.db2project.ejbmodule.exceptions.WrongCredentialsException;
import it.polimi.db2.db2project.ejbmodule.services.EmployeeService;
import it.polimi.db2.db2project.ejbmodule.services.UserService;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.*;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/employeelogin")
public class EmployeeLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/EmployeeService")
    private EmployeeService empService;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usrn = null;
        String pwd = null;

        try {
            usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
            pwd = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
            if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) throw new Exception("Missing or empty credential value");
        } catch (Exception e) {
            // for debugging only e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }

        Employee emp = null;
        try {
            // query db to authenticate for user
            emp = empService.checkEmployeeCredentials(usrn, pwd);
        } catch (WrongCredentialsException | NonUniqueResultException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
            return;
        }

        // If the user exists, add info to the session and go to home page, otherwise
        // show login page with error message

        String path;
        if (emp == null) {
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("errorMsg", "Incorrect username or password");
            path = "/index.html";
            templateEngine.process(path, ctx, response.getWriter());
        } else {
            request.getSession().setAttribute("emp", emp);
            path = getServletContext().getContextPath() + "/employeepage";
            response.sendRedirect(path);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        WebContext ctx = new WebContext(req, resp, getServletContext());
        if (session.isNew() || session.getAttribute("emp") == null) {
            ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(ctx.getServletContext());
            TemplateEngine engine = new TemplateEngine();
            engine.setTemplateResolver(resolver);
            engine.process("index.html", ctx, ctx.getResponse().getWriter());
            return;
        }

        String homePath = getServletContext().getContextPath() + "/hello-world";
        resp.sendRedirect(homePath);
    }
}

