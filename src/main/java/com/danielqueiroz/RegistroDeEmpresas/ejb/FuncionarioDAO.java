package com.danielqueiroz.RegistroDeEmpresas.ejb;

import com.danielqueiroz.RegistroDeEmpresas.model.Empresa;
import com.danielqueiroz.RegistroDeEmpresas.model.Funcionario;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class FuncionarioDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Funcionario buscar(Integer id){
        return entityManager.find(Funcionario.class, id);
    }

    public void salvar(Funcionario funcionario, Empresa empresa){
        empresa.getFuncionarios().add(funcionario);
        funcionario.setEmpresa(empresa);
        entityManager.merge(empresa);
        entityManager.persist(funcionario);
    }

    public void atualizar(Funcionario funcionario,Empresa empresa){

        empresa.getFuncionarios().add(funcionario);
        funcionario.setEmpresa(empresa);
        entityManager.merge(empresa);
        entityManager.merge(funcionario);
    }

    public void excluir(Funcionario funcionario, Empresa empresa){
        empresa.getFuncionarios().remove(funcionario);
        funcionario.setEmpresa(null);
        entityManager.merge(empresa);
        entityManager.remove(entityManager.contains(funcionario) ? funcionario : entityManager.merge(funcionario));
    }

    public List<Funcionario> buscarTodos() {
        return  entityManager.createQuery("select f from Funcionario f", Funcionario.class).getResultList();
    }

    public List<Funcionario> buscarFuncionariosDeEmprera(int id){
        TypedQuery<Funcionario> query = entityManager
                .createQuery("select f from Funcionario f where f.empresa.id = :id", Funcionario.class)
                .setParameter("id", id);
        return query.getResultList();
    }

}
