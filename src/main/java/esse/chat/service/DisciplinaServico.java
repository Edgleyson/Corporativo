package esse.chat.service;

import static esse.chat.acesso.Grupo.ADMINISTRADOR;
import esse.chat.excecao.ExcecaoNegocio;
import esse.chat.modelo.Disciplina;
import static esse.chat.modelo.Disciplina.DISCIPLINAS;
import static esse.chat.modelo.Disciplina.DISCIPLINA_POR_NOME;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

public class DisciplinaServico extends Servico<Disciplina>{
    @Resource
    private SessionContext sessionContext;

    public void salvar(Disciplina disciplina) throws ExcecaoNegocio {
        if (sessionContext.isCallerInRole(ADMINISTRADOR)) {
            checarExistencia(DISCIPLINA_POR_NOME, disciplina.getNome());
            entityManager.persist(disciplina);
        } else {
            throw new EJBAccessException();
        }
    }
    
    @RolesAllowed({ADMINISTRADOR})    
    public void atualizar(Disciplina Disciplina) throws ExcecaoNegocio {
        checarNaoExistencia(DISCIPLINA_POR_NOME, new Object[] {Disciplina.getNome()});
        entityManager.merge(Disciplina);
        entityManager.flush();
    }
    
    @RolesAllowed({ADMINISTRADOR})
    public void remover(Disciplina disciplina) throws ExcecaoNegocio {
        disciplina = entityManager.merge(disciplina);
        if (disciplina.isInativo())
            entityManager.remove(disciplina);
        else
            throw new ExcecaoNegocio(ExcecaoNegocio.REMOVER_DISCIPLINA);
    }
    
    @RolesAllowed({ADMINISTRADOR})
    public void remover(String nome) throws ExcecaoNegocio {        
        Disciplina disciplina = getDisciplina(nome);
        remover(disciplina);
    }

    @TransactionAttribute(SUPPORTS)
    public List<Disciplina> getDisciplinas() {
        return getEntidades(DISCIPLINAS);
    }
    
    @TransactionAttribute(SUPPORTS)    
    public Disciplina getDisciplina(String nome) {
        return super.getEntidade(DISCIPLINA_POR_NOME, new Object[]{nome});
    }
    
    @RolesAllowed({ADMINISTRADOR})
    public Disciplina criar() {
        return new Disciplina();
    }
}
