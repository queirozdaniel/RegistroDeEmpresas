package com.danielqueiroz.RegistroDeEmpresas.ejb;

import com.danielqueiroz.RegistroDeEmpresas.model.Empresa;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class EmpresaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Empresa> listarEmpresas(){
        return  entityManager.createQuery("select e from Empresa e", Empresa.class).getResultList();
    }

    public Empresa buscar(int id) {
        return entityManager.find(Empresa.class, id);
    }

    public void salvar(Empresa empresa){
        entityManager.persist(empresa);
    }

    public void atualizar(Empresa empresa){
        entityManager.merge(empresa);
    }

    public void excluir(Empresa empresa){
        empresa = buscar(empresa.getId());
        entityManager.remove(empresa);
    }

}
