package esse.chat.interceptador;

import esse.chat.acesso.Papel;
import esse.chat.excecao.ExcecaoNegocio;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;


public class LoginInterceptador {
    
    @Resource
    private SessionContext contexto;

    private HttpServletRequest getHttpServletRequest(InvocationContext ic) {
        HttpServletRequest request = null;
        for (Object parameter : ic.getParameters()) {
            if (parameter instanceof HttpServletRequest) {
                request = (HttpServletRequest) parameter;
            }
        }

        return request;
    }

    private HttpHeaders getHttpHeaders(InvocationContext ic) {
        HttpHeaders headers = null;

        for (Object parameter : ic.getParameters()) {
            if (parameter instanceof HttpHeaders) {
                headers = (HttpHeaders) parameter;
            }
        }

        return headers;
    }    
    
    private boolean valido(String valor) {
        return valor != null && valor.trim().length() > 0;
    }

    @AroundInvoke
    public Object interceptar(InvocationContext ic) throws Exception {
        Object resultado;
        HttpServletRequest request = null;
        HttpHeaders headers;

        try {
            request = getHttpServletRequest(ic);
            headers = getHttpHeaders(ic);

            String login = headers.getHeaderString("login");
            String senha = headers.getHeaderString("senha");

            if (valido(login) && valido(senha)) {
                try {
                    request.login(login, senha);
                    request.getSession(true);

                    if (contexto.isCallerInRole(Papel.ADMINISTRADOR)) {
                        resultado = ic.proceed();
                    } else {
                        throw new ExcecaoNegocio(ExcecaoNegocio.ACESSO_NAO_AUTORIZADO);
                    }

                } catch (ServletException ex) {
                    throw new ExcecaoNegocio(ExcecaoNegocio.LOGIN_INVALIDO);
                }
            } else {
                throw new ExcecaoNegocio(ExcecaoNegocio.CREDENCIAIS_OMITIDAS);
            }
        } finally {
            if (request != null) {
                if (request.getSession(false) != null) {
                    request.getSession(false).invalidate();
                }

                request.logout();
            }
        }

        return resultado;
    }
}
