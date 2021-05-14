package src.br.com.caelum.livraria.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import src.br.com.caelum.livraria.util.ForwardView;
import src.br.com.caelum.livraria.util.RedirectView;
import src.br.com.caelum.livraria.dao.UsuarioDAO;
import src.br.com.caelum.livraria.modelo.Usuario;

@Named
@ViewScoped
public class LoginBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	
	@Inject
	UsuarioDAO userDao;
	@Inject
	FacesContext context;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public ForwardView formLogin(){
		return new ForwardView(new RedirectView("login").toString());
	}
	
	public ForwardView efetuaLogin(){
		
		boolean existe = this.userDao.existe(this.usuario);
		
		if(existe){
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return new ForwardView(new RedirectView("livro").toString());
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));
		
		return new ForwardView(new RedirectView("login").toString());
		
	}
	
	public ForwardView deslogar(){
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		return new ForwardView(new RedirectView("login").toString());
	}
	
}