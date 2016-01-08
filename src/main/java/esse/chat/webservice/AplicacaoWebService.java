package esse.chat.webservice;

import esse.chat.webservice.excecao.MapeadorExcecao;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import esse.chat.webservice.conversor.GsonGenericListWriter;
import esse.chat.webservice.conversor.GsonReader;
import esse.chat.webservice.conversor.GsonWriter;


public class AplicacaoWebService extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(InstituicaoWebService.class);
        classes.add(MapeadorExcecao.class);
        classes.add(GsonWriter.class);
        classes.add(GsonReader.class);
        classes.add(GsonGenericListWriter.class);
        return classes;
    }
}
