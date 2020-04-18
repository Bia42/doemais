package br.com.doemais.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.doemais.dao.HemocentroDAO;
import br.com.doemais.dbo.UsuariosHemocentro;


@Path("/hemocentro")
public class HemocentroServices {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";

	private HemocentroDAO hemocentroDAO;

	@PostConstruct
	private void init() {
		hemocentroDAO = new HemocentroDAO();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)	
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response login(UsuariosHemocentro userHemo) {
		
		try {
			userHemo = hemocentroDAO.realizarLogin(userHemo.getEmail(), userHemo.getSenha());
			if(userHemo != null) {
				return Response.status(200).entity(userHemo).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(404).entity("Email ou senha incorretos").build();
	}
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<UsuariosHemocentro> listarNotas() {
		List<UsuariosHemocentro> lista = null;
		try {
			lista = hemocentroDAO.listarUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response addDoador(UsuariosHemocentro userHemo) {
		String msg = "";

		try {
			int idGerado = hemocentroDAO.addUsuarioHemocentro(userHemo);

			msg = String.valueOf(idGerado);
			return Response.status(201).build();
		} catch (Exception e) {
			msg = "Erro ao add a nota!";
			e.printStackTrace();
		}

		return Response.status(404).entity(msg).build();
	}

	/*
	@GET
	@Path("/get/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario buscarPorId(@PathParam("id") int id) {
		Usuario usuario = null;
		try {
			usuario = usuarioDAO.buscarUsuarioPorId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
		//Teste
	}
	
	
	

	@GET
	@Path("/get/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Nota buscarPorId(@PathParam("id") int idNota) {
		Nota nota = null;
		try {
			nota = usuarioDAO.buscarNotaPorId(idNota);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nota;
	}

	@PUT
	@Path("/edit/{id}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarNota(Nota nota, @PathParam("id") int idNota) {
		String msg = "";

		System.out.println(nota.getTitulo());

		try {
			usuarioDAO.editarNota(nota, idNota);

			msg = "Nota editada com sucesso!";
		} catch (Exception e) {
			msg = "Erro ao editar a nota!";
			e.printStackTrace();
		}

		return msg;
	}

	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String removerNota(@PathParam("id") int idNota) {
		String msg = "";

		try {
			usuarioDAO.removerNota(idNota);

			msg = "Nota removida com sucesso!";
		} catch (Exception e) {
			msg = "Erro ao remover a nota!";
			e.printStackTrace();
		}

		return msg;
	}

	
	*/

}
