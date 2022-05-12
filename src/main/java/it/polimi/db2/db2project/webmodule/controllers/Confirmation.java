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

import it.polimi.db2.db2project.ejbmodule.entities.*;
import it.polimi.db2.db2project.ejbmodule.services.*;
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
    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/CustomerService")
    private CustomerService customerService;

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
        TelcoPackage telcoPackage;
        Integer failedOrderID = null;
        Integer amount = 0;
        Integer noopamount = 0;

        if(session.getAttribute("payment").equals(false)) {
            if (request.getParameter("failedorderID") != null) {
                failedOrderID = Integer.parseInt(request.getParameter("failedorderID"));
                CustomerOrder customerOrder = customerService.findOrderbyID(failedOrderID);
                session.setAttribute("customerOrder", customerOrder);
                //session.setAttribute("confirmation", true);
                session.setAttribute("failing", true);

                ValidityPeriod validityPeriod = customerOrder.getValidityPeriod();
                List<OptionalProduct> optionals = customerOrder.getOptionalProducts();
                amount += validityPeriod.getDuration() * validityPeriod.getPrice();
                noopamount = amount; // total amount without optional products fees
                for (OptionalProduct op : optionals) {
                    amount += op.getFee() * validityPeriod.getDuration();
                }

                //per Thymeleaf da sistemare
                session.setAttribute("optionals", customerOrder.getOptionalProducts());
                session.setAttribute("startDate", customerOrder.getStartDate());
                session.setAttribute("validityPeriod", customerOrder.getValidityPeriod());
                session.setAttribute("telcopackage", customerOrder.getTelcoPackage());
                session.setAttribute("amount", amount);
                session.setAttribute("noopamount", noopamount);
                session.setAttribute("confirmation", true);
            } else {
                packageID = (Integer) session.getAttribute("packageID");
                telcoPackage = packageService.findPackageByID(packageID);
                if (session.getAttribute("confirmation").equals(false)) {
                    //retrieve selected optional products
                    List<OptionalProduct> optionals = new ArrayList<>();
                    if (request.getParameter("optionals") != null) {
                        for (String optProd : request.getParameterValues("optionals")) {
                            OptionalProduct optionalProduct = optionalService.findOptionalByID(Integer.parseInt(optProd));
                            optionals.add(optionalProduct);
                        }
                    }

                    //retrieve start date
                    LocalDate startDate = LocalDate.parse(request.getParameterValues("startDate")[0]);

                    //retrieve Validity Period
                    ValidityPeriod validityPeriod = validityService.findValidityByID(Integer.parseInt(request.getParameterValues("validity")[0]));

                    amount += validityPeriod.getDuration() * validityPeriod.getPrice();
                    noopamount = amount; // total amount without optional products fees
                    for (OptionalProduct op : optionals) {
                        amount += op.getFee() * validityPeriod.getDuration();
                    }
                    //save the order variables in the session
                    session.setAttribute("optionals", optionals);
                    session.setAttribute("startDate", startDate);
                    session.setAttribute("validityPeriod", validityPeriod);
                    session.setAttribute("amount", amount);
                    session.setAttribute("noopamount", noopamount);
                    session.setAttribute("telcopackage", telcoPackage);
                    session.setAttribute("confirmation", true);
                }
                if (session.getAttribute("user") != null) {
                    User user = (User) session.getAttribute("user");
                    session.setAttribute("user2", user.getName());
                }
            }

            String path = "/WEB-INF/confirmation.html";
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            templateEngine.process(path, ctx, response.getWriter());
        }else{
            String path = getServletContext().getContextPath() + "/home";
            response.sendRedirect(path);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getServletContext().getContextPath() + "/home";
        resp.sendRedirect(path);
    }

    public void destroy() {
    }


}
