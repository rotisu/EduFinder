/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import entities.Especialidades;
import entities.Roles;
import entities.TiposDocumento;
import entities.Usuarios;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import services.EspecialidadesFacadeLocal;
import services.RolesFacadeLocal;
import services.TiposDocumentoFacadeLocal;
import services.UsuariosFacadeLocal;

/**
 *
 * @author Jhon Deibys Torres
 */
@Named(value = "controllerDeUsuarios")
@SessionScoped
public class controllerDeUsuarios implements Serializable {

    /**
     * Creates a new instance of controllerDeUsuarios
     */
    Usuarios user = new Usuarios();
    Roles rol = new Roles();
    Especialidades esp = new Especialidades();
    TiposDocumento tipod = new TiposDocumento();

    @EJB
    UsuariosFacadeLocal ufl;
    @EJB
    RolesFacadeLocal rlf;
    @EJB
    EspecialidadesFacadeLocal spcl;
    @EJB
    TiposDocumentoFacadeLocal tp;

    List<SelectItem> listaRoles;
    List<SelectItem> listarEspecialidades;
    List<SelectItem> listarDocumento;
    List<SelectItem> listarAdmins;
   

    public Usuarios getUser() {
        return user;
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public Especialidades getEsp() {
        return esp;
    }

    public void setEsp(Especialidades esp) {
        this.esp = esp;
    }

    public TiposDocumento getTipod() {
        return tipod;
    }

    public void setTipod(TiposDocumento tipod) {
        this.tipod = tipod;
    }

    public controllerDeUsuarios() {
    }

    public List<Usuarios> listarUsuariosProfesionales() {

        try {
            return this.ufl.findByProfesional(2);
        } catch (Exception e) {
        }

        return null;
    }
    
     public List<Usuarios> listarUsuariosCliente() {

        try {
            return this.ufl.findByCliente(4);
        } catch (Exception e) {
        }

        return null;
    }
     
       public List<Usuarios> listarUsuariosAdministrador() {

        try {
            return this.ufl.findByCliente(1);
        } catch (Exception e) {
        }

        return null;
    }
     
    public List<SelectItem> listarUsuariosAdmin() {
        listarAdmins = new ArrayList<>();
        try {
            for (Usuarios user : this.ufl.findAll()) {
                if ("Administrador".equalsIgnoreCase(user.getRolId().getNombre())) {
                    SelectItem item = new SelectItem(user.getId(), user.getNombres());
                    listarAdmins.add(item);
                }

            }

            return listarAdmins;
        } catch (Exception e) {
        }
        return null;
    }

    public List<SelectItem> listarRoles() {
        listaRoles = new ArrayList<>();
        try {
            for (Roles rol : this.rlf.findAll()) {

                SelectItem item = new SelectItem(rol.getId(), rol.getNombre());
                listaRoles.add(item);

            }

            return listaRoles;
        } catch (Exception e) {
        }
        return null;
    }
    
    public List<SelectItem> listarRolCliente() {
    listaRoles = new ArrayList<>();
    try {
        for (Roles rol : this.rlf.findAll()) {
            if ("Cliente".equalsIgnoreCase(rol.getNombre())) {
                SelectItem item = new SelectItem(rol.getId(), rol.getNombre());
                listaRoles.add(item);
            }
        }
        return listaRoles;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

    public List<SelectItem> listarEspecialidades() {
        listarEspecialidades = new ArrayList<>();
        try {
            for (Especialidades espe : this.spcl.findAll()) {

                SelectItem item = new SelectItem(espe.getId(), espe.getNombre());
                listarEspecialidades.add(item);

            }

            return listarEspecialidades;
        } catch (Exception e) {
        }
        return null;
    }

    public List<SelectItem> listarTipodeDocu() {
        listarDocumento = new ArrayList<>();
        try {
            for (TiposDocumento tips : this.tp.findAll()) {

                SelectItem item = new SelectItem(tips.getId(), tips.getCodigo());
                listarDocumento.add(item);

            }

            return listarDocumento;
        } catch (Exception e) {
        }
        return null;
    }

    public String cancelar() {

        user = new Usuarios();
        rol = new Roles();
        tipod = new TiposDocumento();
        esp = new Especialidades();
        return "inicioDesarrollador.xhtml?faces-redirect=true";
    }

    public String crearUsuarios() {
        try {

            user.setRolId(rlf.find(rol.getId()));
            user.setEspecialidadId(spcl.find(esp.getId()));
            user.setFechaRegistro(new Date());
            user.setTipoDocumentoId(tp.find(tipod.getId()));
            this.ufl.create(user);
            user = new Usuarios();
            rol = new Roles();
            tipod = new TiposDocumento();
            esp = new Especialidades();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Registrado Exitosamente", "MSG_INFO");
            context.addMessage(null, fm);

            if (FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("Desarrollador")) {
                return "inicioDesarrollador.xhtml?faces-redirect=true";
            }else if(FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("registro1")){
                
             FacesContext contexto = FacesContext.getCurrentInstance();
            FacesMessage fme = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Registrado Exitosamente", "MSG_INFO");
            contexto.addMessage(null, fme);
                
            }

        } catch (Exception e) {
        }
        return null;
    }

    public void editarUsuarioUno(Usuarios user) {

        this.user = user;
        this.esp.setId(user.getEspecialidadId().getId());
        this.rol.setId(user.getRolId().getId());
        this.tipod.setId(user.getTipoDocumentoId().getId());
    }
    
    public String actualizarUsuario(){
    try {
        Usuarios usuarioExistente = ufl.find(user.getId());

        // Validar si la contraseña fue modificada o dejada vacía
        if (user.getContraseña() == null || user.getContraseña().trim().isEmpty()) {
            user.setContraseña(usuarioExistente.getContraseña());
        }
        
        user.setRolId(rlf.find(rol.getId()));
        user.setEspecialidadId(spcl.find(esp.getId()));
        user.setTipoDocumentoId(tp.find(tipod.getId()));
        
        this.ufl.edit(user);
        System.out.println("Usuario editado exitosamente - ID: " + user.getId());
        
        // Limpiar objetos
        user = new Usuarios();
        rol = new Roles();
        tipod = new TiposDocumento();
        esp = new Especialidades();
        
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Editado Exitosamente", "MSG_INFO");
        context.addMessage(null, fm);
        
    } catch (Exception e) {
        e.printStackTrace();
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al editar usuario: " + e.getMessage(), "MSG_ERROR");
        context.addMessage(null, fm);
    }
     return "inicioDesarrollador.xhtml?faces-redirect=true";
}
    
    
     public void EliminarUsuarios(Usuarios user) {
        try {
            this.ufl.remove(user);
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Eliminado Exitosamente", "MSG_INFO");
            context.addMessage(null, fm);
        } catch (Exception e) {
        }
    }
}
