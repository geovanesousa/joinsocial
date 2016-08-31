package br.com.equipe.joinsocial.view;

import java.util.concurrent.ExecutionException;
import br.com.equipe.joinsocial.R;
import br.com.equipe.joinsocial.controll.OperacoesDeUsuario;
import br.com.equipe.joinsocial.controll.dao.UsuarioDAO;
import br.com.equipe.joinsocial.controll.excecoes.NmCompletoVazioException;
import br.com.equipe.joinsocial.controll.excecoes.NmUsuarioVazioException;
import br.com.equipe.joinsocial.controll.excecoes.SenhaVaziaException;
import br.com.equipe.joinsocial.controll.utilitarios.RegrasFormulario;
import br.com.equipe.joinsocial.model.Usuario;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//tela de login
public class Autenticacao extends Activity {

	private Usuario usuario;
	private ProgressDialog dialog;
	//thread que faz conexão com o serviço web
	private AutenticacaoTask autenticacaoTask;
	private UsuarioDAO dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.autenticacao);

		//campo nome de usuário
		final EditText etNmUsuario = (EditText) findViewById(R.id.login_et_nm_usuario);
		//campo senha
		final EditText etSenha = (EditText) findViewById(R.id.login_et_senha);

		final TextView tvNmUsuarioObrig = (TextView) findViewById(R.id.login_tv_nm_obrig);

		final TextView tvSenhaObrig = (TextView) findViewById(R.id.login_tv_senha_obrig);

		final TextView tvResultado = (TextView) findViewById(R.id.login_tv_result);

		
		Button btEntrar = (Button) findViewById(R.id.login_bt_entrar);
		//evento executado ao clicar no botão entrar
		btEntrar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String strNmUsuario = etNmUsuario.getEditableText().toString();
				String strSenha = etSenha.getEditableText().toString();

				try {
					// Classe que avalia regras de campos do formulario
					RegrasFormulario rf = new RegrasFormulario();

					tvResultado.setBackgroundColor(getResources().getColor(R.color.azul_claro));
					tvResultado.setText("");
					// verifica se o campo está preenchido
										rf.NmUsuarioVazio(strNmUsuario);
					etNmUsuario.setBackgroundColor(getResources().getColor(
							R.color.azul_claro));
					tvNmUsuarioObrig.setText("");

					rf.SenhaVazia(strSenha);
					etSenha.setBackgroundColor(getResources().getColor(
							R.color.azul_claro));
					tvSenhaObrig.setText("");

					dialog = ProgressDialog.show(Autenticacao.this,
							getString(R.string.entrar),
							getString(R.string.autenticando));

					usuario = new Usuario();
					usuario.setNomeUsuario(strNmUsuario);
					usuario.setSenha(strSenha);

					autenticacaoTask = new AutenticacaoTask();
					//recebe a resposta do servidor
					//caso seja 0 nao faz login
					//caso seja 1 o login é valido
					Long resposta = autenticacaoTask.execute(usuario).get();
					if (resposta == 1) {
						dao = new UsuarioDAO(Autenticacao.this);
						dao.abrir();
						dao.inserir(usuario);
						dao.fechar();
						Toast.makeText(Autenticacao.this,
								R.string.bem_vindo, Toast.LENGTH_SHORT)
								.show();
						Intent intent = new Intent(Autenticacao.this,
								RedesSociais.class);
						startActivity(intent);
						finish();

					}else{
						Toast.makeText(Autenticacao.this,
								R.string.nao_autenticou, Toast.LENGTH_SHORT)
								.show();
						tvResultado.setBackgroundColor(getResources().getColor(R.color.vermelho));
						tvResultado.setText("Usuário ou senha inválidos!");
						
					}
				} catch (NmUsuarioVazioException e) {
					etNmUsuario.setBackgroundColor(getResources().getColor(
							R.color.vermelho_claro));
					tvNmUsuarioObrig.setText("Digite o nome de usuário");
				} catch (SenhaVaziaException e) {
					etSenha.setBackgroundColor(getResources().getColor(
							R.color.vermelho_claro));
					tvSenhaObrig.setText("Digite a senha");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	//threado que faz a verificação de login diretamente no serviço web
	private class AutenticacaoTask extends AsyncTask<Usuario, Void, Long> {

		@Override
		protected Long doInBackground(Usuario... params) {
			return new OperacoesDeUsuario().autenticacao(params[0]);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);
			dialog.dismiss();
		}

	}
}
