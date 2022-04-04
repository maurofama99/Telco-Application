package it.polimi.db2.db2project.webmodule.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polimi.db2.db2project.ejbmodule.entities.OptionalProduct;
import it.polimi.db2.db2project.ejbmodule.entities.TelcoPackage;
import it.polimi.db2.db2project.ejbmodule.entities.User;
import it.polimi.db2.db2project.ejbmodule.entities.ValidityPeriod;
import it.polimi.db2.db2project.ejbmodule.services.OptionalService;
import it.polimi.db2.db2project.ejbmodule.services.PackageService;
import it.polimi.db2.db2project.ejbmodule.services.UserService;
import it.polimi.db2.db2project.ejbmodule.services.ValidityService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@WebServlet("/confirmation")
public class Confirmation extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/PackageService")
    private PackageService packageService;
    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/OptionalService")
    private OptionalService optionalService;
    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/ValidyService")
    private ValidityService validityService;

    public Confirmation() {
        super();
    }

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        //retrieve packageID from session
        Integer packageID = null;
        packageID = (Integer) session.getAttribute("packageID");
        TelcoPackage telcoPackage;
        telcoPackage = packageService.findPackageByID(packageID);

        //retrieve selected optional products
        List<OptionalProduct> optionals = new ArrayList<>();
        if(request.getParameter("optionals") != null){
            for(String optProd : request.getParameterValues("optionals")){
                OptionalProduct optionalProduct = optionalService.findOptionalByID(Integer.parseInt(optProd));
                optionals.add(optionalProduct);
            }
        }

        //retrieve start date
        LocalDate startDate = LocalDate.parse(request.getParameterValues("startDate")[0]);

        //retrieve Validity Period
        ValidityPeriod validityPeriod = validityService.findValidityByID(Integer.parseInt(request.getParameterValues("validity")[0]));


        //save the order variables in the session
        session.setAttribute("optionals", optionals);
        session.setAttribute("startDate", startDate);
        session.setAttribute("validityPeriod", validityPeriod);


        String path = "/WEB-INF/confirmation.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("telcopackage", telcoPackage);
        ctx.setVariable("optionals", optionals);
        ctx.setVariable("validityPeriod", validityPeriod);


        templateEngine.process(path, ctx, response.getWriter());

    }

    public void destroy() {
    }


}
