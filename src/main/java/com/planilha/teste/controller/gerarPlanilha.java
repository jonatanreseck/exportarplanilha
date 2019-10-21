package com.planilha.teste.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planilha.teste.entities.Pareto;

@CrossOrigin
@RestController
@RequestMapping("/gerarPlanilha")
public class gerarPlanilha {

	@GetMapping
	public void listar() {
		// Criando o arquivo e uma planilha chamada "Gráfico de Pareto"
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Gráfico de Pareto");
		// Definindo alguns padroes de layout
		sheet.setDefaultColumnWidth(25);
		sheet.setDefaultRowHeight((short) 400);

		// Carregando os problemas
		List<Pareto> paretoList = getPareto();

		int rownum = 0;
		int cellnum = 0;
		Cell cell;
		Row row;

		// Configurando estilos de células (Cores, alinhamento, formatação, etc..)
		HSSFDataFormat numberFormat = workbook.createDataFormat();

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		CellStyle textStyle = workbook.createCellStyle();
		textStyle.setAlignment(CellStyle.ALIGN_CENTER);
		textStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		CellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00%"));
		numberStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// Configurando Header
		row = sheet.createRow(rownum++);
		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Problema");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Número de Ocorrência");

		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("% Ocorrências");
		
		cell = row.createCell(cellnum++);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("% Acumulado");
		
		Double soma = 0.0;
		Double acumulado = 0.0;
		for (Pareto pareto : paretoList) {
			soma += pareto.getQuantidade();
		}
		
		// Adicionando os dados dos produtos na planilha
		for (Pareto pareto : paretoList) {
			row = sheet.createRow(rownum++);
			cellnum = 0;
			Double ocorrencia = pareto.getQuantidade()/soma;
			acumulado += ocorrencia;

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(pareto.getProblema());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(textStyle);
			cell.setCellValue(pareto.getQuantidade());

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(ocorrencia);

			cell = row.createCell(cellnum++);
			cell.setCellStyle(numberStyle);
			cell.setCellValue(acumulado);

		}

		try {

			// Escrevendo o arquivo em disco
			FileOutputStream out = new FileOutputStream(new File("Gráfico de Pareto.xls"));
			workbook.write(out);
			out.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Simulando uma listagem de problemas
	private static List<Pareto> getPareto() {
		ArrayList<Pareto> paretoList = new ArrayList<>();
		paretoList.add(new Pareto("Combustível Adulterado", 23));
		paretoList.add(new Pareto("Bicos injetores entupidos", 18));
		paretoList.add(new Pareto("Tanque de Combustível com sujeira", 10));
		paretoList.add(new Pareto("Bomba de combustível com defeito", 7));
		paretoList.add(new Pareto("Filtro de combustível entupido", 4));
		paretoList.add(new Pareto("Cabo das velas com defeito", 2));
		return paretoList;
	}
}
