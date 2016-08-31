package br.com.equipe.joinsocial.view;

import br.com.equipe.joinsocial.R;
import br.com.equipe.joinsocial.controll.dao.UsuarioDAO;
import br.com.equipe.joinsocial.model.Usuario;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//tela inicial do aplicativo
public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);

		Button btCadastreSe = (Button) findViewById(R.id.bt_cadastre_se);

		//evento ao clicar no botão cadastre-se
		if (btCadastreSe != null) {
			btCadastreSe.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//chama a tela cadastro de email
					Intent intent = new Intent(Principal.this,
							CadastroEmail.class);
					startActivity(intent);
				}
			});

		}

		Button btPesquisar = (Button) findViewById(R.id.bt_encontrar);

		//evento ao clicar no botão encontrar pessoas
		if (btPesquisar != null) {
			btPesquisar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Principal.this, Pesquisar.class);
					startActivity(intent);
				}
			});

		}

		Button btAutenticar = (Button) findViewById(R.id.bt_faca_login);

		//evento ao clicar no botão fazer login
		if (btAutenticar != null) {
			btAutenticar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Principal.this,
							Autenticacao.class);
					startActivity(intent);
				}
			});

		}

	}

	/*
	 * verifica se ja existe algum usuario guardado localmente se existir abre a
	 * tela de perfil do usuario
	 */
	@Override
	protected void onStart() {
		super.onStart();
		UsuarioDAO dao = new UsuarioDAO(Principal.this);
		dao.abrir();
		Usuario u = dao.meuUsuarioSenha();
		if (u != null) {
			Intent intent = new Intent(Principal.this, RedesSociais.class);
			startActivity(intent);
			finish();
		}

	}
}
