/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atelier;

import java.util.*;

/**
 *
 * @author user
 */
public class Machine 
{
    // Attributs
    /**
     * . l’instant à partir duquel on peut démarrer une nouvelle tâche sur la machine.
     */
    private int dateDisponibilite;
    
    /**
     * le cumul des coûts de pénalité liés à cette machine.
     */
    private double penaliteTotale;
    
    
    private List<Tache> listeTaches;
    
    
    // CONSTRUCTEURS
    public Machine() 
    {
        this.listeTaches = new LinkedList<>();
        this.dateDisponibilite = 0;
        this.penaliteTotale = 0.0;
    }
    
    
    // MUTATEURS ET ACCESSEURS
    public int getDateDisponibilite() {
        return dateDisponibilite;
    }

    public double getPenaliteTotale() {
        return penaliteTotale;
    }
    
    
    // Q7
    /**
     * Pour rappel, Monsieur Sorcier propose que lorsqu’une
     * tâche est ajoutée à la liste, elle soit exécutée dès que la machine est disponible,
     * pour ne pas avoir de temps morts. 
     * ajoute la tâche t à la liste des tâches exécutées par la machine.
     * @param t La tâche à ajouter.
     * @return true si l’ajout a bien été effectué, false sinon.
     */
    public boolean addTache(Tache t)
    {
        if( t != null && t.peutEtreAffectee() )
        {
            t.setDateDebut(this.dateDisponibilite);         // Date de début de la nouvelle tâche =====> Date de disponibilité de la machine où l'on met la tâche.
            this.dateDisponibilite += t.getDureeExecution();     // Nouvelle date de disponibilité de la machine = date actuelle de dispo + durée exécution de la dernière tâche ajoutée
            this.penaliteTotale += t.coutPenalite();
            this.listeTaches.add(t);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString() {
        String ret = "Machine{" + "dateDisponibilite=" + dateDisponibilite + ", penaliteTotale=" + penaliteTotale + ",\nlisteTaches={\n"; // + listeTaches + '}';
        Iterator<Tache> itTache = this.listeTaches.iterator();
        while(  itTache.hasNext()   )
        {
            Tache elt = itTache.next();
            ret += elt.toString();
            ret += ",\n";
        }
        ret += "}}\n";
        return ret;
    }
    
    
    // TP4
    /**
     * Réactualise la pénalité à l'aide de la liste des taches de la machine.
     */
    private void actualiserPenalite()
    {
        this.penaliteTotale = 0;
        for( Tache t : this.listeTaches)
        {
            this.penaliteTotale += t.coutPenalite();
        }
    }
    
    /**
     * Améliore l'ordonnancement des tâches de façon à avoir une pénalité totale la plus faible possible.
     */
    public void ordonnancerPenaliteHasard()
    {
        List<Tache> meilleureListeTaches = this.listeTaches;
        double penalitePlusFaible = Integer.MAX_VALUE;
        
        for(int i = 0; i < 10000; i++)
        {
            Collections.shuffle(this.listeTaches);
            this.actualiserPenalite();
            if( this.penaliteTotale < penalitePlusFaible )
            {
                meilleureListeTaches = this.listeTaches;
                penalitePlusFaible = this.penaliteTotale;
            }
        } 
        
        this.listeTaches = meilleureListeTaches;
    }
    
    
    public static void main(String[] args) {
        Machine m = new Machine();
        
        System.out.println("TEST SUJET TP");
        Tache t1 = new Tache(300, 150, 2.5);
        Tache t2 = new Tache(400, 140, 1.5);
        Tache t3 = new Tache(200, 50, 2.5);
        Tache t4 = new Tache(200, 85, 1.0);
        Tache t5 = new Tache(160, 75, 0.5);
        Tache t6 = new Tache(500, 80, 1.5);
        
        m.addTache(t1);
        m.addTache(t2);
        m.addTache(t3);
        m.addTache(t4);
        m.addTache(t5);
        m.addTache(t6);
        
        System.out.println(m.toString());
    }
    
    
    
}
