package esse.chat.jsf.beans;

import esse.chat.excecao.ExcecaoNegocio;
import esse.chat.modelo.Curso;
import esse.chat.service.CursoServico;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CursoBean extends Bean<Curso> implements Serializable{
    
    @EJB
    private CursoServico servicoCurso;
    

    @Override
    protected void iniciarCampos() {
        setEntidade(servicoCurso.criar());
    }

    @Override
    protected boolean salvar(Curso curso) throws ExcecaoNegocio {
        servicoCurso.salvar(curso);
        return true;
    }
    
}
