package esse.chat.jsf.beans;

import esse.chat.modelo.Curso;
import esse.chat.modelo.Disciplina;
import esse.chat.modelo.Instituicao;
import esse.chat.modelo.Usuario;
import esse.chat.service.CursoServico;
import esse.chat.service.DisciplinaServico;
import esse.chat.service.InstituicaoServico;
import esse.chat.service.UsuarioServico;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "paginator")
@ViewScoped
public class PaginatorBean implements Serializable {

    @EJB
    private UsuarioServico usuarioServico;
    @EJB
    private InstituicaoServico instituicaoServico;
    @EJB
    private CursoServico cursoServico;
    @EJB
    private DisciplinaServico disciplinaServico;

    private List<Usuario> usuarios;
    private List<Instituicao> instituicoes;
    private List<Curso> cursos;
    private List<Disciplina> disciplinas;

    public List<Usuario> getUsuarios() {
        if (usuarios == null) {
            usuarios = usuarioServico.getUsuarios();
        }
        return usuarios;
    }
    
        
    public List<Instituicao> getInstituicoes() {
        if (instituicoes == null) {
            instituicoes = instituicaoServico.getInstituicoes();
        }
        return instituicoes;
    }

    public List<Curso> getCursos() {
        if (cursos == null) {
            cursos = cursoServico.getCursos();
        }
        return cursos;
    }
    
    public List<Disciplina> getDisciplinas() {
        if (disciplinas == null) {
            disciplinas = disciplinaServico.getDisciplinas();
        }
        return disciplinas;
    }
    
    
}
