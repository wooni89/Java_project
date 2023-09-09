package bank_project.myapp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/*")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageControllerPath = request.getPathInfo();

        response.setContentType("text/html;charset=UTF-8");

        request.getRequestDispatcher(pageControllerPath).include(request, response);

        Exception exception = (Exception) request.getAttribute("exception");
        if (exception != null) {
            throw new ServletException(exception);
        }

        String viewUrl = (String) request.getAttribute("viewUrl");
        if (viewUrl.startsWith("redirect:")) {
            response.sendRedirect(viewUrl.substring(9));
        } else {
            request.getRequestDispatcher(viewUrl).include(request, response);
        }
    }
}
