package br.com.doemais.components;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import br.com.doemais.config.BDConfig;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ReportGenerator {

	public byte[]  generateJasperReportPDF(HttpServletRequest httpServletRequest, String jasperReportName, byte[] outputStream, Map parametros) {
		JRPdfExporter exporter = new JRPdfExporter();
		
		try {			
			String reportLocation = httpServletRequest.getRealPath("/") +jasperReportName+".jasper";
			//String location = httpServletRequest.getRealPath("/");
			
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
