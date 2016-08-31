package br.com.equipe.joinsocial.controll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import android.util.Log;
import br.com.equipe.joinsocial.model.RedeSocial;
import br.com.equipe.joinsocial.model.Usuario;

public class OperacoesDeUsuario {

	/**
	 * Insere um usuário Join diretamente no Web Service
	 * 
	 * @param Usuario
	 *            u
	 * @return String resultado
	 */
	public Long inserirUsuario(Usuario u) {
		String externo = "http://social-geovanesousa.rhcloud.com/usuario/inserir";
		URL url = null;
		try {
			url = new URL(externo);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			JSONObject usuario = new JSONObject();
			usuario.put("email", u.getEmail());
			if (u.getFoto() != null) {
				usuario.put("foto", Arrays.toString(u.getFoto()));
			}
			usuario.put("nomeCompleto", u.getNomeCompleto());
			usuario.put("nomeUsuario", u.getNomeUsuario());
			usuario.put("senha", u.getSenha());

			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream());
			Log.i("Json", usuario.toString());
			wr.write(usuario.toString());
			wr.flush();
			wr.close();
			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			String resposta = sb.toString();
			JSONObject jsonResposta = new JSONObject(resposta);
			int cod = jsonResposta.getInt("codResposta");
			Log.i("Resposta", String.valueOf(cod));
			return Long.valueOf(cod);

		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("Erro JSON", e.getMessage());
			return 0L;
		} catch (ProtocolException e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		}

	}

	/*chamado quando usuario clica em fazer login
	caso o usuario e senha passados estejam corretos
	responde com 1, caso estejam errados 0*/
	public Long autenticacao(Usuario u) {
		String externo = "http://social-geovanesousa.rhcloud.com/usuario/autenticacao";
		URL url = null;
		try {
			url = new URL(externo);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			JSONObject usuario = new JSONObject();
			usuario.put("nomeUsuario", u.getNomeUsuario());
			usuario.put("senha", u.getSenha());

			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream());
			Log.i("Json", usuario.toString());
			wr.write(usuario.toString());
			wr.flush();
			wr.close();
			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			String resposta = sb.toString();
			JSONObject jsonResposta = new JSONObject(resposta);
			Long cod = jsonResposta.getLong("id");
			Log.i("Resposta", String.valueOf(cod));
			return cod;

		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("Erro JSON", e.getMessage());
			return 0L;
		} catch (ProtocolException e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		}

	}

	//usado para verificar se um email já esta em uso
	public Long idUsuarioPorEmail(Usuario u) {
		String externo = "http://social-geovanesousa.rhcloud.com/usuario/id_por_email";

		URL url = null;
		try {
			url = new URL(externo);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			JSONObject jsonEmail = new JSONObject();
			jsonEmail.put("email", u.getEmail());

			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream());
			Log.i("Json", jsonEmail.toString());
			wr.write(jsonEmail.toString());
			wr.flush();
			wr.close();
			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			String resposta = sb.toString();
			JSONObject jsonResposta = new JSONObject(resposta);
			Log.i("JSON resposta", resposta);
			Long id = jsonResposta.getLong("id");
			Log.i("Id", String.valueOf(id));
			return id;

		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.i("MalformedURLException", e.getMessage());
			return 0L;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("JSONException", e.getMessage());
			return 0L;
		} catch (ProtocolException e) {
			e.printStackTrace();
			Log.i("ProtocolException", e.getMessage());
			return 0L;
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException", e.getMessage());
			return 0L;
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		}

	}

	//lista todas as redes sociais de um usuario
	public List<String> consultarRedes(String nomeUsuario) {
		String externo = "http://social-geovanesousa.rhcloud.com/usuario/nome_de_usuario";
		List<String> redes = new ArrayList<String>();
		URL url = null;
		String resposta = null;
		Gson gson = new Gson();
		try {
			url = new URL(externo);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			JSONObject jsonNmUsuario = new JSONObject();
			jsonNmUsuario.put("nomeUsuario", nomeUsuario);

			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream());
			Log.i("Json", jsonNmUsuario.toString());
			wr.write(jsonNmUsuario.toString());
			wr.flush();
			wr.close();
			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			resposta = sb.toString();

			Log.i("JSON resposta", resposta);
			List<RedeSocial> lista = new ArrayList<RedeSocial>();
			Usuario usuario = gson.fromJson(resposta, Usuario.class);
			lista = usuario.getRedesSociais();
			redes.add(usuario.getNomeCompleto());
			int i = 0;
			while (true) {
				try {
					RedeSocial rs = lista.get(i);
					String redeSocial = rs.getNomeRedeSocial() + ": "
							+ rs.getNomeUsuario();
					redes.add(redeSocial);
					i = i + 1;
				} catch (IndexOutOfBoundsException e) {
					break;
				}
			}
			return redes;

		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.i("MalformedURLException", e.getMessage());
			return redes;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("JSONException", e.getMessage());
			return redes;
		} catch (ProtocolException e) {
			e.printStackTrace();
			Log.i("ProtocolException", e.getMessage());
			return redes;
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException", e.getMessage());
			return redes;
		} catch (JsonSyntaxException e) {
			try {
				JSONObject jsonResposta = new JSONObject(resposta);
				redes.add(jsonResposta.getString("nomeCompleto"));
				JSONObject jsonObjRedes = jsonResposta
						.getJSONObject("redesSociais");
				RedeSocial rs = new RedeSocial();
				rs = gson.fromJson(jsonObjRedes.toString(), RedeSocial.class);
				redes.add(rs.getNomeRedeSocial() + ": " + rs.getNomeUsuario());
			} catch (Exception erro) {

			}
			return redes;
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return redes;
		}

	}

	/*a cada caractere digitado na pesquisa, 
	 * responde com nomes de usuários correspondentes*/
	public List<String> usuariosPorInicio(String inicio) {
		String externo = "http://social-geovanesousa.rhcloud.com/usuario/listar_por_inicio";
		List<Usuario> usuarios = new ArrayList<Usuario>();
		List<String> lista = new ArrayList<String>();
		URL url = null;
		String resposta = null;
		Gson gson = new Gson();
		try {
			url = new URL(externo);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			JSONObject jsonInicio = new JSONObject();
			jsonInicio.put("nomeUsuario", inicio);

			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream());
			Log.i("Json", jsonInicio.toString());
			wr.write(jsonInicio.toString());
			wr.flush();
			wr.close();
			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			resposta = sb.toString();

			Log.i("JSON resposta", resposta);
			/*Type tipoDeColecao = new TypeToken<List<Usuario>>() {
			}.getType();
			usuarios = gson.fromJson(resposta, tipoDeColecao);*/
			JSONObject jsonObj = new JSONObject(resposta);
			JSONArray jsonArray = jsonObj.getJSONArray("usuario");
			int c = 0;
			while(true){
				try{
				JSONObject jsonObect = jsonArray.getJSONObject(c);
				Log.i("Nome de usuário", jsonObect.getString("nomeUsuario"));
				lista.add(jsonObect.getString("nomeUsuario"));
				c = c + 1;
				}catch(JSONException e){
					break;
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.i("MalformedURLException", e.getMessage());
		} catch (JSONException e) {
				try {
					JSONObject u = new JSONObject(resposta);
					Log.i("Nome de usuário: ", u.getJSONObject("usuario").
							getString("nomeUsuario"));
					lista.add(u.getJSONObject("usuario").
							getString("nomeUsuario"));
					Log.i("JSONException", e.getMessage());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			
			
		} catch (ProtocolException e) {
			e.printStackTrace();
			Log.i("ProtocolException", e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException", e.getMessage());
		} catch (JsonSyntaxException e) {
			Log.i("JsonSyntaxException", e.getMessage());
			try {
				Usuario usuario = new Usuario();
				usuario = gson.fromJson(resposta, Usuario.class);
				lista.add(usuario.getNomeUsuario());
			} catch (Exception erro) {

			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
		}
		return lista;
	}

	//insere uma rede social a clicar em adicionar rede
	public Long inserirRedeSocial(RedeSocial rede) {
		String externo = "http://social-geovanesousa.rhcloud.com/rede_social/inserir";
		URL url = null;
		try {
			url = new URL(externo);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			JSONObject jsonRedeSocial = new JSONObject();
			jsonRedeSocial.put("nomeRedeSocial", rede.getNomeRedeSocial());
			jsonRedeSocial.put("nomeUsuario", rede.getNomeUsuario());
			jsonRedeSocial.put("nomeUsuarioJoin", rede.getNomeUsuarioJoin());

			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream());
			Log.i("Json", jsonRedeSocial.toString());
			wr.write(jsonRedeSocial.toString());
			wr.flush();
			wr.close();
			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			String resposta = sb.toString();
			Long cod = Long.valueOf(resposta.trim());
			Log.i("Resposta", resposta);
			return cod;

		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("Erro JSON", e.getMessage());
			return 0L;
		} catch (ProtocolException e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("Erro", e.getMessage());
			return 0L;
		}
	}
}
