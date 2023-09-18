package com.cardManagement.cardmanagementapp.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cardManagement.cardmanagementapp.entities.Statement;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/******************************************************************************
 * @author           Hetal Parmar
 * Description       This service class provides functionality for generating PDF reports based on a list of Statement objects
                     and saving them to a specified output path.
 * Version           1.0
 * Created Date      13-Sept-2023 
 ******************************************************************************/
@Service
public class PdfGenerationService {

	 /******************************************************************************
	 * Method              - generatePdf
	 * Description         - Generate a PDF document from a list of Statement objects.
	 * @param statements   - The list of Statement objects to include in the report.
    * @param outputPath   - The file path where the generated PDF will be saved.
    * Created by            Hetal Parmar
    * Created Date          12-Sept-2023 
    ******************************************************************************/
	public void generatePdf(List<Statement> statement, String outputPath) {
		try {

			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(outputPath));
			document.open();

			PdfPTable table = new PdfPTable(5);

			table.setWidthPercentage(100);

			Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
			table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.getDefaultCell().setPadding(8f);

			table.addCell(new Phrase("Statement ID", font));
			table.addCell(new Phrase("Generation Date", font));
			table.addCell(new Phrase("Bill", font));
			table.addCell(new Phrase("Transaction Details", font));
			table.addCell(new Phrase("Category", font));

			for (Statement statements : statement) {

				table.addCell(new Phrase(String.valueOf(statements.getStatementId()), font));
				table.addCell(new Phrase(statements.getGenerationDate().toString(), font));
				table.addCell(new Phrase(String.valueOf(statements.getBill()), font));
				table.addCell(new Phrase(String.valueOf(statements.getCategory()), font));

			}
			document.add(table);
			document.close();
		}
		catch (DocumentException | FileNotFoundException e) {
			e.printStackTrace();

		}
	}

}
