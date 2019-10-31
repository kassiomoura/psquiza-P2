package controladores;

import java.util.HashMap;

import modulos.Pesquisa;
import util.Validador;

public class ControllerPesquisa {

    /**
     * Verificador das entradas para o tratamento.
     *
     */
    private Validador validador;

    /**
     * Mapa onde os pesquisas serão armazenados para as operações do sistema.
     * 
     */
    private HashMap<String, Pesquisa> pesquisas;

    /**
     * Construtor do mapa e do verificador do validador.
     */
    public ControllerPesquisa() {
        this.validador = new Validador();
        this.pesquisas = new HashMap<String, Pesquisa>();
    }

    /**
	 * Método das pesquisas a serem cadastrados
	 * @author adyssonfs 
	 * @param descricao: resumo descritivo da pesquisa
	 * @param campoDeInteresse: areas que são abrangidas pela pesquisa. 
	 * A entrada deve ser até 255 caracteres. Cada area é separada por vírgula  
	 * */
    public String cadastraPesquisa(String descricao, String campoDeInteresse) {

        this.validador.valida(descricao, "Descricao nao pode ser nula ou vazia.");
        this.validador.validaTamanhoEntrada(campoDeInteresse, "Formato do campo de interesse invalido.");

        Pesquisa pesquisa = new Pesquisa(descricao, campoDeInteresse);
        pesquisa.geraCodigo(pesquisas);

        String codigoPesquisa = pesquisa.getCodigo();
        this.pesquisas.put(codigoPesquisa, pesquisa);
        return codigoPesquisa;
    }

    /**
	 * Método usado para a alteração em pesquisa
	 * @author adyssonfs 
	 * @param codigo: identificador da pesquisa
	 * @param campoASerAlterado: pode ser "CAMPO" ou "DESCRICAO"
	 * */
    public void alteraPesquisa(String codigo, String conteudoASerAlterado, String novoConteudo) {

        this.validador.validaAtributo(conteudoASerAlterado, "Nao e possivel alterar esse valor de pesquisa.");

        if (!pesquisas.containsKey(codigo))
            throw new IllegalArgumentException("Pesquisa nao encontrada.");

        Pesquisa pesquisa = this.pesquisas.get(codigo);

        if (!pesquisa.getAtiva())
            throw new Error("Pesquisa desativada.");

        if (conteudoASerAlterado.equals("CAMPO")) {
            this.validador.valida(novoConteudo, "Formato do campo de interesse invalido.");
            pesquisa.setCamposInteresse(novoConteudo);
        } else {
            this.validador.valida(novoConteudo, "Descricao nao pode ser nula ou vazia.");
            pesquisa.setDescricao(novoConteudo);
        }
    }

    /**
	 * Método para efetuar o encerramento de uma pesquisa
	 * @author adyssonfs 
	 * @param codigo: identificador da pesquisa
	 * @param motivo: motivação para o encerramento da pesquisa"
	 * */
    public void encerraPesquisa(String codigo, String motivo) {

        this.validador.valida(motivo, "Motivo nao pode ser nulo ou vazio.");

        if (!pesquisas.containsKey(codigo))
            throw new Error("Pesquisa nao encontrada.");

        Pesquisa pesquisa = this.pesquisas.get(codigo);

        if (!pesquisa.getAtiva())
            throw new Error("Pesquisa desativada.");

        pesquisa.setAtiva(false);
    }

	/**
	 * Método passa configurar a ativação da pesquisa
	 * @author adyssonfs 
	 * @param codigo: identificador da pesquisa
	 * */
    public void ativaPesquisa(String codigo) {

        if (!pesquisas.containsKey(codigo))
            throw new IllegalArgumentException("Pesquisa nao encontrada.");

        Pesquisa pesquisa = this.pesquisas.get(codigo);

        if (pesquisa.getAtiva())
            throw new Error("Pesquisa ja ativada.");

        pesquisa.setAtiva(true);
    }

    /**
	 * Método faz a exibição da pesquisa no formato
     * "CODIGO - Descricao - ComposDeInteresse"
	 * @author adyssonfs 
	 * @param codigo: identificador da pesquisa
	 * */
    public String exibePesquisa(String codigo) {

        this.validador.valida(codigo, "Codigo nao pode ser nulo ou vazio.");

        if (!pesquisas.containsKey(codigo))
            throw new IllegalArgumentException("Pesquisa nao encontrada.");

        Pesquisa pesquisa = this.pesquisas.get(codigo);
        return pesquisa.toString();
    }

    /**
	 * Método para a verificação da atividade da pesquisa
	 * @author adyssonfs 
	 * @param codigo: identificador da pesquisa
	 * */
    public boolean pesquisaEhAtiva(String codigo) {

        this.validador.valida(codigo, "Codigo nao pode ser nulo ou vazio.");

        if (!pesquisas.containsKey(codigo))
            throw new IllegalArgumentException("Pesquisa nao encontrada.");

        Pesquisa pesquisa = this.pesquisas.get(codigo);
        return pesquisa.getAtiva();
    }

}