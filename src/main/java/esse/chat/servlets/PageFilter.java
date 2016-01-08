package esse.chat.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PageFilter implements Filter {

    @Override
    public void destroy() {
         // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        //recupera a Sessão atual ou cria uma nova caso não exista
        HttpSession sess = ((HttpServletRequest) request).getSession(true);

        //captura a página atual que está sendo acessada
        String newCurrentPage = ((HttpServletRequest) request).getServletPath();

        //verifica se não existe uma página atual gravada na sessão, 
        //caso isso seja verdade então sabemos que é o primeiro acesso do usuário. 
        //Neste caso iremos gravamos a última página e a página atual como sendo as mesmas
        if (sess.getAttribute("currentPage") == null) {
            sess.setAttribute("lastPage", newCurrentPage);
            sess.setAttribute("currentPage", newCurrentPage);
        } else {

            String oldCurrentPage = sess.getAttribute("currentPage").toString();
            if (!oldCurrentPage.equals(newCurrentPage)) {
                sess.setAttribute("lastPage", oldCurrentPage);
                sess.setAttribute("currentPage", newCurrentPage);
            }
        }

        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
         // TODO Auto-generated method stub

    }

}
