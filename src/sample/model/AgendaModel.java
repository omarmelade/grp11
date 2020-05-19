package sample.model;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.scene.control.Label;

public class AgendaModel {

    private AgendaModel am;

    public Label reunionName;
    public JFXComboBox<Label> reunionGroup;
    private JFXDatePicker reunionDate;
    public JFXTimePicker debutHoraire;
    private JFXTimePicker finHoraire;

    public AgendaModel(Label reunionName, JFXComboBox<Label> reunionGroup, JFXDatePicker reunionDate, JFXTimePicker debutHoraire, JFXTimePicker finHoraire){
        this.reunionName = reunionName;
        this.reunionGroup = reunionGroup;
        this.reunionDate = reunionDate;
        this.debutHoraire = debutHoraire;
        this.finHoraire = finHoraire;
    }

    public Label getReunionName() {
        return reunionName;
    }

    public void setReunionName(Label reunionName) {
        this.reunionName = reunionName;
    }

    public JFXComboBox<Label> getReunionGroup() {
        return reunionGroup;
    }

    public void setReunionGroup(JFXComboBox<Label> reunionGroup) {
        this.reunionGroup = reunionGroup;
    }

    public JFXDatePicker getReunionDate() {
        return reunionDate;
    }

    public void setReunionDate(JFXDatePicker reunionDate) {
        this.reunionDate = reunionDate;
    }

    public JFXTimePicker getDebutHoraire() {
        return debutHoraire;
    }

    public void setDebutHoraire(JFXTimePicker debutHoraire) {
        this.debutHoraire = debutHoraire;
    }

    public JFXTimePicker getFinHoraire() {
        return finHoraire;
    }

    public void setFinHoraire(JFXTimePicker finHoraire) {
        this.finHoraire = finHoraire;
    }

    @Override
    public String toString() {
        return "AgendaModel{" +
                "am=" + am +
                ", reunionName=" + reunionName +
                ", reunionGroup=" + reunionGroup +
                ", reunionDate=" + reunionDate +
                ", debutHoraire=" + debutHoraire +
                ", finHoraire=" + finHoraire +
                '}';
    }
}
