package it.polimi.db2.db2project.webmodule.controllers.employee;

import it.polimi.db2.db2project.ejbmodule.entities.*;
import it.polimi.db2.db2project.ejbmodule.services.CustomerService;
import it.polimi.db2.db2project.ejbmodule.services.OptionalService;
import it.polimi.db2.db2project.ejbmodule.services.SalesReportService;
import it.polimi.db2.db2project.ejbmodule.services.UserService;
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

@WebServlet("/salesreport")
public class SalesReport extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/SalesReportService")
    private SalesReportService salesReportService;
    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/OptionalService")
    private OptionalService optionalService;
    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/UserService")
    private UserService userService;
    @EJB(name = "it.polimi.db2.db2project.ejbmodule.services/CustomerService")
    private CustomerService customerService;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        final WebContext ctx = new WebContext(request, response, getServletContext(), request.getLocale());
        List<Object[]> totPurchPckg = new ArrayList<>();
        List<Object[]> totPurchPckgVp = new ArrayList<>();
        List<Object[]> totValueOp = new ArrayList<>();
        List<Object[]> totValueNoop = new ArrayList<>();
        Double optionalsAvg;
        List<Object[]> bestSeller;
        OptionalProduct op;
        List<User> insolventUsers = new ArrayList<>();
        List<CustomerOrder> suspendedOrders = new ArrayList<>();
        List<Alert> alerts = new ArrayList<>();
        if (session.isNew() || session.getAttribute("emp") == null) {
            templateEngine.process("/index.html", ctx, response.getWriter());
        } else {
            Employee user = (Employee) session.getAttribute("emp");
            session.setAttribute("empname", user.getName());
            totPurchPckg = salesReportService.totPurchPckg();
            totPurchPckgVp = salesReportService.totPurchPckgVp();
            totValueOp = salesReportService.totValueOp();
            totValueNoop = salesReportService.totValueNoop();
            optionalsAvg = salesReportService.optionalsAvg();
            bestSeller = salesReportService.bestSellerOp();
            op = optionalService.findOptionalByID((Integer) bestSeller.get(0)[0]);
            insolventUsers = userService.insolventUsers();
            suspendedOrders = customerService.suspendedOrders();
            alerts = salesReportService.alerts();
            session.setAttribute("totPurchPckg", totPurchPckg);
            session.setAttribute("totPurchPckgVp", totPurchPckgVp);
            session.setAttribute("totValueOp", totValueOp);
            session.setAttribute("totValueNoop", totValueNoop);
            session.setAttribute("optionalsAvg", optionalsAvg);
            session.setAttribute("bestSeller", bestSeller);
            session.setAttribute("optionale", op);
            session.setAttribute("insolvent", insolventUsers);
            session.setAttribute("suspended", suspendedOrders);
            session.setAttribute("alerts", alerts);
            templateEngine.process("/WEB-INF/salesreport.html", ctx, response.getWriter());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getServletContext().getContextPath() + "/employeepage";
        resp.sendRedirect(path);
    }
}
