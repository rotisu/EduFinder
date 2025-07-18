/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import entities.Ciudades;
import entities.Universidades;
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
import services.CiudadesFacadeLocal;
import services.UniversidadesFacadeLocal;
import services.UsuariosFacadeLocal;

/**
 *
 * @author Jhon Deibys Torres
 */
@Named(value = "controllerUniversity")
@SessionScoped
public class controllerUniversity implements Serializable {

    Universidades uni = new Universidades();
    Ciudades ciu = new Ciudades();
    Usuarios user = new Usuarios();
    private Integer idUsuarioSeleccionado;

    List<SelectItem> listaCiudades;

    @EJB
    UniversidadesFacadeLocal unfl;
    @EJB
    CiudadesFacadeLocal cl;
    @EJB
    UsuariosFacadeLocal ufl;

    public controllerUniversity() {
    }

    public Integer getIdUsuarioSeleccionado() {
        return idUsuarioSeleccionado;
    }

    public void setIdUsuarioSeleccionado(Integer idUsuarioSeleccionado) {
        this.idUsuarioSeleccionado = idUsuarioSeleccionado;
    }

    public Usuarios getUser() {
        return user;
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }

    public Universidades getUni() {
        return uni;
    }

    public void setUni(Universidades uni) {
        this.uni = uni;
    }

    public Ciudades getCiu() {
        return ciu;
    }

    public void setCiu(Ciudades ciu) {
        this.ciu = ciu;
    }

    public List<Universidades> listarUniversidades() {

        try {
            return this.unfl.findAll();
        } catch (Exception e) {
        }

        return null;
    }

    public List<SelectItem> listarCiudades() {
        listaCiudades = new ArrayList<>();
        try {
            for (Ciudades ciud : this.cl.findAll()) {

                SelectItem item = new SelectItem(ciud.getId(), ciud.getNombre());
                listaCiudades.add(item);

            }

            return listaCiudades;
        } catch (Exception e) {
        }
        return null;
    }

    public String registrarUniversidades() {
        try {
            uni.setFechaRegistro(new Date());
            uni.setCiudadId(cl.find(ciu.getId()));
            Usuarios usuarioSeleccionado = ufl.find(idUsuarioSeleccionado);
            uni.setCreadoPor(usuarioSeleccionado);
            this.unfl.create(uni);
            uni = new Universidades();
            ciu = new Ciudades();

            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Universidad Registrada Exitosamente", "MSG_INFO");
            context.addMessage(null, fm);

            if (FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("Universidad")) {
                return "inicioSesionUniversidad.xhtml?faces-redirect=true";
            }

        } catch (Exception e) {
        }
        return null;
    }

    public String EliminarUniversidad(Universidades uni) {
        try {
            this.unfl.remove(uni);
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Universidad Eliminada Exitosamente", "MSG_INFO");
            context.addMessage(null, fm);
        } catch (Exception e) {
        }
        return "inicioSesionUniversidad.xhtml?faces-redirect=true";
    }

    public void editarUniversidad(Universidades uni) {

        this.uni = uni;

        // Evitar null pointer en ciudad
       
            this.ciu.setId(uni.getCiudadId().getId());
            this.idUsuarioSeleccionado = uni.getCreadoPor().getId();

    }

    public String actualizarUniversidades() {
        try {

          this.uni.setCiudadId(cl.find(ciu.getId()));
            Usuarios usuarioSeleccionado = ufl.find(idUsuarioSeleccionado);
            this.uni.setCreadoPor(usuarioSeleccionado);

            this.unfl.edit(uni);
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Editado Exitosamente", "MSG_INFO");
            context.addMessage(null, fm);

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario Editado Exitosamente: " + e.getMessage(), "MSG_ERROR");
            context.addMessage(null, fm);
        }
        return "inicioSesionUniversidad.xhtml?faces-redirect=true";
    }
}
