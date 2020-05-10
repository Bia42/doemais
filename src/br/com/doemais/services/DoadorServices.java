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

import br.com.doemais.dao.DoadorDAO;
import br.com.doemais.dbo.Doacoes;
import br.com.doemais.dbo.Doador;

@Path("/doador")
public class DoadorServices {

	private static final String CHARSET_UTF8 = ";charset=utf-8";

	private DoadorDAO doadorDAO;

	@PostConstruct
	private void init() {
		doadorDAO = new DoadorDAO();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response login(Doador doador) {
		Doador doadorLogado = null;
		try {
			doadorLogado = doadorDAO.realizarLogin(doador.getEmail(), doador.getSenha());
			if (doadorLogado != null) {
				return Response.status(200).entity(doadorLogado).build();
			}
			if (doadorDAO.verificarUserExistente(doador.getEmail())) {
				return Response.status(404).entity("Senha incorreta!").build();
			} else {
				return Response.status(404).entity("Usuário não cadastrado").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(404).entity("Email ou senha incorretos").build();
	}

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Doador> listarNotas() {
		List<Doador> lista = null;
		try {
			lista = doadorDAO.listarDoadores();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response addDoador(Doador doador) {
		String msg = "";

		try {
			if (doadorDAO.verificarUserExistente(doador.getEmail())) {
				return Response.status(404).entity("Email existente!").build();
			} else if (doadorDAO.verificarCpfExistente(doador.getCpf())) {
				return Response.status(404).entity("CPF já utilizado").build();
			} else {
				int idGerado = doadorDAO.addDoador(doador);
			}
			return Response.status(201).build();
		} catch (Exception e) {
			msg = "Erro ao add o usuário, entre em contato com o administrador!" + e.getMessage();
			e.printStackTrace();
		}

		return Response.status(404).entity(msg).build();
	}

	@POST
	@Path("/atualizaDoacao")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response addDoador(Doacoes doacoes) {
		String msg = "";

		try {
			boolean response = doadorDAO.updateDoacao(doacoes.getId(), doacoes.getConfirmacao());
			if (response == true)
				return Response.status(200).build();
		} catch (Exception e) {
			msg = "Erro ao add a nota!";
			e.printStackTrace();
		}

		return Response.status(404).entity(msg).build();
	}

	@POST
	@Path("/listDoacoes")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Doacoes> listDoacoes(Doacoes doacoes) {
		String msg = "";
		List<Doacoes> lista = null;
		try {
			lista = doadorDAO.listarDoaocoes(doacoes.getDoadorId());

			return lista;
		} catch (Exception e) {
			msg = "Erro a listar doações";
			e.printStackTrace();
		}

		return lista;
	}

	/*
	 * @GET
	 * 
	 * @Path("/get/{id}")
	 * 
	 * @Consumes(MediaType.TEXT_PLAIN)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8) public Usuario
	 * buscarPorId(@PathParam("id") int id) { Usuario usuario = null; try { usuario
	 * = usuarioDAO.buscarUsuarioPorId(id); } catch (Exception e) {
	 * e.printStackTrace(); } return usuario; //Teste }
	 * 
	 * 
	 * 
	 * 
	 * @GET
	 * 
	 * @Path("/get/{id}")
	 * 
	 * @Consumes(MediaType.TEXT_PLAIN)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8) public Nota
	 * buscarPorId(@PathParam("id") int idNota) { Nota nota = null; try { nota =
	 * usuarioDAO.buscarNotaPorId(idNota); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return nota; }
	 * 
	 * @PUT
	 * 
	 * @Path("/edit/{id}")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public String editarNota(Nota
	 * nota, @PathParam("id") int idNota) { String msg = "";
	 * 
	 * System.out.println(nota.getTitulo());
	 * 
	 * try { usuarioDAO.editarNota(nota, idNota);
	 * 
	 * msg = "Nota editada com sucesso!"; } catch (Exception e) { msg =
	 * "Erro ao editar a nota!"; e.printStackTrace(); }
	 * 
	 * return msg; }
	 * 
	 * @DELETE
	 * 
	 * @Path("/delete/{id}")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public String removerNota(@PathParam("id")
	 * int idNota) { String msg = "";
	 * 
	 * try { usuarioDAO.removerNota(idNota);
	 * 
	 * msg = "Nota removida com sucesso!"; } catch (Exception e) { msg =
	 * "Erro ao remover a nota!"; e.printStackTrace(); }
	 * 
	 * return msg; }
	 * 
	 * 
	 */

}
