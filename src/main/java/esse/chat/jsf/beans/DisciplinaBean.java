package esse.chat.jsf.beans;

import esse.chat.excecao.ExcecaoNegocio;
import esse.chat.modelo.Curso;
import esse.chat.modelo.Disciplina;
import esse.chat.service.CursoServico;
import esse.chat.service.DisciplinaServico;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DisciplinaBean extends Bean<Disciplina> implements Serializable{
    
    @EJB
    private DisciplinaServico servicoDisciplina;
    

    @Override
    protected void iniciarCampos() {
        setEntidade(servicoDisciplina.criar());
    }

    @Override
    protected boolean salvar(Disciplina disciplina) throws ExcecaoNegocio {
        servicoDisciplina.salvar(disciplina);
        return true;
    }
    
}
