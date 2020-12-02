/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import com.mservice.allinone.models.CaptureMoMoResponse;
import com.mservice.allinone.processor.allinone.CaptureMoMo;
import com.mservice.shared.sharedmodels.Environment;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author MinHiu
 */
@WebServlet(name = "SendMoMoRequestServlet", urlPatterns = {"/SendMoMoRequestServlet"})
public class SendMoMoRequestServlet extends HttpServlet {

    final String ERROR_PAGE = "loadCart";

    static Logger log = Logger.getLogger(SendMoMoRequestServlet.class.getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");

	PrintWriter out = response.getWriter();

	String url = ERROR_PAGE;
	Environment env = null;

	try {
	    String orderId = (String) request.getAttribute("ORDER_ID");
	    if (orderId != null) {
		String requestId = String.valueOf(System.currentTimeMillis());
		float total = (float) request.getAttribute("TOTAL_PAY");
		long totalPay = (long) (total * 23000);

		String orderInfo = "Buy mooncake.";
		String returnUrl = "http://localhost:8084/YellowMoonShop/processMoMoPayment";
		String notifyUrl = "http://localhost:8084/YellowMoonShop/processMoMoPayment";

		env = Environment.selectEnv("dev", Environment.ProcessType.PAY_GATE);

		CaptureMoMoResponse captureMoMoResponse = CaptureMoMo.process(env, orderId, requestId, Long.toString(totalPay), orderInfo, returnUrl, notifyUrl, "");
		if (captureMoMoResponse != null) {
		    url = captureMoMoResponse.getPayUrl();
		}

	    } 
	    if(url.equals(ERROR_PAGE)){
		url += "?sendingError=We can't send your request to Momo server. Please try again.";
	    }

	} catch (Exception ex) {
	    log.error(ex.getMessage());
	    url += "?sendingError=We can't send your request to Momo server. Please try again.";
	} finally {
	    response.sendRedirect(url);
	    out.close();
	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>

}
