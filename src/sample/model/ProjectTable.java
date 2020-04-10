package sample.model;

import java.util.ArrayList;

public class ProjectTable {

    private ArrayList<ProjectModel> arrayProject;

    public ProjectTable() {
        this.arrayProject = new ArrayList<>();
    }


    public void ajouteProj(ProjectModel pm){
        this.arrayProject.add(pm);
    }

    public void deleteProj(int index){
        this.arrayProject.remove(index);
    }

    public ArrayList<ProjectModel> getArrayProject() {
        return arrayProject;
    }
}
