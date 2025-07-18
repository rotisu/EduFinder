/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Usuarios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jhon Deibys Torres
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> implements UsuariosFacadeLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }

    @Override
    public Usuarios iniciarSesion(String nombres, String contrasenna, String correo) {
         Query query = em.createQuery("SELECT U FROM Usuarios U WHERE U.nombreUsuario=:nombres AND U.contraseña=:password AND U.correoElectronico=:correo");
        query.setParameter("nombres", nombres);
        query.setParameter("password", contrasenna);
         query.setParameter("correo", correo);
        
        
        try {
            return (Usuarios) query.getSingleResult();
        } catch (Exception e) {
        }
        
         Usuarios userInexistente = new Usuarios();
        return  userInexistente;
    }

    @Override
    public List<Usuarios> findByProfesional(int rolid) {
          return em.createQuery("SELECT u FROM Usuarios u WHERE u.rolId.id=:rolId")
             .setParameter("rolId", rolid)
             .getResultList();
    }

    @Override
    public List<Usuarios> findByCliente(int clienteid) {
        return em.createQuery("SELECT u FROM Usuarios u WHERE u.rolId.id=:rolId")
             .setParameter("rolId", clienteid)
             .getResultList();
    }

    @Override
    public List<Usuarios> findByAdmin(int admin) {
         return em.createQuery("SELECT u FROM Usuarios u WHERE u.rolId.id=:rolId")
             .setParameter("rolId", admin)
             .getResultList();
    }
    
}
