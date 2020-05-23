package sample.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjectTable {

    private ArrayList<ProjectModel> arrayProject;
    private int nbProj;

    public ProjectTable() {
        this.arrayProject = new ArrayList<>();
        this.nbProj = 0;
    }


    public void ajouteProj(ProjectModel pm) {
        this.arrayProject.add(pm);
        this.nbProj += 1;
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        return map.keySet()
                .stream()
                .filter(key -> value.equals(map.get(key)))
                .findFirst().get();
    }

    public Map<Integer, String> ListNameId() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (ProjectModel p : arrayProject) {
            map.put(p.getId_projet(), p.getNom());
        }
        return map;
    }

    public ArrayList<String> ListNProj() {
        ArrayList<String> projets = new ArrayList<String>();
        for (ProjectModel p : arrayProject) {
            projets.add(p.getNom());

        }
        return (projets);

    }

    public void deleteProj(int index) {
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
