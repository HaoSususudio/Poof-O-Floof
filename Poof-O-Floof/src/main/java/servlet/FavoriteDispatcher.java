package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.FavoriteHandler;

public class FavoriteDispatcher implements Dispatcher {

	@Override
	public boolean supports(HttpServletRequest req) {
		return isSaveFavorite(req) || isGetFavorites(req);
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		if (isSaveFavorite(req)) {
			FavoriteHandler.handleSaveFavorite(req, resp);
		} else if (isGetFavorites(req)) {
			FavoriteHandler.handleGetFavorites(req, resp);
		}

	}

	private boolean isSaveFavorite(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getRequestURI().equals("/Poof-O-Floof/api/favorite");
	}

	private boolean isGetFavorites(HttpServletRequest req) {
		return req.getMethod().equals("GET") && req.getRequestURI().equals("/Poof-O-Floof/api/favorite");
	}
}
