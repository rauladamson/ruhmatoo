package com.ut.servlet;

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

        // We can able to get the form data by means of the below ways.
        // Form arguments should be matched and then only they are recognised
        String userName = request.getParameter("userName");
        String yourEmailID = request.getParameter("yourEmailID");
        String yourPassword = request.getParameter("yourPassword");
        String gender = request.getParameter("gender");
        String favoriteLanguage = request.getParameter("favoriteLanguage");
        System.out.println("gender.." + gender);
        System.out.println("favoriteLanguage.." + favoriteLanguage);

        // Here the business validations goes. As a sample,
        // we can check against a hardcoded value or pass the
        // values into a database which can be available in local/remote  db
        // For easier way, let us check against a hardcoded value
        if (userName != null && yourEmailID != null &&  yourPassword != null ) {
            // We can redirect the page to a welcome page
            // Need to pass the values in session in order
            // to carry forward that one to next pages
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("emailId", yourEmailID);
            httpSession.setAttribute("gender", gender);
            httpSession.setAttribute("favoriteLanguage", favoriteLanguage);
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
        }
    }
}