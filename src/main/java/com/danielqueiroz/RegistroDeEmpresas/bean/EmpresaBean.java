package com.danielqueiroz.RegistroDeEmpresas.bean;

import com.danielqueiroz.RegistroDeEmpresas.ejb.EmpresaDAO;
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
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class EmpresaBean {

    @EJB
    private EmpresaDAO empresaDAO;

    @Inject
    private FacesContext context;

    private UIComponent searchInputText;

    private Integer empresaID;
    private Empresa empresa;

    private List<Empresa> empresas;

    @PostConstruct
    public void init(){
        this.getEmpresas();
    }

    public String cadastraEmpresa() {
        if (empresa.getId() == null){
            empresaDAO.salvar(empresa);
        } else {
            empresaDAO.atualizar(empresa);
        }

        empresa = null;

        return "crud-empresas?faces-redirect=true";
    }

    public void pesquisar(AjaxBehaviorEvent event){
        empresa = empresaDAO.buscar(empresaID);

        if (empresa == null){
            context.addMessage(searchInputText.getClientId(context), new FacesMessage("Empresa não encontrada"));
        }

        empresaID = null;


    }

    public String excluir(){
        empresaDAO.excluir(empresa);
        empresa = null;

        return "crud-empresas?faces-redirect=true";
    }

    public List<Empresa> getEmpresas() {
        if (empresas == null) {
            empresas = empresaDAO.listarEmpresas();
        }
        return empresas;
    }

    public List<Empresa> empresasAtualizadas(AjaxBehaviorEvent event){
        if (empresas == null) {
            empresas = empresaDAO.listarEmpresas();
        }
        return empresas;
    }

    public Integer getEmpresaID() {
        return empresaID;
    }

    public void setEmpresaID(Integer empresaID) {
        this.empresaID = empresaID;
    }

    public Empresa getEmpresa() {
        if (empresa == null){
            empresa = new Empresa();
        }
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public UIComponent getSearchInputText() {
        return searchInputText;
    }

    public void setSearchInputText(UIComponent searchInputText) {
        this.searchInputText = searchInputText;
    }

}
