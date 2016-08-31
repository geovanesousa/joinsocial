package br.com.equipe.joinsocial.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.equipe.joinsocial.R;
import br.com.equipe.joinsocial.controll.OperacoesDeUsuario;
import br.com.equipe.joinsocial.model.Usuario;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//tela que exibe o perfil de outros usuários
public class RedesDeOutro extends ListActivity {

	private Usuario usuario;
	private RedesSociaisTask redesSociaisTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.redes_de_outro);

		Bundle extras = getIntent().getExtras();
		this.usuario = new Usuario();
		usuario.setNomeUsuario(extras.getString("nomeUsuario"));
	}

	//lista as redes socias do usuario selecionado
	@Override
	protected void onResume() {
		super.onResume();
		redesSociaisTask = new RedesSociaisTask();
		try {
			TextView tvNmUsuario = (TextView) findViewById(R.id.tv_nm_usuario_encont);
			tvNmUsuario.setText(this.usuario.getNomeUsuario());
			List<String> redes = new ArrayList<String>();
			redes = redesSociaisTask.execute(this.usuario).get();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					RedesDeOutro.this, android.R.layout.simple_list_item_1, redes);
			setListAdapter(adapter);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	//acessa o serviço web para listar as redes
	//sociais do usuário selecionado
	private class RedesSociaisTask extends AsyncTask<Usuario, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Usuario... params) {
			return new OperacoesDeUsuario().consultarRedes(params[0].getNomeUsuario());
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(List<String> result) {
			super.onPostExecute(result);
		}

	}

}
