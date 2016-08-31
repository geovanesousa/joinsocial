package br.com.equipe.joinsocial.view;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import br.com.equipe.joinsocial.R;
import br.com.equipe.joinsocial.controll.OperacoesDeUsuario;
import br.com.equipe.joinsocial.controll.excecoes.EmailVazioException;
import br.com.equipe.joinsocial.controll.utilitarios.RegrasFormulario;
import br.com.equipe.joinsocial.model.Usuario;

//tela de cadastro de email
public class CadastroEmail extends Activity {

	//thread que acessa o serviço web para verificar se
	//o e-mail escolhido não está em uso
	private idPorEmailTask idPorEmailTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastrar_email);

		//campo e-mail
		final EditText etEmail = (EditText) findViewById(R.id.et_email);
		final TextView tvEmailObrigatorio = (TextView) findViewById(R.id.tv_email_obrig);
		Button btAvancar = (Button) findViewById(R.id.bt_avancar_email);

		//evento ao clicar no botão avançar
		if (btAvancar != null) {
			btAvancar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String strEmail = etEmail.getEditableText().toString();

					try {
						new RegrasFormulario().EmailVazio(strEmail);
						idPorEmailTask = new idPorEmailTask();
						Usuario u = new Usuario();
						u.setEmail(strEmail.trim());
						Long resultado = idPorEmailTask.execute(u).get();
						if (resultado == 0) {
							Intent intent = new Intent(CadastroEmail.this,
									ConcluiCadastro.class);
							intent.putExtra("email", strEmail.trim());
							startActivity(intent);
						} else {
							etEmail.setBackgroundColor(getResources().getColor(
									R.color.vermelho_claro));
							tvEmailObrigatorio.setText("Email em uso");
						}
					} catch (EmailVazioException e) {
						etEmail.setBackgroundColor(getResources().getColor(
								R.color.vermelho_claro));
						tvEmailObrigatorio.setText("Digite o email");
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

	}

	//thread que acesso o serviço web para verificiar a disponibilidade
	// do endereço de e-mail, ou seja, se ele não está em uso.
	private class idPorEmailTask extends AsyncTask<Usuario, Void, Long> {

		@Override
		protected Long doInBackground(Usuario... params) {
			return new OperacoesDeUsuario().idUsuarioPorEmail(params[0]);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);
		}

	}

}
