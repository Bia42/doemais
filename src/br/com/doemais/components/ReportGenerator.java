package br.com.doemais.components;

import java.sql.Connection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import br.com.doemais.config.BDConfig;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class ReportGenerator {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[]  generateJasperReportPDF(HttpServletRequest httpServletRequest, String jasperReportName, byte[] outputStream, Map parametros) {
		
		try {	
	
			String reportLocation = httpServletRequest.getServletContext().getRealPath("/")+jasperReportName+".jasper";
			
		    // Cria a conexão com o banco de dados. 
 			Connection conexao = null;
 			try {
 			  conexao = BDConfig.getConnection();
 			} catch (Exception e) {
 				e.printStackTrace();
 			}
		    
		    JasperPrint jasperPrint = JasperFillManager.fillReport(reportLocation, parametros, conexao);
		    
		    outputStream = JasperExportManager.exportReportToPdf(jasperPrint);
		    
		} catch (Exception e) {
		    e.printStackTrace();
		    System.out.println("Erro ao gerar relatório..."+e);
		} finally {
		}
		return outputStream;
		}
}
