package br.com.equipe.joinsocial.model.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*classe que representa a tabela do banco de dados local
essa tabela armazena usuário e senha após cadastro
ou login no aplicativo*/
public class CustomSQLiteOpenHelper extends SQLiteOpenHelper{

	public static final String TABELA_USUARIO = "usuario";
	public static final String COLUNA_ID = "_id";
	public static final String COLUNA_NM_USUARIO = "nm_usuario";
	public static final String COLUNA_SENHA = "senha";
	
	public static final String NOME_BD = "social.db";
	public static final int VERSAO_BD = 1;
	
	private static final String CRIA_TB_USUARIO = "create table "
			+ TABELA_USUARIO + "(" + COLUNA_ID
			+ " integer primary key autoincrement, " + COLUNA_NM_USUARIO
			+ " text not null, "+COLUNA_SENHA+" text not null);" ;
	
	public CustomSQLiteOpenHelper(Context context) {
		super(context, NOME_BD, null, VERSAO_BD);
	}

	//cria o banco de dados e a tabela
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CRIA_TB_USUARIO);		
	}

	//atualiza a estrutura do banco de dados
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABELA_USUARIO);
		onCreate(db);
	}
	
}
