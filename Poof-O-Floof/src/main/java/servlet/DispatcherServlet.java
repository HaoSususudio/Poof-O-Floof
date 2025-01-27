package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LogManager.getLogger(getClass());
	private final Dispatcher dispatcherChain = DispatcherChain.getInstance();

	public DispatcherServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (dispatcherChain.supports(req)) {
			dispatcherChain.execute(req, resp);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
	}
	
	public boolean testDoGet(HttpServletRequest req, HttpServletResponse resp) {
		if (dispatcherChain.supports(req)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// We need to configure the different options such that our application
		// can RESPOND to any client, as long as it's a valid request to our resource
		logger.info("{} request coming to {}", req.getMethod(), req.getRequestURI());
		if (dispatcherChain.supports(req)) {
			dispatcherChain.execute(req, resp);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
	}

}
