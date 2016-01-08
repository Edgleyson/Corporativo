package esse.chat.webservice;

import esse.chat.excecao.ExcecaoNegocio;
import esse.chat.interceptador.ValidadorEntidadeInterceptador;
import esse.chat.modelo.Curso;
import esse.chat.modelo.Disciplina;
import esse.chat.service.CursoServico;
import esse.chat.service.DisciplinaServico;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import javax.ws.rs.core.Response;


@Stateless
@Path("esse.chat.modelo.disciplina")
public class DisciplinaWebService extends WebService<Disciplina> {
    @EJB
    private DisciplinaServico disciplinaServico;

    @GET
    @Path("get")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response getDisciplina(@QueryParam("nome") String nome,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Disciplina disciplina = disciplinaServico.getDisciplina(nome);
        return super.getResposta(disciplina);
    }

    @GET
    @Path("get/disciplinas")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response getDisciplina(@Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        List<Disciplina> disciplina = disciplinaServico.getDisciplinas();
        return getRespostaLista(disciplina);
    }

    @Override
    protected GenericEntity<List<Disciplina>> getListaGenerica(List<Disciplina> entidades) {
        return new GenericEntity<List<Disciplina>>(entidades){};
    }    
    
    @POST
    @Path("salvar")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Interceptors({ValidadorEntidadeInterceptador.class})
    public Response salvarDisciplina(Disciplina disciplina,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        disciplinaServico.salvar(disciplina);
        return super.getRespostaSucesso();
    }

    @PUT
    @Path("atualizar")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Interceptors({ValidadorEntidadeInterceptador.class})    
    public Response atualizarDisciplina(Disciplina disciplina,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {        
        disciplinaServico.atualizar(disciplina);
        return super.getRespostaSucesso();
    }

    @DELETE
    @Path("remover/{nome}")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response removerDisciplina(@PathParam("nome") String nome,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        disciplinaServico.remover(nome);
        return super.getRespostaSucesso();
    }
    
}
