package com.vaadin.tutorial.crm.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.backend.entity.Contact;
import com.vaadin.tutorial.crm.backend.servise.ContactService;


@Route("/")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {
    private final ContactForm form;
    Grid<Contact> grid = new Grid<>(Contact.class);
    TextField filetText = new TextField();
    private ContactService service;

    public MainView(ContactService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        ConfigureGrid();
        getToolbar();
        form = new ContactForm(service.findAll());
        form.addListener(ContactForm.SaveEvent.class, this::saveContact);
        form.addListener(ContactForm.CloseEvent.class,this::closeContact);
        form.addListener(ContactForm.DeleteEvent.class,this::deleteContact);

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();
        add(filetText, content);
        UpdateList();
        closeEditor();
    }

    private  void closeContact(ContactForm.CloseEvent event) {
        closeEditor();
    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        service.delete(event.getContact());
        UpdateList();
        closeEditor();
    }

    private void saveContact(ContactForm.SaveEvent event) {
        service.save(event.getContact());
        UpdateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void getToolbar() {

        filetText.setPlaceholder("Filter by name");
        filetText.setClearButtonVisible(true);
        filetText.setValueChangeMode(ValueChangeMode.LAZY);
        filetText.addValueChangeListener(e -> UpdateList());

        Button button = new Button("NewContact");
        button.addClickListener(buttonClickEvent -> NewContact());
        add(button);

    }

    private void NewContact() {
        grid.asSingleSelect().clear();
        form.setVisible(true);
        editContact(new Contact());
    }

    private void UpdateList() {
        grid.setItems(service.findAll(filetText.getValue()));
    }

    private void ConfigureGrid() {
        //?????
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(evt->editContact(evt.getValue()));
    }

    private void editContact(Contact value) {
        if (value == null) {
            closeEditor();
        } else {
            form.setContact(value);
            form.setVisible(true);
            addClassName("editing");
        }

    }

}
