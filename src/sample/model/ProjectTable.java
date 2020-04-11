package sample.model;

import java.util.ArrayList;

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
