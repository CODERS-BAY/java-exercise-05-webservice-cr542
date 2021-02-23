package servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Fruit;
import model.FruitDB;

/**
 * Servlet implementation class FruitServlet
 */
@WebServlet("/FruitServlet")
public class FruitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		FruitDB.insertFruit(new Fruit("orange", "orange"));
		FruitDB.insertFruit(new Fruit("orange", "orange"));
		FruitDB.insertFruit(new Fruit("pear", "green"));
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action) {
			case "getAll" -> response.getWriter().append(new Gson().toJson(FruitDB.getFruits()));
			case "getByParam" -> getByParam(request, response);
			case "edit" -> update(request, response);
			default -> respond(response, "[HTTPResponse] "+ action + " is not a valid parameter");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void getByParam(HttpServletRequest request, HttpServletResponse response) throws IOException {
		prepare(response, "application/json");
		String variety = request.getParameter("variety");
		String color = request.getParameter("color");

		if(variety != null) {
			List<Fruit> result = FruitDB.getFruits().stream().filter(fruit -> fruit.getVariety().equals(variety)).collect(Collectors.toList());
			response.getWriter().append(new Gson().toJson(result));
		} else if(color != null) {
			List<Fruit> result = FruitDB.getFruits().stream().filter(fruit -> fruit.getColor().equals(color)).collect(Collectors.toList());
			response.getWriter().append(new Gson().toJson(result));
		} else {
			System.out.println("Both fields are empty...");
		}
	}

	protected void update(HttpServletRequest request, HttpServletResponse response) {
		prepare(response, "text/html");
		String variety = request.getParameter("variety");
		String color = request.getParameter("color");
		String choice = request.getParameter("actionToPerform");

		if(variety == null || color == null || choice == null) {
			respond(response,"[HTTPResponse] Required parameter(s) was/were empty. Request not successful.");
		} else if (choice.equalsIgnoreCase("insert")) {
			FruitDB.insertFruit(new Fruit(variety, color));
			respond(response, "[HTTPResponse] Request received. Fruit insertion was successful.");
		} else if (choice.equalsIgnoreCase("delete")) {
			FruitDB.deleteFruit(new Fruit(variety, color));
			respond(response, "[HTTPResponse] Request received. Fruit deletion was successful.");
		} else {
			respond(response, "[HTTPResponse] Error processing your request. Probably wrong parameter for \"choice\". Please retry.");
			respond(response, "[HTTPResponse] Error processing your request. Probably wrong parameter for \"choice\". Please retry.");
		}

	}

	/*
	 * ******************************
	 * Private helper methods below *
	 * ******************************
	 */

	private void respond(HttpServletResponse response, String customText) {
		try {
			response.getWriter().append(customText);
		} catch (IOException e) {
			System.out.println("Response failed due to an IOException.");
		}
	}

	private void prepare(HttpServletResponse response, String contentType) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(contentType);
	}

}
