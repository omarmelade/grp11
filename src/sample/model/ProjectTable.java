package sample.model;

import sample.API.Project;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class ProjectTable {

    private ArrayList<ProjectModel> arrayProject;
    private int nbProj;

    public ProjectTable() {
        this.arrayProject = new ArrayList<>();
        this.nbProj = 0;
    }


    public void ajouteProj(ProjectModel pm){
        this.arrayProject.add(pm);
        this.nbProj += 1;
    }


    public ArrayList<String> ListNProj() throws SQLException {
        ArrayList<String> projets = new ArrayList<String>();
        for (ProjectModel p : arrayProject) {
            projets.add(p.getNom());

        }
        return(projets);

    }
    public void deleteProj(int index){
        this.arrayProject.remove(index);
        this.nbProj -= 1;
    }

    public ArrayList<ProjectModel> getArrayProject() {
        return arrayProject;
    }

    public int getNbProj() {
        return nbProj;
    }

    @Override
    public String toString() {
        String res = "";
        for (ProjectModel p: this.arrayProject) {
            res += p.toString() + "\n";
        }
        return res;
    }
}
