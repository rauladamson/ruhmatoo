package servlet;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Map;
import java.util.Arrays;
//import java.util.HashMap;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

//import pdf.PDFPrintTest;
import pdfsave.FetchData;
import oppeaine.Oppeaine;

@WebServlet("/inputServlet")
public class InputServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public InputServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String[]> inputsMap = request.getParameterMap();

        ArrayList<Oppeaine> oppeained = new ArrayList<>();
        //HashMap<String, String[]> inputsMap = new PDFPrintTest(null).getInputsMap();
        StringBuilder builder = new StringBuilder();

        if (!inputsMap.isEmpty()) {

            for (String paramName : inputsMap.keySet()) {
                String[] paramValues = inputsMap.get(paramName);
                //System.out.println("Parameter name: " + paramName);
                //System.out.println("Parameter values: " + Arrays.toString(paramValues));
                builder.append("Parameter name: ").append(paramName);

                Oppeaine oa = FetchData.fetchAPIData(paramValues[0]); // luuaks euus Õppeaine objekt
                builder.append("Parameter name: ").append(oa);
                oppeained.add(oa);
            }



            //HttpSession httpSession = request.getSession();
            String text = builder.toString();

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);

            
            /*httpSession.setAttribute("inputsMap", inputsMap);
            request.getRequestDispatcher("index.jsp").forward(request, response);*/

        }

    }
}