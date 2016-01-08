package esse.chat.service;

import static esse.chat.acesso.Grupo.ADMINISTRADOR;
import esse.chat.excecao.ExcecaoNegocio;
import esse.chat.modelo.Curso;
import static esse.chat.modelo.Curso.CURSOS;
import static esse.chat.modelo.Curso.CURSO_POR_NOME;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

public class CursoServico extends Servico<Curso>{
    @Resource
    private SessionContext sessionContext;

    public void salvar(Curso curso) throws ExcecaoNegocio {
        if (sessionContext.isCallerInRole(ADMINISTRADOR)) {
            checarExistencia(CURSO_POR_NOME, curso.getNome());
            entityManager.persist(curso);
        } else {
            throw new EJBAccessException();
        }
    }
    
    @RolesAllowed({ADMINISTRADOR})    
    public void atualizar(Curso curso) throws ExcecaoNegocio {
        checarNaoExistencia(CURSO_POR_NOME, new Object[] {curso.getNome()});
        entityManager.merge(curso);
        entityManager.flush();
    }
    
    @RolesAllowed({ADMINISTRADOR})
    public void remover(Curso curso) throws ExcecaoNegocio {
        curso = entityManager.merge(curso);
        if (curso.isInativo())
            entityManager.remove(curso);
        else
            throw new ExcecaoNegocio(ExcecaoNegocio.REMOVER_CURSO);
    }
    
    @RolesAllowed({ADMINISTRADOR})
    public void remover(String nome) throws ExcecaoNegocio {        
        Curso curso = getCurso(nome);
        remover(curso);
    }

    @TransactionAttribute(SUPPORTS)
    public List<Curso> getCursos() {
        return getEntidades(CURSOS);
    }
    
    @TransactionAttribute(SUPPORTS)    
    public Curso getCurso(String nome) {
        return super.getEntidade(CURSO_POR_NOME, new Object[]{nome});
    }
    
    @RolesAllowed({ADMINISTRADOR})
    public Curso criar() {
        return new Curso();
    }
}
