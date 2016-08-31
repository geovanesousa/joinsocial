package br.com.equipe.joinsocial.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import br.com.equipe.joinsocial.R;
import br.com.equipe.joinsocial.controll.OperacoesDeUsuario;
import br.com.equipe.joinsocial.model.Usuario;

public class Pesquisar extends ListActivity {

	// thread que acesso o serviço para
	// consultar nomes de usuário
	private UsuariosPorInicioTask usuariosPorInicioTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesquisar);

		final EditText etPesquisar = (EditText) findViewById(R.id.et_pesquisar);
		etPesquisar.addTextChangedListener(new TextWatcher() {

			// executa quando o texto da caixa for alterado
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String textoNaEditText = etPesquisar.getText().toString();
				if (textoNaEditText.trim().length() > 0) {
					List<String> usuarios = new ArrayList<String>();
					Usuario u = new Usuario();
					u.setNomeUsuario(textoNaEditText);
					usuariosPorInicioTask = new UsuariosPorInicioTask();
					try {
						usuarios = usuariosPorInicioTask.execute(u).get();
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								Pesquisar.this,
								android.R.layout.simple_list_item_1, usuarios);
						setListAdapter(adapter);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	// evento para a lista de nomes de usuário
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		// Pegar o item clicado
		Object o = this.getListAdapter().getItem(position);
		String nmUsuarioSelecionado = o.toString();
		Intent intent = new Intent(Pesquisar.this, RedesDeOutro.class);
		intent.putExtra("nomeUsuario", nmUsuarioSelecionado.trim());
		startActivity(intent);
	}

	// thread que acesso o serviço web para listar os usuários
	private class UsuariosPorInicioTask extends
			AsyncTask<Usuario, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Usuario... params) {
			return new OperacoesDeUsuario().usuariosPorInicio(params[0]
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