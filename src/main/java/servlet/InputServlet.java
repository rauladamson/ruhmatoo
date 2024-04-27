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

        Map<String, String[]> inputsMap = request.getParameterMap(); // sisend teisedatakse Mapiks
        ArrayList<Oppeaine> oppeained = new ArrayList<>(); // luuakse uus Õppeaine objektide list
        //HashMap<String, String[]> inputsMap = new PDFPrintTest(null).getInputsMap(); // etapp 1 - vana
        StringBuilder builder = new StringBuilder(); // luuakse StringBuilder objekt

        if (!inputsMap.isEmpty()) { // kui sisend ei ole tühi, siis töödeldakse vastuse sisust asjakohaseid väärtused

            for (String paramName : inputsMap.keySet()) { // sisendväärtuste iteratsioon
                String[] paramValues = inputsMap.get(paramName); // võtamele vastava väärtuse massiv massiiv
                //System.out.println("Parameter name: " + paramName);
                //System.out.println("Parameter values: " + Arrays.toString(paramValues));
                builder.append("Parameter name: ").append(paramName); // vastuse sisule lisatakse võti

                Oppeaine oa = FetchData.fetchAPIData(paramValues[0]); // luuakse uus Õppeaine objekt
                // TODO kontrolli paramValues pikkust - kas on alati ainult üks el?
                builder.append("Parameter name: ").append(oa); // vastuse sisule lisatakse Õppeaine objekt
                oppeained.add(oa); // Õppeaine objekt lisatakse Õppeaine objektide listi
            }

            //HttpSession httpSession = request.getSession();
            String text = builder.toString();

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);

            // TODO salvesta iga massiivi obj faili (KUI uuid hulgas pole): json
            // TODO kirjuta uuid eraldi faili
            // TODO uuid-d kuhugi cahce-i

            /*httpSession.setAttribute("inputsMap", inputsMap);
            request.getRequestDispatcher("index.jsp").forward(request, response);*/
        }
    }
}