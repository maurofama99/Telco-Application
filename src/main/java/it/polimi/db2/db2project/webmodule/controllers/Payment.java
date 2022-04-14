package it.polimi.db2.db2project.webmodule.controllers;

import it.polimi.db2.db2project.ejbmodule.entities.*;
import it.polimi.db2.db2project.ejbmodule.exceptions.WrongCredentialsException;
import it.polimi.db2.db2project.ejbmodule.services.*;
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
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@WebServlet(urlPatterns = "/payment")
public class Payment extends  HttpServlet{
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/CustomerService")
    private CustomerService customerService;



    public Payment() {
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
        Random rand = new Random();
        int int_random = rand.nextInt(2);
        int amount = 0;
        boolean orderStatus;

        if(session.getAttribute("failing") != null && session.getAttribute("failing").equals(true)) {
            CustomerOrder customerOrder = (CustomerOrder) session.getAttribute("customerOrder");
            customerService.updateStatus(customerOrder.getId());
            session.setAttribute("status", true);

        }else{
            User user = (User) session.getAttribute("user");
            TelcoPackage telcoPackage = (TelcoPackage) session.getAttribute("telcopackage");
            ValidityPeriod validityPeriod = (ValidityPeriod) session.getAttribute("validityPeriod");
            List<OptionalProduct> optionals = (List<OptionalProduct>) session.getAttribute("optionals");
            LocalDate startDate = (LocalDate) session.getAttribute("startDate");
            LocalDate date = LocalDate.now();

            amount += validityPeriod.getDuration() * validityPeriod.getPrice();
            for (OptionalProduct op : optionals) {
                amount += op.getFee() * validityPeriod.getDuration();
            }

            if (int_random == 0) {
                orderStatus = true;
                session.setAttribute("status", true);
            } else {
                orderStatus = false;
                session.setAttribute("status", false);
            }

            //session parameter to redirect to home
            session.setAttribute("home", true);
            customerService.newOrder(user, telcoPackage, validityPeriod, date, startDate, orderStatus, amount, optionals);
        }

        String path = "/WEB-INF/payment.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public void destroy() {
    }
}
