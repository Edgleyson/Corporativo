package esse.chat.service;

import static esse.chat.acesso.Papel.ADMINISTRADOR;
import static esse.chat.modelo.Instituicao.INSTITUICAO_POR_SIGLA;
import static esse.chat.modelo.Instituicao.INSTITUICOES;
import static javax.ejb.TransactionManagementType.CONTAINER;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

import esse.chat.modelo.Instituicao;
import esse.chat.excecao.ExcecaoNegocio;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBAccessException;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;


@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class InstituicaoServico extends Servico<Instituicao> {

    @Resource
    private SessionContext sessionContext;

    public void salvar(Instituicao instituicao) throws ExcecaoNegocio {
        if (sessionContext.isCallerInRole(ADMINISTRADOR)) {
            checarExistencia(INSTITUICAO_POR_SIGLA, instituicao.getSigla());
            entityManager.persist(instituicao);
        } else {
            throw new EJBAccessException();
        }
    }

    @TransactionAttribute(SUPPORTS)
    public List<Instituicao> getInstituicoes() {
        return getEntidades(INSTITUICOES);
    }
    
    @TransactionAttribute(SUPPORTS)    
    public Instituicao getInstituicao(String sigla) {
        return super.getEntidade(INSTITUICAO_POR_SIGLA, new Object[]{sigla});
    }
    
    @TransactionAttribute(SUPPORTS)
    public Instituicao criar() {
        return new Instituicao();
    }
}
