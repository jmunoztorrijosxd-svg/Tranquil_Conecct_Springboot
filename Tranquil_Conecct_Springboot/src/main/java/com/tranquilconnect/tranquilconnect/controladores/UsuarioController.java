package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// IMPORTS PARA EXPORTACIÓN (CORREGIDOS PARA EVITAR AMBIGÜEDAD)
import jakarta.servlet.http.HttpServletResponse;

// Específicos para Excel (Apache POI)
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// Específicos para PDF (OpenPDF / LibrePDF)
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
import java.util.Optional;

@Controller
@RequestMapping("/usuarios") 
public class UsuarioController { 

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Optional<Usuario> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String correo = authentication.getName();
            return usuarioRepository.findByCorreo(correo);
        }
        return Optional.empty();
    }
    
    @GetMapping
    public String listarUsuarios(
            @RequestParam(required = false) String keyword,      
            @RequestParam(required = false) String rol,          
            @RequestParam(required = false) String genero,       
            Model model) {
        
        List<Usuario> usuarios;
        if ((keyword != null && !keyword.isEmpty()) || (rol != null && !rol.isEmpty()) || (genero != null && !genero.isEmpty())) {
            usuarios = usuarioRepository.findByFiltros(keyword, rol, genero);
        } else {
            usuarios = usuarioRepository.findAll();
        }

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("keyword", keyword);
        model.addAttribute("rolFiltro", rol); 
        model.addAttribute("generoFiltro", genero); 
        
        getAuthenticatedUser().ifPresent(u -> model.addAttribute("usuario", u));
        return "usuarios"; 
    }

    @GetMapping("/exportar/excel")
    public void exportarExcel(@RequestParam(required = false) String keyword,
                              @RequestParam(required = false) String rol,
                              @RequestParam(required = false) String genero,
                              HttpServletResponse response) throws IOException {
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_Usuarios.xlsx");

        List<Usuario> usuarios = usuarioRepository.findByFiltros(keyword, rol, genero);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");

        org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        String[] columnas = {"ID", "Nombre", "Correo", "Teléfono", "Género", "Rol"};
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Usuario u : usuarios) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(u.getId());
            row.createCell(1).setCellValue(u.getNombre());
            row.createCell(2).setCellValue(u.getCorreo());
            row.createCell(3).setCellValue(u.getTelefono());
            row.createCell(4).setCellValue(u.getGenero());
            row.createCell(5).setCellValue(u.getRol());
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/exportar/pdf")
    public void exportarPdf(@RequestParam(required = false) String keyword,
                            @RequestParam(required = false) String rol,
                            @RequestParam(required = false) String genero,
                            HttpServletResponse response) throws IOException {
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_Usuarios.pdf");

        List<Usuario> usuarios = usuarioRepository.findByFiltros(keyword, rol, genero);

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        
        com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Reporte de Usuarios - Tranquil Connect", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        
        String[] headers = {"ID", "Nombre", "Correo", "Tel.", "Género", "Rol"};
        for (String h : headers) {
            // Se usa PdfPCell explícitamente para evitar choques con Cell de POI
            PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        for (Usuario u : usuarios) {
            table.addCell(String.valueOf(u.getId()));
            table.addCell(u.getNombre());
            table.addCell(u.getCorreo());
            table.addCell(u.getTelefono());
            table.addCell(u.getGenero());
            table.addCell(u.getRol());
        }

        document.add(table);
        document.close();
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
        getAuthenticatedUser().ifPresent(u -> model.addAttribute("usuario", u));
        model.addAttribute("usuarioAEditar", new Usuario());
        model.addAttribute("titulo", "Crear Nuevo Usuario");
        return "usuario_form"; 
    }
    
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            getAuthenticatedUser().ifPresent(u -> model.addAttribute("usuario", u));
            model.addAttribute("usuarioAEditar", usuarioOpt.get());
            model.addAttribute("titulo", "Editar Usuario: " + usuarioOpt.get().getNombre());
            return "usuario_form"; 
        }
        return "redirect:/usuarios";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuarioAEditar") Usuario usuario, RedirectAttributes ra) {
        try {
            if (usuario.getId() != null) {
                Optional<Usuario> existingUserOpt = usuarioRepository.findById(usuario.getId());
                if (existingUserOpt.isPresent()) {
                    Usuario existingUser = existingUserOpt.get();
                    usuario.setPassword(existingUser.getPassword());
                }
            } else {
                if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                    usuario.setPassword("temporal123"); 
                }
            }
            usuarioRepository.save(usuario);
            ra.addFlashAttribute("mensaje", "Usuario guardado exitosamente!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al guardar el usuario.");
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes ra) {
        try {
            usuarioRepository.deleteById(id);
            ra.addFlashAttribute("mensaje", "Usuario eliminado exitosamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al eliminar el usuario.");
        }
        return "redirect:/usuarios";
    }
    // --- MÉTODOS PARA PERFIL DE PSICÓLOGO ---

@GetMapping("/perfil")
public String editarMiPerfil(Model model) {
    Optional<Usuario> usuarioOpt = getAuthenticatedUser();
    if (usuarioOpt.isPresent()) {
        model.addAttribute("usuario", usuarioOpt.get());
        model.addAttribute("usuarioAEditar", usuarioOpt.get());
        model.addAttribute("titulo", "Mi Perfil Profesional");
        
        // Esta es la ruta correcta si lo mueves a la carpeta admin
        return "admin/perfil_psicologo"; 
    }
    return "redirect:/login";
}
@PostMapping("/perfil/guardar")
public String guardarPerfilPropio(@ModelAttribute("usuarioAEditar") Usuario datosEditados, RedirectAttributes ra) {
    Optional<Usuario> usuarioActualOpt = getAuthenticatedUser();
    
    if (usuarioActualOpt.isPresent()) {
        Usuario usuarioBD = usuarioActualOpt.get();
        
        // Actualizamos solo los campos permitidos para el profesional
        usuarioBD.setNombre(datosEditados.getNombre());
        usuarioBD.setTelefono(datosEditados.getTelefono());
        usuarioBD.setEspecialidad(datosEditados.getEspecialidad());
        usuarioBD.setTarjetaProfesional(datosEditados.getTarjetaProfesional());
        usuarioBD.setGenero(datosEditados.getGenero());

        usuarioRepository.save(usuarioBD);
        ra.addFlashAttribute("mensaje", "¡Perfil actualizado correctamente!");
    } else {
        ra.addFlashAttribute("error", "No se pudo identificar al usuario.");
    }
    return "redirect:/usuarios/perfil";
}
}