package esse.chat.excecao;

import javax.ejb.ApplicationException;


@ApplicationException(rollback = true)
public class ExcecaoNegocio extends Exception {
    public static final String OBJETO_INEXISTENTE = "excecao.ExcecaoNegocio.objetoInexistente";
    private String chave;
    public static final String REMOVER_ALUNO = "excecao.ExcecaoNegocio.alunoServico.remover";
    public static final String REMOVER_ADMINISTRADOR = "excecao.ExcecaoNegocio.administradorServico.remover";
    public static final String REMOVER_PROFESSOR = "excecao.ExcecaoNegocio.administradorServico.remover";
    public static final String REMOVER_CURSO = "excecao.ExcecaoNegocio.cursoServico.remover";
    public static final String REMOVER_DISCIPLINA = "excecao.ExcecaoNegocio.disciplinaServico.remover";
    public static final String OBJETO_EXISTENTE = "excecao.ExcecaoNegocio.objetoExistente";  
    public static final String ACESSO_NAO_AUTORIZADO = "excecao.ExcecaoNegocio.acesso.nao.autorizado";
    public static final String CREDENCIAIS_OMITIDAS = "excecao.ExcecaoNegocio.acesso.credenciais.omitidadas";
    public static final String LOGIN_INVALIDO = "excecao.ExcecaoNegocio.acesso.login.invalido";    
    
    public ExcecaoNegocio(String chave) {
        this.chave = chave;
    }  

    public String getChave() {
        return chave;
    }
    
    @Override
    public String getMessage() {
        MensagemExcecao mensagemExcecao = new MensagemExcecao(this);
        return mensagemExcecao.getMensagem();
    }
    
    public boolean isAutorizacao() {
        switch(chave) {
            case ACESSO_NAO_AUTORIZADO:
            case CREDENCIAIS_OMITIDAS:
            case LOGIN_INVALIDO:
                return true;
            default:
                return false;
        }
    }
}
