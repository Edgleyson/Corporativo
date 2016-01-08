package esse.chat.webservice;

import esse.chat.modelo.Instituicao;
import esse.chat.interceptador.LoginInterceptador;
import esse.chat.service.InstituicaoServico;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import javax.ws.rs.core.Response;

@Path("instituicao")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors({LoginInterceptador.class})
public class InstituicaoWebService extends WebService<Instituicao> {

    @EJB
    private InstituicaoServico instituicaoService;

    @GET
    @Path("get/sigla")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response getinstituicao(@QueryParam("sigla") String sigla,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Instituicao instituicao = instituicaoService.getInstituicao(sigla);
        return super.getResposta(instituicao);
    }

}
