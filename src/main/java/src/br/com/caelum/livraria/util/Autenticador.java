package src.br.com.caelum.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import src.br.com.caelum.livraria.modelo.Usuario;

public class Autenticador implements PhaseListener{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void afterPhase(PhaseEvent event) {
		
		FacesContext context = event.getFacesContext();
		String page = context.getViewRoot().getViewId();
		
		System.out.println(page);
		
		if("/login.xhtml".equals(page) || "/carosel.xhtml".equals(page))
			return;
		
		Usuario usuarioLogado = (Usuario)context.getExternalContext().getSessionMap().get("usuarioLogado");
		if(usuarioLogado != null){
			return;
		}
		
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "/login?faces-redirect=true");
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
