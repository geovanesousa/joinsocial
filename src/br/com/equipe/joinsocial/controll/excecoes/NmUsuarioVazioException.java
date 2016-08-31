package br.com.equipe.joinsocial.controll.excecoes;

//exceção lançada caso o campo nome de usuário esteja vazio
public class NmUsuarioVazioException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		
		return "Campo obrigatório!";
	}

}
