package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Grupo;
import com.tranquilconnect.tranquilconnect.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// IMPORTS PARA EXPORTACIÓN
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

import java.io.IOException;
import java.util.List;

@Controller
public class GruposcrudController {

    @Autowired
    private GrupoRepository grupoRepository;

    // --- Listar con Filtrado ---
    @GetMapping("/gruposcrud") 
    public String verGrupos(@RequestParam(required = false) String keyword, Model model) {
        List<Grupo> lista;
        if (keyword != null && !keyword.isEmpty()) {
            lista = grupoRepository.findByFiltros(keyword);
        } else {
            lista = grupoRepository.findAll();
        }
        model.addAttribute("listaDeGrupos", lista);
        model.addAttribute("keyword", keyword);
        return "gruposcrud"; 
    }

    // --- Nuevo Grupo (Ruta cambiada para evitar conflicto) ---
    @GetMapping("/gruposcrud/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("grupo", new Grupo());
        return "grupos_form"; 
    }

    // --- Editar Grupo ---
    @GetMapping("/gruposcrud/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Grupo grupo = grupoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de grupo inválido:" + id));
        model.addAttribute("grupo", grupo);
        return "grupos_form"; 
    }

    // --- Guardar/Actualizar Grupo ---
    @PostMapping("/gruposcrud/guardar")
    public String guardarGrupo(@ModelAttribute("grupo") Grupo grupo, RedirectAttributes flash) {
        grupoRepository.save(grupo);
        flash.addFlashAttribute("mensaje", "Grupo guardado con éxito.");
        return "redirect:/gruposcrud";
    }

    // --- Eliminar Grupo ---
    @GetMapping("/gruposcrud/eliminar/{id}")
    public String eliminarGrupo(@PathVariable("id") Long id, RedirectAttributes flash) {
        try {
            grupoRepository.deleteById(id);
            flash.addFlashAttribute("mensaje", "Grupo eliminado correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se pudo eliminar el grupo. Verifique dependencias.");
        }
        return "redirect:/gruposcrud";
    }

    // --- Exportar a Excel ---
    @GetMapping("/gruposcrud/exportar/excel")
    public void exportarExcel(@RequestParam(required = false) String keyword, 
                              HttpServletResponse response) throws IOException {
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_Grupos.xlsx");

        List<Grupo> lista = (keyword != null && !keyword.isEmpty()) 
                            ? grupoRepository.findByFiltros(keyword) 
                            : grupoRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Grupos de Apoyo");

        Row headerRow = sheet.createRow(0);
        String[] columnas = {"ID", "Nombre", "Descripción", "Nº Miembros", "Motivo Salida"};
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
        }

        int rowNum = 1;
        for (Grupo g : lista) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(g.getIdGrupo());
            row.createCell(1).setCellValue(g.getNombre());
            row.createCell(2).setCellValue(g.getDescripcion());
            row.createCell(3).setCellValue(g.getNumMiembros() != null ? g.getNumMiembros() : 0);
            row.createCell(4).setCellValue(g.getMotivoSalida());
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // --- Exportar a PDF ---
    @GetMapping("/gruposcrud/exportar/pdf")
    public void exportarPdf(@RequestParam(required = false) String keyword, 
                            HttpServletResponse response) throws IOException {
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_Grupos.pdf");

        List<Grupo> lista = (keyword != null && !keyword.isEmpty()) 
                            ? grupoRepository.findByFiltros(keyword) 
                            : grupoRepository.findAll();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        
        com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Reporte de Grupos - Tranquil Connect", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        
        String[] headers = {"ID", "Nombre", "Descripción", "Miembros", "Motivo"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        for (Grupo g : lista) {
            table.addCell(String.valueOf(g.getIdGrupo()));
            table.addCell(g.getNombre());
            table.addCell(g.getDescripcion());
            table.addCell(String.valueOf(g.getNumMiembros()));
            table.addCell(g.getMotivoSalida());
        }

        document.add(table);
        document.close();
    }
}