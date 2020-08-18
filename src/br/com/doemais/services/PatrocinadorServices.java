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

import br.com.doemais.dao.PatrocinadorDAO;
import br.com.doemais.dbo.Cupom;
import br.com.doemais.dbo.Patrocinador;

@Path("/patrocinador")
public class PatrocinadorServices {

	private static final String CHARSET = ";charset=iso-8859-1";

	private PatrocinadorDAO patrocinadorDAO;

	@PostConstruct
	private void init() {
		patrocinadorDAO = new PatrocinadorDAO();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET)
	@Produces(MediaType.APPLICATION_JSON + CHARSET)
	public Response login(Patrocinador pat) {
		Patrocinador patLogado = null;
		try {
			patLogado = patrocinadorDAO.realizarLogin(pat.getEmail(), pat.getSenha());
			if (patLogado != null) {
				return Response.status(200).entity(patLogado).build();
			}
			if (patrocinadorDAO.verificarUserExistente(pat.getEmail())) {
				return Response.status(404).entity("Senha incorreta!").build();
			} else {
				return Response.status(404).entity("Usu�rio n�o cadastrado").build();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(404).entity("Email ou senha incorretos").build();
	}

	@GET
	@Path("/listPatrocinadores")
	@Produces(MediaType.APPLICATION_JSON + CHARSET)
	public List<Patrocinador> listarUsers() {
		List<Patrocinador> lista = null;
		try {
			lista = patrocinadorDAO.listarPatrocinadores();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@GET
	@Path("/listCuponsAtivos")
	@Produces(MediaType.APPLICATION_JSON + CHARSET)
	public List<Cupom> listarCuponsAtivos() {
		List<Cupom> lista = null;
		try {
			lista = patrocinadorDAO.listarCuponsDisponiveis();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@POST
	@Path("/cupomAutoGerado")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET)
	@Produces(MediaType.APPLICATION_JSON + CHARSET)
	public Cupom listarCupomAutoGerado(Cupom cup) {
		Cupom cupom = null;
		try {
			cupom = patrocinadorDAO.listarCupomAutoGerado(cup.getDoadorId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cupom;
	}
	@POST
	@Path("/listCuponsAtivosPorPatrocinador")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET)
	@Produces(MediaType.APPLICATION_JSON + CHARSET)
	public List<Cupom> listarCuponsAtivosPorPatrocinador(Cupom cupom) {
		List<Cupom> lista = null;
		try {
			lista = patrocinadorDAO.listarCuponsDispoPat(cupom.getPatrocinadorId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@POST
	@Path("/listCuponsResgatadosPorPatrocinador")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET)
	@Produces(MediaType.APPLICATION_JSON + CHARSET)
	public List<Cupom> listarCuponsResgatadosPorPatrocinador(Cupom cupom) {
		List<Cupom> lista = null;
		try {
			lista = patrocinadorDAO.listarCuponsResgaPat(cupom.getPatrocinadorId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@POST
	@Path("/gerarCupons")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET)
	@Produces(MediaType.APPLICATION_JSON + CHARSET)
	public List<String> gerarCupons(Cupom cupom) {
		List<String> lista = null;
		try {
			lista = patrocinadorDAO.geraCupons(cupom.getPatrocinadorId(), cupom.getQuantidade(), cupom.getDescricao());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@POST
	@Path("/vinculoCupom")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET)
	public Response addDoador(Cupom cupom) {
		String msg = "";

		try {
			boolean response = patrocinadorDAO.vinculoCupom(cupom.getCupomId(), cupom.getDoadorId());
			if (response == true)
				return Response.status(200).build();
		} catch (Exception e) {
			msg = "Erro vincunlar cupom, contate o administrador do sistema";
			e.printStackTrace();
		}

		return Response.status(404).entity(msg).build();
	}
	
	
	@POST
	@Path("/cupomPorDoador")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET)
	public List<Cupom> cupomDoador(Cupom cupom) {
		List<Cupom> lista = null;
		try {
			lista = patrocinadorDAO.listarCuponsDoador(cupom.getDoadorId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}


	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET)
	public Response adduserPatrocinador(Patrocinador pat) {
		String msg = "";
		try {
			//if (patrocinadorDAO.verificarUserExistente(pat.getEmail())) {
			//	return Response.status(404).entity("Email existente!").build();
			//} else if (patrocinadorDAO.verificarCnpjExistente(pat.getCnpj())) {
			//	return Response.status(404).entity("CNPJ j� utilizado").build();
			//} else {
				int idGerado = patrocinadorDAO.addPatrocinador(pat);
				msg = String.valueOf(idGerado);
				return Response.status(201).entity("Usu�rio Cadastrado Com Sucesso").build();
			//}

		} catch (Exception e) {
			msg = "Erro ao add o usu�rio, entre em contato com o administrador!" + e.getMessage();
		}

		return Response.status(404).entity(msg).build();
	}

	/*
	 * @GET
	 * 
	 * @Path("/get/{id}")
	 * 
	 * @Consumes(MediaType.TEXT_PLAIN)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON + CHARSET) public Usuario
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
	 * @Produces(MediaType.APPLICATION_JSON + CHARSET) public Nota
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
	 * @Consumes(MediaType.APPLICATION_JSON + CHARSET)
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
