package it.polimi.db2.db2project.webmodule.controllers.employee;

import it.polimi.db2.db2project.ejbmodule.entities.*;
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
            List<OptionalProduct> optionalProducts = optionalService.getAllOptionalProducts();
            session.setAttribute("existingoptionals", optionalProducts);
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
            switch (action) {
                case "fixedphone":
                    doPostFixedPhone(request, response);
                    break;
                case "createpackage":
                    doGetCreatePackage(request, response);
                    break;
                case "addexistingoptionals":
                    doGetAddExistingOptionals(request, response);
                    break;
                case "mobilephone":
                    doGetMobilePhone(request, response);
                    break;
                case "fixedinternet":
                    doGetFixedInternet(request, response);
                    break;
                case "mobileinternet":
                    doGetMobileInternet(request, response);
                    break;
                case "createoptional":
                    doGetCreateOptional(request, response);
                    break;
                case "createvp":
                    doGetCreateVp(request, response);
                    break;
                default:
                    throw new IllegalStateException("Cannot find action keyword, check keyword correctness");
            }
        }
    }

    private void doGetCreateVp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("vperiods") == null) {
            List<ValidityPeriod> vperiods = new ArrayList<ValidityPeriod>();
            vperiods.add(new ValidityPeriod(Integer.parseInt(request.getParameter("duration")), Integer.parseInt(request.getParameter("price"))));
            session.setAttribute("vperiods", vperiods);
        } else {
            List<ValidityPeriod> vperiods = (List<ValidityPeriod>) session.getAttribute("vperiods");
            vperiods.add(new ValidityPeriod(Integer.parseInt(request.getParameter("duration")), Integer.parseInt(request.getParameter("price"))));
            session.setAttribute("vperiods", vperiods);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/employeepage");
    }

    private void doGetAddExistingOptionals(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String[] checkedoptionalsids = request.getParameterValues("checkedexistingoptionals"); // qui ho messo in checkedoptionalsids tutti gli id degli optionals esistenti selezionati
        if (session.getAttribute("optionals") == null) {
            List<OptionalProduct> optionals = new ArrayList<OptionalProduct>();
            // caso in cui nessun optional è stato aggiunto alla sessione, devo prendere tutti gli optional corrispondenti agli id dal database e metterli nella sessione
            for (String id: checkedoptionalsids) {
                optionals.add(optionalService.findOptionalByID(Long.parseLong(id)));
            }
            session.setAttribute("optionals", optionals);
        } else {
            List<OptionalProduct> optionals = (List<OptionalProduct>) session.getAttribute("optionals");
            for (String id: checkedoptionalsids) {
                if (optionals.stream().noneMatch(o -> o.getId().equals(Long.parseLong(id)))){
                    optionals.add(optionalService.findOptionalByID(Long.parseLong(id)));
                }
            }
            session.setAttribute("optionals", optionals);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/employeepage");
    }

    private void doGetCreatePackage(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        packageService.createPackage(StringEscapeUtils.escapeJava(request.getParameter("packagename")),(List<Service>) session.getAttribute("services"), (List<OptionalProduct>) session.getAttribute("optionals"), (List<ValidityPeriod>) session.getAttribute("vperiods"));
        // TODO azzera tutti gli attributi della session
    }

    private void doGetCreateOptional(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("optionals") == null) {
            List<OptionalProduct> optionals = new ArrayList<OptionalProduct>();
            optionals.add(new OptionalProduct(StringEscapeUtils.escapeJava(request.getParameter("name")), Integer.parseInt(request.getParameter("fee"))));
            session.setAttribute("optionals", optionals);
        } else {
            List<OptionalProduct> optionals = (List<OptionalProduct>) session.getAttribute("optionals");
            optionals.add(new OptionalProduct(StringEscapeUtils.escapeJava(request.getParameter("name")), Integer.parseInt(request.getParameter("fee"))));
            session.setAttribute("optionals", optionals);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/employeepage");
        // persist anche nel db
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

    private void doPostFixedPhone(HttpServletRequest request, HttpServletResponse response) throws IOException {
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