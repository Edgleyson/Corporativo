package esse.chat.webservice;

import esse.chat.excecao.ExcecaoNegocio;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import esse.chat.interceptador.LoginInterceptador;
import esse.chat.interceptador.ValidadorEntidadeInterceptador;
import esse.chat.modelo.Curso;
import esse.chat.service.CursoServico;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Path("curso")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors({LoginInterceptador.class})
public class CursoWebService extends WebService<Curso> {
    @EJB
    private CursoServico cursoServico;

    @GET
    @Path("get")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response getCurso(@QueryParam("nome") String nome,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Curso curso = cursoServico.getCurso(nome);
        return super.getResposta(curso);
    }

    @GET
    @Path("get/cursos")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response getCurso(@Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        List<Curso> curso = cursoServico.getCursos();
        return getRespostaLista(curso);
    }

    @Override
    protected GenericEntity<List<Curso>> getListaGenerica(List<Curso> entidades) {
        return new GenericEntity<List<Curso>>(entidades){};
    }    
    
    @POST
    @Path("salvar")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Interceptors({ValidadorEntidadeInterceptador.class})
    public Response salvarCurso(Curso curso,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        cursoServico.salvar(curso);
        return super.getRespostaSucesso();
    }

    @PUT
    @Path("atualizar")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Interceptors({ValidadorEntidadeInterceptador.class})    
    public Response atualizarCurso(Curso curso,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {        
        cursoServico.atualizar(curso);
        return super.getRespostaSucesso();
    }

    @DELETE
    @Path("remover/{nome}")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response removerCurso(@PathParam("nome") String nome,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        cursoServico.remover(nome);
        return super.getRespostaSucesso();
    }
    
}
