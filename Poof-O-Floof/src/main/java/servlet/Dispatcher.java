package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Dispatcher {

	boolean supports(HttpServletRequest req);
	void execute(HttpServletRequest req, HttpServletResponse resp);
}
