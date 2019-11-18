package modulos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import util.Validador;

/**
 * Representação de Pesquisa no Sistema
 */
public class Pesquisa {

	private String descricao;
	private String camposInteresse[];
	private String codigo;
	private boolean ativa;
	private HashMap<String,Objetivo> objetivos;
	private Optional<Problema> problemaOptional;
	/**
	 * Mapa de atividade.
	 */
	private Map<String, Atividade> atividades;

	/**
	 * 
	 * @param descricao
	 * @param camposInteresse
	 */
	public Pesquisa(String descricao, String camposInteresse) {

		this.camposInteresse = new String[4];
		this.gerarCamposInteresse(camposInteresse);
		this.descricao = descricao;
		this.ativa = true;
		this.atividades = new HashMap<>();
		this.objetivos = new HashMap<>();
		this.problemaOptional = Optional.empty();

	}

	/**
	 * Adiciona uma atividade.
	 * 
	 * @param codigoAtividade Código da atividade.
	 * @param atividade       Atividade a ser adicionada.
	 * @return "True" se a adição for bem sucedida e "False" se a adição não
	 *         acontecer.
	 */
	public boolean addAtividade(String codigoAtividade, Atividade atividade) {
		if (!atividades.containsKey(codigoAtividade)) {
			this.atividades.put(codigoAtividade, atividade);
			this.atividades.get(codigoAtividade).setEhAssociada();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Remove uma atividade.
	 * 
	 * @param codigoAtividade Código da atividade.
	 * @return "True" se a remoção for bem sucedida e "False" se a remocão não
	 *         acontecer.
	 */
	public boolean removeAtividade(String codigoAtividade) {
		if (atividades.get(codigoAtividade) != null) {
			this.atividades.get(codigoAtividade).setNaoAssociada();
			this.atividades.remove(codigoAtividade);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @param camposString
	 */
	private void gerarCamposInteresse(String camposString) {

		String[] interesses = camposString.split(",");

		if (interesses.length <= this.camposInteresse.length) {

			for (int i = 0; i < interesses.length; i++) {

				final String interesse = interesses[i];

				new Validador().valida(interesse, "Formato do campo de interesse invalido.");
				this.camposInteresse[i] = interesses[i].trim();
			}
		} else {
			throw new IllegalArgumentException("Formato do campo de interesse invalido.");
		}

	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param camposInteresse the camposInteresse to set
	 */
	public void setCamposInteresse(String camposInteresse) {
		this.gerarCamposInteresse(camposInteresse);
	}

	/**
	 * @param ativa the ativa to set
	 */
	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	@Override
	public String toString() {
		StringBuilder sbCamposInteresse = new StringBuilder();

		for (String string : camposInteresse) {
			if (string != null)
				sbCamposInteresse.append(string + ", ");
		}

		String camposInteresseString = sbCamposInteresse.toString();
		camposInteresseString = camposInteresseString.substring(0, camposInteresseString.length() - 2);

		return String.format("%s - %s - %s", this.codigo, this.descricao, camposInteresseString);
	}
	
	
	/**
	 * toString usado para exibir o resultado de busca
	 * 
	 * @return {@link String}
	 */
	public String toStringBusca() {
			
		StringBuilder sbCamposInteresse = new StringBuilder();

		for (String string : camposInteresse) {
			if (string != null)
				sbCamposInteresse.append(string + ", ");
		}

		String camposInteresseString = sbCamposInteresse.toString();
		camposInteresseString = camposInteresseString.substring(0, camposInteresseString.length() - 2);

		return String.format("%s: %s - %s | ",this.codigo, this.descricao, camposInteresseString);
	}

	/***
	 * 
	 * @param novoConteudo
	 */
	public void setDescricao(String novoConteudo) {
		this.descricao = novoConteudo;
	}

	/**
	 * 
	 * @return void
	 */
	public boolean getAtiva() {
		return this.ativa;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setAssociada(String codigoAtividade) {
		this.atividades.get(codigoAtividade).setEhAssociada();
	}

	public void setNaoAssociada(String codigoAtividade) {
		this.atividades.get(codigoAtividade).setNaoAssociada();
	}

	public void setCodigo(String codigoPesquisa) {
		this.codigo = codigoPesquisa;
	}
	public boolean associaProblema(Problema problema) {
		boolean retorno = false;
		if (!this.problemaOptional.isPresent()) {
			this.problemaOptional = Optional.ofNullable(problema);
			retorno = true;

		} else if (!this.problemaOptional.get().equals(problema)) {
			throw new IllegalArgumentException("Pesquisa ja associada a um problema.");
		}
		return retorno;
	}

	public boolean desassociaProblema() {
		boolean retorno = false;
		if(this.problemaOptional.isPresent()) {
			this.problemaOptional = Optional.empty();
			retorno = true;
		}
		return retorno;
	}

	public boolean associaObjetivo(Objetivo objetivo, String idObjetivo) {
		boolean retorno = false;
		if (!this.objetivos.containsKey(idObjetivo)) {
			if (objetivo.isAssociado()) {
				throw new IllegalArgumentException("Objetivo ja associado a uma pesquisa.");
			}
			this.objetivos.put(idObjetivo, objetivo);
			this.objetivos.get(idObjetivo).setAssociado(true);
			retorno = true;
		}
		return retorno;
	}

	public boolean desassociaObjetivo(String idObjetivo) {
		boolean retorno = false;
		if (this.objetivos.containsKey(idObjetivo)) {
			this.objetivos.get(idObjetivo).setAssociado(false);
			this.objetivos.remove(idObjetivo);
			retorno = true;
		}
		return retorno;
	}

	public Optional<Problema> getProblema() {
		return this.problemaOptional;
	}

	public int getQuantiadeDeObjetivos(){
		return this.objetivos.size();
	}

	public String maiorId() {
		String variavelId = "";
		for (String id : objetivos.keySet()) {
			if (id == "") {
				variavelId = id;
			} else {
				if (variavelId.compareTo(id) < 0){
					variavelId = id;
				}
			}
		}
		return variavelId;
	}
	
	/**
	 * Metodo retorna todas as atividade da pesquisa
	 * 
	 * @return List<Atividade> 
	 * */
	public List<Atividade> getAtividades() {
		
		List<Atividade> atividades = new ArrayList<>();
		
		for(String key: this.atividades.keySet()) {
			atividades.add(this.atividades.get(key));
		}
		
		return atividades;
	}
	
	/**
	 * Método retorna uma lista de Objetivos associados à uma pesquisa
	 * 
	 * @return List<Objetivo>
	 * */
	public List<Objetivo> getObjetivos() {
		
		List<Objetivo> objetivos = new ArrayList<>();
    	
    	for(String key: this.objetivos.keySet())
    		objetivos.add(this.objetivos.get(key));
    	
    	return objetivos; 
	}
	

}
