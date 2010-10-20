package com.babukuma.jasper;

import java.io.File;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class PDFBuilder {
	private static Log log = LogFactory.getLog(PDFBuilder.class);

	/**
	 * 指定したPathにPDFを出力する。
	 * 
	 * @author BABUKUMA
	 * @since 2009/07/13
	 * @param pdfPath
	 *            PDF格納PATH
	 * @param jasperPath
	 *            JasperファイルのPATH
	 * @param params
	 *            パラメータ
	 * @param beans
	 *            Beanの配列。データソース
	 * @param password
	 *            パスワード
	 * @throws WISException
	 *             エラー
	 */
	public static void create(final String pdfPath, final String jasperPath, final Map<String, Object> params, final Object[] beans, final String password) {
		try {
			File path = new File(pdfPath);
			if (!path.getParentFile().exists()) {
				path.getParentFile().mkdirs();
			}

			File reportFile = new File(jasperPath);

			if (!reportFile.exists()) {
				log.error(jasperPath + "ファイルが存在しない。");
				throw new RuntimeException(jasperPath + "ファイルが存在しない。");
			}

			JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), params, new JRBeanArrayDataSource(beans));

			// Exporterの生成
			JRPdfExporter exporter = new JRPdfExporter();

			// 出力オブジェクトの設定
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);

			// 出力先の設定
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, pdfPath);

			if (password != null) {
				// パスワード保護を有効にする
				exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
				// パスワードの設定
				exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, password);
			}

			// 出力の実行
			exporter.exportReport();
		} catch (JRException e) {
			log.error("PDF出力失敗", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 指定したPathにPDFを出力する。
	 * 
	 * @author BABUKUMA
	 * @since 2009/07/13
	 * @param pdfPath
	 *            PDF格納PATH
	 * @param jasperPath
	 *            JasperファイルのPATH
	 * @param params
	 *            パラメータ
	 * @param password
	 *            パスワード
	 * @throws WISException
	 *             エラー
	 */
	public static void create(final String pdfPath, final String jasperPath, final Map<String, Object> params, final String password) {
		try {
			File path = new File(pdfPath);
			if (!path.getParentFile().exists()) {
				path.getParentFile().mkdirs();
			}

			File reportFile = new File(jasperPath);

			if (!reportFile.exists()) {
				log.error(jasperPath + "ファイルが存在しない。");
				throw new RuntimeException(jasperPath + "ファイルが存在しない。");
			}

			JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), params, new JREmptyDataSource());

			// Exporterの生成
			JRPdfExporter exporter = new JRPdfExporter();

			// 出力オブジェクトの設定
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);

			// 出力先の設定
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, pdfPath);

			if (password != null) {
				// パスワード保護を有効にする
				exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
				// パスワードの設定
				exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, password);
			}

			// 出力の実行
			exporter.exportReport();
		} catch (JRException e) {
			log.error("PDF出力失敗", e);
			throw new RuntimeException(e);
		}
	}
}
