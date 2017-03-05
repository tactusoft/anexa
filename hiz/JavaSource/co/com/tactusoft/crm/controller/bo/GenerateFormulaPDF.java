package co.com.tactusoft.crm.controller.bo;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.tools.ant.DirectoryScanner;

import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.view.backing.ApplicationBacking;

public class GenerateFormulaPDF {

	public static void PDF(BigDecimal idAppointment, String type)
			throws JRException, IOException, SQLException {
		String imagePath = FacesUtil.getRealPath("/images/");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(JRParameter.REPORT_LOCALE, new Locale("es", "CO"));
		param.put("P_APPOINTMENT", idAppointment.intValue());
		param.put("P_IMAGE", imagePath);

		Connection connection = getConnection();

		String path = "/reports/formula.jasper";
		String nameReport = "FormulaMedica.pdf";
		if (type.equals(Constant.MATERIAL_TYPE_THERAPY)) {
			path = "/reports/therapy.jasper";
			nameReport = "FormulaTerapia.pdf";
		} else if (type.equals(Constant.MATERIAL_TYPE_EXAMS)) {
			path = "/reports/lab.jasper";
			nameReport = "ExamanesLab.pdf";
		} else if (type.equals(Constant.MATERIAL_TYPE_ODONTOLOGY)) {
			path = "/reports/formulaOdo.jasper";
			nameReport = "FormulaOdontologica.pdf";
		}

		String reportPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath(path);
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath,
				param, connection);
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=" + nameReport);
		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,
				servletOutputStream);
		FacesContext.getCurrentInstance().responseComplete();
		connection.close();
	}

	public static void remissionPDF(BigDecimal idNote) throws JRException,
			IOException, SQLException {
		String imagePath = FacesUtil.getRealPath("/images/");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(JRParameter.REPORT_LOCALE, new Locale("es", "CO"));
		param.put("P_APPOINTMENT", idNote.intValue());
		param.put("P_IMAGE", imagePath);

		Connection connection = getConnection();

		String path = "/reports/remission.jasper";
		String nameReport = "Remission.pdf";

		String reportPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath(path);
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath,
				param, connection);
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=" + nameReport);
		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,
				servletOutputStream);
		FacesContext.getCurrentInstance().responseComplete();
		connection.close();
	}

	public static void recommendationsPDF(BigDecimal idNote)
			throws JRException, IOException, SQLException {
		String imagePath = FacesUtil.getRealPath("/images/");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(JRParameter.REPORT_LOCALE, new Locale("es", "CO"));
		param.put("P_APPOINTMENT", idNote.intValue());
		param.put("P_IMAGE", imagePath);

		Connection connection = getConnection();

		String path = "/reports/recommendations.jasper";
		String nameReport = "Recommendations.pdf";

		String reportPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath(path);
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath,
				param, connection);
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=" + nameReport);
		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,
				servletOutputStream);
		FacesContext.getCurrentInstance().responseComplete();
		connection.close();
	}

	public static void historyPDF(BigDecimal idPatient, String type,
			Integer gender, Integer show) throws JRException, IOException,
			SQLException {
		String imagePath = FacesUtil.getRealPath("/images/");
		String subreportsPath = FacesUtil.getRealPath("/reports/") + "/";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(JRParameter.REPORT_LOCALE, new Locale("es", "CO"));
		param.put("P_ID_PATIENT", idPatient);
		param.put("SUBREPORT_DIR", subreportsPath);
		param.put("P_IMAGE", imagePath);
		param.put("P_GENDER", gender.toString());
		param.put("P_SHOW", show.toString());

		Connection connection = getConnection();

		String nameReport = "HistoriaClinica";
		String path = "/reports/history_new.jasper";
		if (type.equals(Constant.ODONTOLOGY_HISTORY_TYPE)) {
			path = "/reports/historyOdo.jasper";
			nameReport = "HistoriaOdontologica";
		}

		nameReport = nameReport + idPatient + ".pdf";

		String reportPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath(path);
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath,
				param, connection);
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=" + nameReport);
		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,
				servletOutputStream);
		FacesContext.getCurrentInstance().responseComplete();
		connection.close();
	}

	public static String getHistoryPDF(BigDecimal idPatient, String type,
			Integer gender, Integer show) throws JRException, IOException,
			SQLException {
		// String reportPdf = null;
		String imagePath = FacesUtil.getRealPath("/images/");
		String subreportsPath = FacesUtil.getRealPath("/reports/") + "/";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(JRParameter.REPORT_LOCALE, new Locale("es", "CO"));
		param.put("P_ID_PATIENT", idPatient);
		param.put("SUBREPORT_DIR", subreportsPath);
		param.put("P_IMAGE", imagePath);
		param.put("P_GENDER", gender.toString());
		param.put("P_SHOW", show.toString());

		Connection connection = getConnection();

		String nameReport = "HistoriaClinica";
		String path = "/reports/history_new.jasper";
		if (type.equals(Constant.ODONTOLOGY_HISTORY_TYPE)) {
			path = "/reports/historyOdo.jasper";
			nameReport = "HistoriaOdontologica";
		}

		ApplicationBacking applicationBacking = FacesUtil
				.findBean("applicationBacking");
		int consecutive = applicationBacking.getConsecutive();
		applicationBacking.setConsecutive(consecutive + 1);
		nameReport = nameReport + FacesUtil.getCurrentUser().getId() + "_"
				+ idPatient + "_" + consecutive + ".pdf";
		String reportPathPDF = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/pdf/");
		FacesUtil.deleteHistory(reportPathPDF);

		String reportPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath(path);
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath,
				param, connection);
		JasperExportManager.exportReportToPdfFile(jasperPrint, reportPathPDF
				+ "/" + nameReport);
		connection.close();
		// System.out.println(reportPdf);

		/*
		 * ByteArrayOutputStream out = new ByteArrayOutputStream();
		 * JRPdfExporter exporter = new JRPdfExporter();
		 * exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, new
		 * Boolean( true));
		 * exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, new
		 * Boolean(true)); /*
		 * exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, new
		 * Integer( PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY));
		 * 
		 * // exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD,
		 * "crm"); exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD,
		 * "1c2rm3"); exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
		 * out); exporter.setParameter(JRExporterParameter.JASPER_PRINT,
		 * jasperPrint);
		 * exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
		 * nameReport); exporter.exportReport();
		 * 
		 * InputStream fis = new ByteArrayInputStream(out.toByteArray());*
		 * 
		 * return new DefaultStreamedContent(fis,
		 * "application/pdf; charset=UTF-8", nameReport);
		 */

		return nameReport;
	}

	public static void deleteFile(String folder) {
		String nameReport = "HistoriaClinica"
				+ FacesUtil.getCurrentUser().getId() + "_*" + ".pdf";
		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setIncludes(new String[] { nameReport });
		scanner.setBasedir(folder);
		scanner.setCaseSensitive(false);
		scanner.scan();
		String[] files = scanner.getIncludedFiles();
		for (String file : files) {
			FacesUtil.removeFile(file);
		}
	}

	public static Connection getConnection() throws SQLException {
		String url = FacesUtil.getParameterTextValue("JDBC_URL");
		String username = FacesUtil.getParameterTextValue("JDBC_USER");
		String password = FacesUtil.getParameterTextValue("JDBC_PASSWORD");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection(url, username, password);
	}
}
