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
import br.com.doemais.dao.HemocentroDAO;
import br.com.doemais.dbo.AgendaHemocentro;
import br.com.doemais.dbo.Agendados;
import br.com.doemais.dbo.AtendimentoHemocentro;
import br.com.doemais.dbo.Campanhas;
import br.com.doemais.dbo.Hemocentro;
import br.com.doemais.dbo.UsuariosHemocentro;

@Path("/hemocentro")
public class HemocentroServices {

	private static final String CHARSET_UTF8 = ";charset=iso-8859-1";

	private HemocentroDAO hemocentroDAO;
	
	@Context
	private HttpServletRequest httpServletRequest;

	@PostConstruct
	private void init() {
		hemocentroDAO = new HemocentroDAO();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response login(UsuariosHemocentro userHemo) {
		UsuariosHemocentro userHemoLogado = null;
		try {
			userHemoLogado = hemocentroDAO.realizarLogin(userHemo.getEmail(), userHemo.getSenha());
			if (userHemoLogado != null) {
				return Response.status(200).entity(userHemoLogado).build();

			}
			if (hemocentroDAO.verificarUserExistente(userHemo.getEmail())) {
				return Response.status(404).entity("Senha incorreta!").build();
			} else {
				return Response.status(404).entity("Usu�rio n�o cadastrado").build();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(404).entity("Email ou senha incorretos").build();
	}

	@POST
	@Path("/listHemocentro")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Hemocentro login(Hemocentro hemocentro) {
		try {
			hemocentro = hemocentroDAO.listarHemocentro(hemocentro.getHemocentroId());
			if (hemocentro != null) {
				return hemocentro;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hemocentro;
	}
	@POST
	@Path("/listAgendados")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Agendados> listAgendados(Hemocentro hemocentro) {
		 List<Agendados> retorno = null;
		try {
			retorno = hemocentroDAO.listarAgendas(hemocentro.getHemocentroId());
			if (retorno != null) {
				return retorno;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
	@POST
	@Path("/checkInPendentes")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Agendados> checkInPendentes(Hemocentro hemocentro) {
		 List<Agendados> retorno = null;
		try {
			retorno = hemocentroDAO.checkInPendentes(hemocentro.getHemocentroId());
			if (retorno != null) {
				return retorno;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@POST
	@Path("/agenda")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<AgendaHemocentro> login(AgendaHemocentro agenda) {
		List<AgendaHemocentro> horarios = null;
		try {
			horarios = hemocentroDAO.getAgendaHemocentro(agenda.getHemocentroId());
			if (horarios != null) {
				return horarios;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return horarios;
	}

	@GET
	@Path("/listUser")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<UsuariosHemocentro> listarUsers() {
		List<UsuariosHemocentro> lista = null;
		try {
			lista = hemocentroDAO.listarUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	@GET
	@Path("/listHemocentros")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Hemocentro> listarHemocentros() {
		List<Hemocentro> lista = null;
		try {
			lista = hemocentroDAO.listarHemocentros();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@GET
	@Path("/listCampanhas")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Campanhas> listarCampanhas() {
		List<Campanhas> lista = null;
		try {
			lista = hemocentroDAO.listarCampanhas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}


	@GET
	@Path("/listHemocentrosPorNivel")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Hemocentro> listarHemocentrosPorNivel() {
		List<Hemocentro> lista = null;
		try {
			lista = hemocentroDAO.listarHemocentrosPorNivel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response addUserHemocentro(UsuariosHemocentro userHemo) {
		String msg = "";

		try {
			if (hemocentroDAO.verificarUserExistente(userHemo.getEmail())) {
				return Response.status(404).entity("Email existente!").build();
			} else if (hemocentroDAO.verificarCpfExistente(userHemo.getCpf())) {
				return Response.status(404).entity("CPF j� utilizado").build();
			} else {
				int idGerado = hemocentroDAO.addUsuarioHemocentro(userHemo);
				msg = String.valueOf(idGerado);
				
				return Response.status(201).entity("Usu�rio Cadastrado Com Sucesso").build();
			}

		} catch (Exception e) {
			msg = "Erro ao add o usu�rio, entre em contato com o administrador!" + e.getMessage();
		}

		return Response.status(404).entity(msg).build();
	}

	@POST
	@Path("/addAgenda")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response addAgenda(AtendimentoHemocentro agenda) throws Exception {
		String msg = "";

		try {

			int idGerado = hemocentroDAO.addAgenda(agenda);
			msg = String.valueOf(idGerado);
			return Response.status(201).entity("Agenda gerada com sucesso!").build();

		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@POST
	@Path("/dilvugarCampanha")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response divulgarCampanha(Campanhas camp) {
		String msg = "";

		try {

			boolean idGerado = hemocentroDAO.dilvugarCampanhas(camp);
			msg = String.valueOf(idGerado);
			return Response.status(201).entity("Campanha divulgada com sucesso!").build();

		} catch (Exception e) {
			msg = "Erro ao adicionar o usu�rio, entre em contato com o administrador!" + e.getMessage();
		}

		return Response.status(404).entity(msg).build();
	}


	@POST
	@Path("/atualizaDados")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response addDoador(Hemocentro hemocentro) {
		String msg = "";

		try {
			boolean response = hemocentroDAO.updateHemocentro(hemocentro.getHemocentroId(), hemocentro.getHorarios());
			if (response == true)
				return Response.status(200).build();
		} catch (Exception e) {
			msg = "Erro ao atualizar os dados, entre em contato com o administrador!" + e.getMessage();
			e.printStackTrace();
		}

		return Response.status(404).entity(msg).build();
	}

	@POST
	@Path("/confirmacaoCheckIn")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response confirmacaoCheckIn(Agendados agen) {
		String msg = "";
		try {
			boolean response = hemocentroDAO.confirmacaoCheckin(agen.getAgendaId());
			if (response == true)
				return Response.status(200).build();
		} catch (Exception e) {
			msg = "Erro ao realizar checkin";
			e.printStackTrace();
		}

		return Response.status(404).entity(msg).build();
	}
	@POST
	@Path("/desmarcarAgendamento")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response desmarcarAgendamento(Agendados agen) {
		String msg = "";

		try {
			boolean response = hemocentroDAO.desmarcarCheckIn(agen.getAgendaId());
			if (response == true)
				return Response.status(200).build();
		} catch (Exception e) {
			msg = "Erro ao realizar checkin";
			e.printStackTrace();
		}

		return Response.status(404).entity(msg).build();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/relatorioNivel")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces("application/pdf")
	public Response relatorioNivel(Hemocentro hemocentro) {
		String msg = "";
		try {
			String arquivoJrxml = hemocentro.getRelatorio();
			
			byte[] outputStream = null;
			Map fillParams = new HashMap(); 
			fillParams.put("hemocentro_id", hemocentro.getHemocentroId());
			fillParams.put("mes", hemocentro.getMes());
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
