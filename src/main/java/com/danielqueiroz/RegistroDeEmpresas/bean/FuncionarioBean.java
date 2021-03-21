package com.danielqueiroz.RegistroDeEmpresas.bean;

import com.danielqueiroz.RegistroDeEmpresas.ejb.EmpresaDAO;
import com.danielqueiroz.RegistroDeEmpresas.ejb.FuncionarioDAO;
import com.danielqueiroz.RegistroDeEmpresas.model.Empresa;
import com.danielqueiroz.RegistroDeEmpresas.model.Funcionario;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class FuncionarioBean {

    @EJB
    private EmpresaDAO empresaDAO;

    @EJB
    private FuncionarioDAO funcionarioDAO;

    @Inject
    private FacesContext context;

    private UIComponent searchInputText;

    private Empresa empresa;
    private Funcionario funcionario;

    private List<Empresa> empresas;
    private List<Funcionario> funcionarios;

    private Integer empresaID;
    private Integer funcionarioID;

    @PostConstruct
    public void init() {
        empresas = this.empresaDAO.listarEmpresas();
        funcionarios = this.funcionarioDAO.buscarTodos();
    }

    public void carregarEmpresas(ComponentSystemEvent componentSystemEvent){
        this.empresas = this.empresaDAO.listarEmpresas();
    }

    public void carregarDadosEmpresa(ValueChangeEvent event){
        int id = Integer.parseInt(event.getNewValue().toString());
        this.empresa = this.empresaDAO.buscar(id);
    }


    public String cadastroFuncionario() {
        if (funcionario.getId() == null){
            empresa = empresaDAO.buscar(empresaID);
            funcionarioDAO.salvar(funcionario, empresa);
        } else {
            empresa = empresaDAO.buscar(empresaID);
            funcionarioDAO.atualizar(funcionario, empresa);
        }

        funcionario = null;

        return "crud-funcionarios?faces-redirect=true";
    }

    public String excluirFuncionario() {

        funcionarioDAO.excluir(funcionario, empresa);
        funcionario = null;

        return "crud-funcionarios?faces-redirect=true";
    }

    public void pesquisar(AjaxBehaviorEvent event){
        funcionario = funcionarioDAO.buscar(funcionarioID);

        if (funcionario == null){
            context.addMessage(searchInputText.getClientId(context), new FacesMessage("Funcionário não encontrada"));
        }

        funcionarioID = null;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public Integer getEmpresaID() {
        return empresaID;
    }

    public Integer getFuncionarioID() {
        return funcionarioID;
    }

    public void setFuncionarioID(Integer funcionarioID) {
        this.funcionarioID = funcionarioID;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public void setEmpresaID(Integer empresaID) {
        this.empresaID = empresaID;
    }

    public Funcionario getFuncionario() {
        if (funcionario == null){
            funcionario = new Funcionario();
        }
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public UIComponent getSearchInputText() {
        return searchInputText;
    }

    public void setSearchInputText(UIComponent searchInputText) {
        this.searchInputText = searchInputText;
    }
}
