package com.vaadin.tutorial.crm;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("/")
public class MainView extends VerticalLayout {


    public MainView() {
        Button button = new Button("Hello");
        DatePicker datePicker = new DatePicker("Pick a date");
        HorizontalLayout horizontalLayout = new HorizontalLayout(button, datePicker);
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.END);
        button.addClickListener(click -> add(new Paragraph("SOSI")));


        add(horizontalLayout);


    }

}
