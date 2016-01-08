package esse.chat.jsf.beans;


import esse.chat.excecao.ExcecaoNegocio;
import esse.chat.modelo.Usuario;
import esse.chat.service.UsuarioServico;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean(name = "usuarioBean")
@RequestScoped
public class UsuarioBean extends Bean<Usuario> implements Serializable {

    @EJB
    private UsuarioServico servicoUsuario;
    
    public UsuarioBean() {
        this.entidade = new Usuario() {};
    }
    
    private boolean sucesso = true;

    @Override
    protected void iniciarCampos() {
        if (sucesso) {
            this.entidade = new Usuario();
        }
    }    

    @Override
    protected boolean salvar(Usuario usuario) throws ExcecaoNegocio {
        servicoUsuario.salvar(usuario);
        return true;
    }
}
