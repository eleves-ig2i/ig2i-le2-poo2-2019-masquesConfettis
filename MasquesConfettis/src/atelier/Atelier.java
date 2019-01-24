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
public class Atelier 
{
    /**
     * Temps d'exécution total de l'atelier.
     */
    private int tempsTotalExec;
    
    /**
     * Somme totale des pénalités de chaque machine.
     */
    private double penaliteFinal;
    
    /**
     * Liste des machines de l'atelier.
     */
    private List<Machine> listeMachines;

    
    // CONSTRUCTEURS
    public Atelier(int nbMachines) {
        this.tempsTotalExec = 0;
        this.penaliteFinal = 0;
        
        nbMachines = Math.max(nbMachines, 1);
        this.listeMachines = new ArrayList<Machine>();
        
        for(int i =0; i < nbMachines; i++)
        {
            this.listeMachines.add( new Machine());
        }
               
    }

    
    // ACCESSEURS
    public int getTempsTotalExec() {
        return tempsTotalExec;
    }

    public double getPenaliteFinal() {
        return penaliteFinal;
    }

    @Override
    public String toString() {
        String ret = "Atelier{" + "tempsTotalExec=" + tempsTotalExec + ", penaliteFinal=" + penaliteFinal + ", listeMachines={"; // + listeMachines.toString() + '}';
        for( Machine m : this.listeMachines )
        {
            ret += m.toString();
            ret += ",\n";
        }
        ret += "}}";
        return ret;
    }
    
    
    // Q11
    /**
     * calcule les critères d’ordonnancement (temps total d’exécution et coût total de pénalité) et met à jour les attributs correspondants.
     */
    public void miseAJourCriteres()
    {
            this.penaliteFinal = 0;
            this.tempsTotalExec = 0;
            for( Machine m : this.listeMachines )
            {
                if( m.getDateDisponibilite() > this.tempsTotalExec )    // Le temps total d'execution est équivalent à la date de disponibilité la plus loin des machines.
                    this.tempsTotalExec = m.getDateDisponibilite();
                
                this.penaliteFinal += m.getPenaliteTotale();        // Les couts de pénalité de chaque machine s'ajoutent.
            }
    }
    
    
    // Q12
    /**
     * renvoie la machine en position posMachine de la liste des machines
     * @param posMachine la position de la machine.
     * @return la machine en position posMachine si posMachine est valide, null sinon.
     */
    private Machine getMachine(int posMachine)
    {
        if( 0 <= posMachine && posMachine < this.listeMachines.size() )
            return this.listeMachines.get(posMachine);
        else
            return null;
    }
    
    /**
     * ajoute la tâche t à la machine en position posMachine dans la liste des machines
     * @param t la tâche à ajouter.
     * @param posMachine la position de la machine, où l'on va ajouter la tâche.
     * @return true si l’ajout a été correctement effectué, false sinon
     */
    private boolean addTache(Tache t, int posMachine)
    {
        Machine m = this.getMachine(posMachine);
        if( m != null && m.addTache(t) )
        {
            return true;
        }
        else
            return false;
    }
    
    /**
     * renvoie un entier indiquant la position, dans la liste des machines, de la machine qui est disponible en premier
     * @return La position de la machine disponible en premier dans le temps. 
     */
    private int getPremiereMachineLibre()
    {
        int positionMachine=0;
        int tempsDispoPremiereMachine = Integer.MAX_VALUE;
        for(int i=0;i<this.listeMachines.size();i++)
        {
            if( this.listeMachines.get(i).getDateDisponibilite() < tempsDispoPremiereMachine)
            {
                tempsDispoPremiereMachine = this.listeMachines.get(i).getDateDisponibilite();
                positionMachine = i;        
            }
        }
        
        return positionMachine;
    }
    
    
    /**
     * 
     * @param taches 
     */
    public void ordonnancerTaches(List<Tache> taches)
    {
        this.addTache(taches.get(0),0);
        this.miseAJourCriteres();
        for(int i =1; i < taches.size(); i++)
        {
            this.addTache(taches.get(i), this.getPremiereMachineLibre());
            this.miseAJourCriteres();
        }
    }
    
    
    // TP4
    
    /**
     * Réactualise la pénalité à l'aide de la liste des taches de chaque machine
     */
    private void actualiserPenalite()
    {
        this.penaliteFinal = 0;
        for( Machine m : this.listeMachines)
        {
            this.penaliteFinal += m.getPenaliteTotale();
        }
    }
    
    
    /**
     * Améliore l'ordonnancement des tâches de façon à avoir une pénalité totale la plus faible possible, pour CHAQUE MACHINE.
     */
    public void ordonnancerCoutPenalite()
    {
        for(int numMachine = 0; numMachine < this.listeMachines.size(); numMachine++)
        {
            this.getMachine(numMachine).ordonnancerPenaliteHasard();
            this.miseAJourCriteres();
        }
    }
    
    
    
    
    
    
    public static void main(String[] args) {
        Atelier a = new Atelier(2);
        
        System.out.println("TEST SUJET TP3");
        Tache t1 = new Tache(300, 150, 2.5);
        Tache t2 = new Tache(400, 140, 1.5);
        Tache t3 = new Tache(200, 50, 2.5);
        Tache t4 = new Tache(200, 85, 1.0);
        Tache t5 = new Tache(160, 75, 0.5);
        Tache t6 = new Tache(500, 80, 1.5);
        
        ArrayList<Tache> listeTaches = new ArrayList<Tache>();
        listeTaches.add(t1);
        listeTaches.add(t2);
        listeTaches.add(t3);
        listeTaches.add(t4);
        listeTaches.add(t5);
        listeTaches.add(t6);
        
        a.ordonnancerTaches(listeTaches);
        
        
        System.out.println(a);
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
