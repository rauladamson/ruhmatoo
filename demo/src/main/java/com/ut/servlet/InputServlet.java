package com.ut.servlet;

import java.util.Map;
import java.util.Arrays;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/inputServlet")
public class InputServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public InputServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String[]> inputsMap = request.getParameterMap();

        

        if (inputsMap.size() != 0) {
            for (String paramName : inputsMap.keySet()) {
                String[] paramValues = inputsMap.get(paramName);
                System.out.println("Parameter name: " + paramName);
                System.out.println("Parameter values: " + Arrays.toString(paramValues));
            }

            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("inputsMap", inputsMap);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } 

    }
}