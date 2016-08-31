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

//tela que conclui o cadastro no aplicativo
public class ConcluiCadastro extends Activity {

	private Usuario usuario;
	private ProgressDialog dialog;
	//thread que insere o usuário na 
	//base de dados do serviço web
	private InsereUsuarioTask insereUsuarioTask;
	private UsuarioDAO dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.concluir_cadastro);

		final EditText etNmCompleto = (EditText) findViewById(R.id.et_nm_completo);

		final EditText etNmUsuario = (EditText) findViewById(R.id.et_nm_usuario);

		final EditText etSenha = (EditText) findViewById(R.id.et_senha);

		final TextView tvNmCompletoObrig = (TextView) findViewById(R.id.tv_nm_completo_obrig);

		final TextView tvNmUsuarioObrig = (TextView) findViewById(R.id.tv_nm_usuario_obrig);

		final TextView tvSenhaObrig = (TextView) findViewById(R.id.tv_senha_obrig);

		final TextView tvResultado = (TextView) findViewById(R.id.tv_resultado);

		Button btConcluir = (Button) findViewById(R.id.bt_concluir);
		//evento executado ao clicar no botão concluir
		btConcluir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String strNmCompleto = etNmCompleto.getEditableText()
						.toString();
				String strNmUsuario = etNmUsuario.getEditableText().toString();
				String strSenha = etSenha.getEditableText().toString();

				try {
					// Classe que avalia regras de campos do formulario
					RegrasFormulario rf = new RegrasFormulario();

					// verifica se o campo está preenchido
					rf.NmCompletoVazio(strNmCompleto);
					etNmCompleto.setBackgroundColor(getResources().getColor(
							R.color.azul_claro));
					tvNmCompletoObrig.setText("");

					rf.NmUsuarioVazio(strNmUsuario);
					etNmUsuario.setBackgroundColor(getResources().getColor(
							R.color.azul_claro));
					tvNmUsuarioObrig.setText("");

					rf.SenhaVazia(strSenha);
					etSenha.setBackgroundColor(getResources().getColor(
							R.color.azul_claro));
					tvSenhaObrig.setText("");

					dialog = ProgressDialog.show(ConcluiCadastro.this,
							getString(R.string.progresso_cadastro),
							getString(R.string.cadastrando));

					Bundle extras = getIntent().getExtras();
					usuario = new Usuario();
					usuario.setEmail(extras.getString("email"));
					usuario.setNomeCompleto(strNmCompleto);
					usuario.setNomeUsuario(strNmUsuario.trim());
					usuario.setSenha(strSenha.trim());

					insereUsuarioTask = new InsereUsuarioTask();
					Long resposta = insereUsuarioTask.execute(usuario).get();
					if (resposta == 1) {
						dao = new UsuarioDAO(ConcluiCadastro.this);
						dao.abrir();
						dao.inserir(usuario);
						dao.fechar();
						Toast.makeText(ConcluiCadastro.this,
								R.string.cad_sucesso, Toast.LENGTH_SHORT)
								.show();
						Intent intent = new Intent(ConcluiCadastro.this,
								RedesSociais.class);
						startActivity(intent);
						finish();

					}
					if (resposta == 2) {
						etNmUsuario.setBackgroundColor(getResources().getColor(
								R.color.vermelho_claro));
						tvNmUsuarioObrig
								.setText("Escolha outro nome de usuário");
					}
					if (resposta == 3) {
						Toast.makeText(ConcluiCadastro.this,
								R.string.cad_sem_sucesso, Toast.LENGTH_SHORT)
								.show();
					}
				} catch (NmCompletoVazioException e) {
					etNmCompleto.setBackgroundColor(getResources().getColor(
							R.color.vermelho_claro));
					tvNmCompletoObrig.setText("Digite o nome completo");
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

	//thread que insere o usuário cadastrado na 
	//base de dados do serviço web
	private class InsereUsuarioTask extends AsyncTask<Usuario, Void, Long> {

		@Override
		protected Long doInBackground(Usuario... params) {
			return new OperacoesDeUsuario().inserirUsuario(params[0]);
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
