package br.com.equipe.joinsocial.controll.utilitarios;

import br.com.equipe.joinsocial.controll.excecoes.EmailVazioException;
import br.com.equipe.joinsocial.controll.excecoes.NmCompletoVazioException;
import br.com.equipe.joinsocial.controll.excecoes.NmUsuarioVazioException;
import br.com.equipe.joinsocial.controll.excecoes.SenhaVaziaException;

//regras no preenchimento de formulário
public class RegrasFormulario {

	/*analisa se o campo e-mail está vazio
	 *caso esteja lança uma exceção*/
	public void EmailVazio(String conteudo) throws EmailVazioException {
		if (conteudo.trim().isEmpty()) {
			throw new EmailVazioException();
		}
	}

	/*analisa se o campo nome completo está vazio
	 *caso esteja lança uma exceção*/
	public void NmCompletoVazio(String conteudo)
			throws NmCompletoVazioException {
		if (conteudo.trim().isEmpty()) {
			throw new NmCompletoVazioException();
		}
	}

	/*analisa se o campo nome de usuário está vazio
	 *caso esteja lança uma exceção*/
	public void NmUsuarioVazio(String conteudo) throws NmUsuarioVazioException {
		if (conteudo.trim().isEmpty()) {
			throw new NmUsuarioVazioException();
		}
	}

	/*analisa se o campo senha está vazio
	 *caso esteja lança uma exceção*/
	public void SenhaVazia(String conteudo) throws SenhaVaziaException {
		if (conteudo.trim().isEmpty()) {
			throw new SenhaVaziaException();
		}
	}

}
