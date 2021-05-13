package org.fis.student.sre.controllers;

import org.fis.student.sre.model.Appointment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fis.student.sre.model.ClickListener;

import java.io.IOException;
import java.util.Objects;

public class AppointmentLayoutLabelController {
    @FXML
    private Text detailsAboutAppointment;

    private Appointment appointment;

    private ClickListener clickListener;
    @FXML
    public void click(MouseEvent event) throws IOException {
        clickListener.onClickListener(appointment);
        Parent homepageViewParent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("appointmentPage.fxml")));
        Scene homepageViewScene = new Scene(homepageViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(homepageViewScene);
        window.show();
    }

    public void setData(Appointment appointment, ClickListener clickListener) {
        this.appointment = appointment;
        this.clickListener = clickListener;
        detailsAboutAppointment.setText(appointment.toString());
    }
}
