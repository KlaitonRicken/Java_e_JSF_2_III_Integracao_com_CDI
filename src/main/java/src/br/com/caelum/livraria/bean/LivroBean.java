package src.br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import src.br.com.caelum.livraria.dao.AutorDAO;
import src.br.com.caelum.livraria.dao.LivroDao;
import src.br.com.caelum.livraria.modelo.Autor;
import src.br.com.caelum.livraria.modelo.Livro;
import src.br.com.caelum.livraria.tx.Log;
import src.br.com.caelum.livraria.tx.Transacional;
import src.br.com.caelum.livraria.util.ForwardView;
import src.br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped //javax.faces.view.ViewScoped
public class LivroBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;
	private List<Livro> livros;
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");
	
	@Inject
	private LivroDao livroDao;
	
	@Inject
	AutorDAO autorDao;
	
	@Inject
	FacesContext context;

	public List<String> getGeneros() {
		return generos;
	}

	public void setGeneros(List<String> generos) {
		this.generos = generos;
	}

	public Livro getLivro() {
		return livro;
	}
	
	public Integer getLivroId() {
        return livroId;
    }
	
	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregaPelaId() {
		this.livro = this.livroDao.buscaPorId(this.livroId);
	}

	@Log
	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			context.addMessage("autor", new FacesMessage("Livro deve ter pelo menos um autor!"));
			return;
		}

		if(this.livro.getId() == null){
			livroDao.adiciona(this.livro);
		}else{
			System.out.println("Gravando alteração");
			this.livroDao.atualiza(this.livro);
		}
		
		this.livros = livroDao.listaTodos();
		
		this.livro = new Livro();
	}
	
	@Log
	@Transacional
	public void removerAutorDoLivro(Autor autor){
		this.livro.removeAutor(autor);
	}
	
	public List<Autor> getAutores(){
		return autorDao.listaTodos();
	}
	
	@Log
	@Transacional
	public void gravarAutor(){
		Autor autor  = autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}
	
	@Transacional
	public void remover(Livro livro){
		livroDao.remove(livro);
		this.livros = livroDao.listaTodos();
	}
	
	@Log
	@Transacional
	public void update(Livro livro){
		this.livro = this.livroDao.buscaPorId(livro.getId());
	}
	
	public ForwardView formAutor(){
		return new ForwardView(new RedirectView("autor").toString());
	}
	
	public ForwardView formCarosel(){
		return new ForwardView(new RedirectView("carosel").toString());
	}
	
	public ForwardView formLivro(){
		return new ForwardView(new RedirectView("livro").toString());
	}
	
	public ForwardView formRing(){
		return new ForwardView(new RedirectView("ring").toString());
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}
	
	public List<Livro> getLivros(){
		if(this.livros == null)
			this.livros = livroDao.listaTodos();
		return livros;
	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException{
		String valor = value.toString();
		if(!valor.startsWith("1")){
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
	}
	
	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) { // java.util.Locale

        //tirando espaços do filtro
        String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

        // o filtro é nulo ou vazio?
        if (textoDigitado == null || textoDigitado.equals("")) {
            return true;
        }

        // elemento da tabela é nulo?
        if (valorColuna == null) {
            return false;
        }

        try {
            // fazendo o parsing do filtro para converter para Double
            Double precoDigitado = Double.valueOf(textoDigitado);
            Double precoColuna = (Double) valorColuna;

            // comparando os valores, compareTo devolve um valor negativo se o value é menor do que o filtro
            return precoColuna.compareTo(precoDigitado) < 0;

        } catch (NumberFormatException e) {

            // usuario nao digitou um numero
            return false;
        }
}
}
