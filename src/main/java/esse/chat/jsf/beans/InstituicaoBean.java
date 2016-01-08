package esse.chat.jsf.beans;

import esse.chat.excecao.ExcecaoNegocio;
import esse.chat.modelo.Instituicao;
import esse.chat.service.InstituicaoServico;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class InstituicaoBean extends Bean<Instituicao> implements Serializable{
    
    @EJB
    private InstituicaoServico servicoInstituicao;
    

    @Override
    protected void iniciarCampos() {
        setEntidade(servicoInstituicao.criar());
    }

    @Override
    protected boolean salvar(Instituicao instituicao) throws ExcecaoNegocio {
        servicoInstituicao.salvar(instituicao);
        return true;
    }
    
}
