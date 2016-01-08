package esse.chat.service;

import static javax.ejb.TransactionManagementType.CONTAINER;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static esse.chat.modelo.Usuario.USUARIO_POR_LOGIN;
import static esse.chat.acesso.Grupo.USUARIO;

import esse.chat.modelo.Usuario;
import esse.chat.excecao.ExcecaoNegocio;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import javax.ejb.TransactionManagement;


@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class UsuarioServico extends Servico<Usuario> {
    @EJB
    private GrupoServico grupoService;
    @EJB
    private EmailServico emailService;

    public void salvar(Usuario usuario) throws ExcecaoNegocio {
        checarExistencia(USUARIO_POR_LOGIN, usuario.getApelido());
        usuario.adicionarGrupo(grupoService.getGrupo(USUARIO));
        entityManager.persist(usuario);
        emailService.enviarMensagem(usuario.getEmail());
    }
    
    @TransactionAttribute(SUPPORTS)   
    @PermitAll
    public List<Usuario> getUsuarios() {
        return getEntidades(Usuario.USUARIOS);
    }
}
