package br.com.equipe.joinsocial.view;

import java.util.concurrent.ExecutionException;

import br.com.equipe.joinsocial.R;
import br.com.equipe.joinsocial.controll.OperacoesDeUsuario;
import br.com.equipe.joinsocial.controll.excecoes.NmUsuarioVazioException;
import br.com.equipe.joinsocial.controll.utilitarios.RegrasFormulario;
import br.com.equipe.joinsocial.model.RedeSocial;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

//tela que adiciona uma nova rede social
public class AdicionarRedeSocial extends Activity{
	
	//thread que faz conexao diretamente ao serviço web
	private InserirRedeTask inserirRedeTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_rede_social);
		
		//lista de redes sociais para o usuario escolher
		final Spinner spinner = (Spinner) findViewById(R.id.sp_lista_redes);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.redes_sociais, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		//nome de usuario digitado
		final EditText etNmUsuarioRede = (EditText) findViewById(R.id.et_nm_usuario_rede);
		final TextView tvNmUsuarioObrig = (TextView) findViewById(R.id.tv_nm_rede_obrig);
		Button btAddRede = (Button) findViewById(R.id.bt_add_rede);
		
		//evento ao clicar no botão executar
		btAddRede.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
				RegrasFormulario rf = new RegrasFormulario();
				String nmUsuario = etNmUsuarioRede.getEditableText().toString().trim();
				rf.NmUsuarioVazio(nmUsuario);
				RedeSocial rdSocial = new RedeSocial();
				rdSocial.setNomeRedeSocial(spinner.getSelectedItem().toString());
				rdSocial.setNomeUsuario(nmUsuario);
				Bundle extras = getIntent().getExtras();
				rdSocial.setNomeUsuarioJoin(extras.getString("nomeUsuarioJoin"));
				inserirRedeTask = new InserirRedeTask();
					Long resultado = inserirRedeTask.execute(rdSocial).get();
					Intent intent = new Intent(AdicionarRedeSocial.this, RedesSociais.class);
					startActivity(intent);
					finish();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NmUsuarioVazioException e) {
					etNmUsuarioRede.setBackgroundColor(getResources().getColor(
							R.color.vermelho_claro));
					tvNmUsuarioObrig.setText("Digite o nome de usuário");
				}				
				
			}
		});
		
	}
	 
	/*thread que insere redes sociais diretamente na 
	 * base de dados do serviço web*/
	 private class InserirRedeTask extends AsyncTask<RedeSocial, Void, Long> {

			@Override
			protected Long doInBackground(RedeSocial... params) {
				return new OperacoesDeUsuario().inserirRedeSocial(params[0]);
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
