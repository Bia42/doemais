package br.com.doemais.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.doemais.components.ReportGenerator;
import br.com.doemais.dao.PatrocinadorDAO;
import br.com.doemais.dbo.Campanhas;
import br.com.doemais.dbo.Cupom;
import br.com.doemais.dbo.Hemocentro;
import br.com.doemais.dbo.Patrocinador;

@Path("/patrocinador")
public class PatrocinadorServices {

	private static final String CHARSET = ";charset=utf-8";

	private PatrocinadorDAO patrocinadorDAO;
	
	@Context
	private HttpServletRequest httpServletRequest;

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
				return Response.status(404).entity("Usuário não cadastrado").build();
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
	@Path("/cadastrarCampanhas")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET)
	public Response gerarCupons(Campanhas campanha) {
		String msg = "";
		try {
			boolean retorno = patrocinadorDAO.cadastrarCampanha(campanha.getDescricao(), campanha.getPatrocinadorId(),
					campanha.getQuantCupons());
			if (retorno) {
				return Response.status(200).build();

			} else {
				msg = "Erro ao cadastrar campanhas, contate o administrador do sistema";
			}

		} catch (Exception e) {
			msg = "Erro ao cadastrar campanhas, contate o administrador do sistema";
			e.printStackTrace();
		}
		return Response.status(404).entity(msg).build();
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
			// if (patrocinadorDAO.verificarUserExistente(pat.getEmail())) {
			// return Response.status(404).entity("Email existente!").build();
			// } else if (patrocinadorDAO.verificarCnpjExistente(pat.getCnpj())) {
			// return Response.status(404).entity("CNPJ já utilizado").build();
			// } else {
			int idGerado = patrocinadorDAO.addPatrocinador(pat);
			msg = String.valueOf(idGerado);
			return Response.status(201).entity("Usuário Cadastrado Com Sucesso").build();
			// }

		} catch (Exception e) {
			msg = "Erro ao add o usuário, entre em contato com o administrador!" + e.getMessage();
		}

		return Response.status(404).entity(msg).build();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/relatorioContrato")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET)
	@Produces("application/pdf")
	public Response relatorioContrato(Patrocinador pat) {
		String msg = "";
		try {
			//Relatório Contrato
			String arquivoJrxml = "p_contrato";			
			byte[] outputStream = null;
			Map fillParams = new HashMap(); 
			fillParams.put("patrocinador_id", pat.getPatrocinadorId());
			ReportGenerator pdf = new ReportGenerator();
			byte[] bytes= pdf.generateJasperReportPDF(httpServletRequest, arquivoJrxml, outputStream, fillParams);

			String nomeRelatorio= arquivoJrxml + ".pdf";
			
			return Response.ok(bytes).type("application/pdf").header("Content-Disposition","inline; filename=\"" + nomeRelatorio + "\"").build();
		} catch (Exception e) {
			e.printStackTrace();
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
