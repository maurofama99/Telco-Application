package it.polimi.db2.db2project.webmodule.controllers.employee;

import it.polimi.db2.db2project.ejbmodule.entities.*;
import it.polimi.db2.db2project.ejbmodule.services.EmployeeService;
import it.polimi.db2.db2project.ejbmodule.services.OptionalService;
import it.polimi.db2.db2project.ejbmodule.services.PackageService;
import it.polimi.db2.db2project.ejbmodule.services.ServiceService;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/employeepage")
public class EmployeePage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/PackageService")
    private PackageService packageService;
    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/ServiceService")
    private ServiceService serviceService;
    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/OptionalService")
    private OptionalService optionalService;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        final WebContext ctx = new WebContext(request, response, getServletContext(), request.getLocale());
        if (session.isNew() || session.getAttribute("emp") == null) {
            templateEngine.process("/index.html", ctx, response.getWriter());
        } else {
            Employee user = (Employee) session.getAttribute("emp");
            session.setAttribute("empname", user.getName());
            templateEngine.process("/WEB-INF/employeehome.html", ctx, response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        final WebContext ctx = new WebContext(request, response, getServletContext(), request.getLocale());
        if (action == null) {
            templateEngine.process("/WEB-INF/employeehome.html", ctx, response.getWriter());
        } else {
            if (action.equalsIgnoreCase("fixedphone")) {
                doGetFixedPhone(request, response);
            } else if (action.equalsIgnoreCase("mobilephone")) {
                doGetMobilePhone(request, response);
            } else if (action.equalsIgnoreCase("fixedinternet")) {
                doGetFixedInternet(request, response);
            } else if (action.equalsIgnoreCase("mobileinternet")) {
                doGetMobileInternet(request, response);
            } else throw new IllegalStateException("Problem with creating the service");

        }
    }

    private void doGetMobileInternet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("services") == null) {
            List<Service> services = new ArrayList<Service>();
            services.add(new Service("mobileinternet", Integer.parseInt(request.getParameter("ngiga")), Integer.parseInt(request.getParameter("extragigafee"))));
            session.setAttribute("services", services);
        } else {
            List<Service> services = (List<Service>) session.getAttribute("services");
            services.add(new Service("mobileinternet", Integer.parseInt(request.getParameter("ngiga")), Integer.parseInt(request.getParameter("extragigafee"))));
            session.setAttribute("services", services);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/employeepage");
    }

    private void doGetFixedInternet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("services") == null) {
            List<Service> services = new ArrayList<Service>();
            services.add(new Service("fixedinternet", Integer.parseInt(request.getParameter("ngiga")), Integer.parseInt(request.getParameter("extragigafee"))));
            session.setAttribute("services", services);
        } else {
            List<Service> services = (List<Service>) session.getAttribute("services");
            services.add(new Service("fixedinternet", Integer.parseInt(request.getParameter("ngiga")), Integer.parseInt(request.getParameter("extragigafee"))));
            session.setAttribute("services", services);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/employeepage");
    }

    private void doGetMobilePhone(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("services") == null) {
            List<Service> services = new ArrayList<Service>();
            services.add(new Service(Integer.parseInt(request.getParameter("nmin")), Integer.parseInt(request.getParameter("nsms")), Integer.parseInt(request.getParameter("extraminfee")), Integer.parseInt(request.getParameter("extrasmsfee"))));
            session.setAttribute("services", services);
        } else {
            List<Service> services = (List<Service>) session.getAttribute("services");
            services.add(new Service(Integer.parseInt(request.getParameter("nmin")), Integer.parseInt(request.getParameter("nsms")), Integer.parseInt(request.getParameter("extraminfee")), Integer.parseInt(request.getParameter("extrasmsfee"))));
            session.setAttribute("services", services);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/employeepage");
    }

    private void doGetFixedPhone(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("services") == null) {
            List<Service> services = new ArrayList<Service>();
            services.add(new Service());
            session.setAttribute("services", services);
        } else {
            List<Service> services = (List<Service>) session.getAttribute("services");
            services.add(new Service());
            session.setAttribute("services", services);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/employeepage");
    }

}
