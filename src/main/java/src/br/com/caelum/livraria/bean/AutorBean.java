package src.br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import src.br.com.caelum.livraria.dao.AutorDAO;
import src.br.com.caelum.livraria.modelo.Autor;
import src.br.com.caelum.livraria.tx.Log;
import src.br.com.caelum.livraria.tx.Transacional;
import src.br.com.caelum.livraria.util.ForwardView;
import src.br.com.caelum.livraria.util.RedirectView;

@Named
@javax.faces.view.ViewScoped
public class AutorBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Autor autor = new Autor();
	private Integer autorId;
	
	@Inject
	private AutorDAO dao;
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Autor getAutor() {
		return autor;
	}
	
	public List<Autor> getAutores(){
		return this.dao.listaTodos();
	}
	
	@Log
	@Transacional
	public void remover(Autor autor){
		System.out.println("Removendo Autor");
		this.dao.remove(autor);
	}

	@Log
	@Transacional
	public void update(Autor autor){
		this.autor = autor;
	}
	
	public void carregaAutorPorId(){
		this.autor = this.dao.buscaPorId(autorId);
	}

	@Log
	@Transacional
	public ForwardView gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if(this.autor.getId() == null)
			this.dao.adiciona(this.autor);
		else
			this.dao.atualiza(this.autor);
		
		this.autor = new Autor();
		
//		return "livro?faces-redirect=true";
		 return new ForwardView(new RedirectView("livro").toString());
	}
	
	public ForwardView formLivro(){
		return new ForwardView(new RedirectView("livro").toString());
	}
}
