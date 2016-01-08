package esse.chat.service;

import static esse.chat.acesso.Papel.ADMINISTRADOR;
import static esse.chat.acesso.Papel.USUARIO;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static javax.ejb.TransactionManagementType.CONTAINER;

import esse.chat.acesso.Grupo;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;


@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@DeclareRoles({ADMINISTRADOR, USUARIO})
public class GrupoServico extends Servico<Grupo> {
    @TransactionAttribute(SUPPORTS)       
    @PermitAll    
    public Grupo getGrupo(String nomeGrupo) {
        return getEntidade(Grupo.GRUPO_POR_NOME, new Object[]{nomeGrupo});
    }
}
