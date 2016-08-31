package br.com.equipe.joinsocial.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.equipe.joinsocial.R;
import br.com.equipe.joinsocial.controll.OperacoesDeUsuario;
import br.com.equipe.joinsocial.controll.dao.UsuarioDAO;
import br.com.equipe.joinsocial.model.Usuario;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class RedesSociais extends ListActivity {

	private Usuario usuario;
	// se conecta ao serviço web para listar as redes
	// sociais do usuário cadastrado no aplicativo
	private RedesSociaisTask redesSociaisTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.redes_sociais);

		this.usuario = new UsuarioDAO(RedesSociais.this).meuUsuarioSenha();

	}

	// lista as redes sociais do usuário cadastrado no aplicativo
	@Override
	protected void onResume() {
		super.onResume();
		redesSociaisTask = new RedesSociaisTask();
		try {
			List<String> redes = new ArrayList<String>();
			redes = redesSociaisTask.execute(this.usuario).get();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					RedesSociais.this, android.R.layout.simple_list_item_1,
					redes);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.redes_sociais, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.add_rede) {
			Intent intent = new Intent(RedesSociais.this,
					AdicionarRedeSocial.class);
			intent.putExtra("nomeUsuarioJoin", usuario.getNomeUsuario());
			startActivity(intent);
			finish();

		}
		if (item.getItemId() == R.id.enc_pessoas) {
			Intent intent = new Intent(RedesSociais.this, Pesquisar.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	// thread que acessa o serviço web para listar
	// as redes sociais do usuário cadastrado no aplicativo
	private class RedesSociaisTask extends
			AsyncTask<Usuario, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Usuario... params) {
			return new OperacoesDeUsuario().consultarRedes(params[0]
					.getNomeUsuario());
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
