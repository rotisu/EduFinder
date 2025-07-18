/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;


import entities.Usuarios;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import services.UsuariosFacadeLocal;



/**
 *
 * @author Jhon Deibys Torres
 * 
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private String usuario;
    private String contrasenna;
    private String correo;
  

   
    private Usuarios user = new Usuarios();
    
    @EJB
    UsuariosFacadeLocal ufl;

    public Login(String usuario, String contrasenna, String correo) {
        this.usuario = usuario;
        this.contrasenna = contrasenna;
        this.correo = correo;
    }
    
    

      public Login() {
          System.out.println("Bean Login instanciado correctamente");
    }
  

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }
    
    public String iniciarSesion(){
       user = this.ufl.iniciarSesion(usuario, contrasenna, correo);
        if (user.getNumeroDocumento() != null && user.getRolId().getId() == 1) {
            HttpSession sesion = ( HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            sesion.setAttribute("usuario", usuario);
            return "/views/inicioDesarrollador.xhtml?faces-redirect=true";
        }else if(user.getNumeroDocumento() != null && user.getRolId().getId() == 4){
             HttpSession sesion = ( HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            sesion.setAttribute("usuario", usuario);
            return "/views/perfil.xhtml?faces-redirect=true";
        }
        else{
            FacesContext context =  FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "nombre de usuario y/o contraseña invalidos", "MSG_ERROR");
            context.addMessage(null, fm);
            return null;
        }
    }
    
  
    
   public String cerrarSesion(){
       
       FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
       return "/inicioDeSesion1.xhtml?faces-redirect=true";
   }
}
