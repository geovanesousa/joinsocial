package br.com.equipe.joinsocial.controll.dao;

import br.com.equipe.joinsocial.model.Usuario;
import br.com.equipe.joinsocial.model.bd.CustomSQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

//classe que salva usuario e senha no banco de dados local
public class UsuarioDAO {
	private SQLiteDatabase bancoDeDados;
	private String[] colunas = { CustomSQLiteOpenHelper.COLUNA_ID,
			CustomSQLiteOpenHelper.COLUNA_NM_USUARIO,
			CustomSQLiteOpenHelper.COLUNA_SENHA };
	private CustomSQLiteOpenHelper sqliteOpenHelper;

	public UsuarioDAO(Context context) {
		sqliteOpenHelper = new CustomSQLiteOpenHelper(context);
	}

	//abre a conexão com o banco de dados local
	public void abrir() throws SQLException {
		bancoDeDados = sqliteOpenHelper.getWritableDatabase();
	}

	//fecha a conexão com o banco de dados local
	public void fechar() {
		sqliteOpenHelper.close();
		bancoDeDados.close();
	}

	//insere usuario e senha no banco de dados local
	public long inserir(Usuario u) {
		ContentValues valores = new ContentValues();
		valores.put(CustomSQLiteOpenHelper.COLUNA_NM_USUARIO,
				u.getNomeUsuario());
		valores.put(CustomSQLiteOpenHelper.COLUNA_SENHA, u.getSenha());
		long id = bancoDeDados.insert(CustomSQLiteOpenHelper.TABELA_USUARIO,
				null, valores);
		return id;
	}

	//recupera usuário e senha salvos no banco de dados local
	public Usuario meuUsuarioSenha() {
		Usuario usuario = new Usuario();
		try {
			abrir();
			Cursor cursor = bancoDeDados.query(
					CustomSQLiteOpenHelper.TABELA_USUARIO, colunas, null, null,
					null, null, null);
			cursor.moveToFirst();
			usuario.setId(cursor.getLong(0));
			usuario.setNomeUsuario(cursor.getString(1));
			usuario.setSenha(cursor.getString(2));
			cursor.close();
			fechar();
			return usuario;
		} catch (Exception e) {
			return null;
		}

	}

	/*atualiza o nome de usuario que está salvo
	no banco de dados local*/
	public long atualizarNmUsuario(Usuario u) {
		Usuario usuario = u;
		usuario.setId(meuUsuarioSenha().getId());
		ContentValues valores = new ContentValues();
		valores.put(CustomSQLiteOpenHelper.COLUNA_NM_USUARIO,
				u.getNomeUsuario());
		long retorno = bancoDeDados.update(
				CustomSQLiteOpenHelper.TABELA_USUARIO, valores,
				CustomSQLiteOpenHelper.COLUNA_ID + " = " + u.getId(), null);
		return retorno;
	}

	/*atualiza a senha que foi salva
	 * no banco de dados local*/
	public long atualizarSenha(Usuario u) {
		Usuario usuario = u;
		usuario.setId(meuUsuarioSenha().getId());
		ContentValues valores = new ContentValues();
		valores.put(CustomSQLiteOpenHelper.COLUNA_SENHA, u.getSenha());
		long retorno = bancoDeDados.update(
				CustomSQLiteOpenHelper.TABELA_USUARIO, valores,
				CustomSQLiteOpenHelper.COLUNA_ID + " = " + u.getId(), null);
		return retorno;
	}

	/*no caso do usuario se desautenticar ou excluir sua conta
	 * esse método exclui usuário e senha que estejam salvos
	 * no banco de dados local*/
	public long excluir(Usuario u) {
		Usuario usuario = u;
		usuario.setId(meuUsuarioSenha().getId());
		long retorno = bancoDeDados.delete(
				CustomSQLiteOpenHelper.TABELA_USUARIO,
				CustomSQLiteOpenHelper.COLUNA_ID + " = " + u.getId(), null);
		return retorno;
	}
}
