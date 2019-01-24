/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordonnancement;

import atelier.*;
import java.util.*;

/**
 * Classe permettant de tester divers algorithmes de tri afin d'optimiser la production dans l'atelier.
 * @author user
 */
public class Ordonnancement 
{
    private List<Tache> ensTaches;  /**! L’ensemble des tâches à ordonnancer. */
    private int nbMachines;     /**! Le nombre de machines dans l’atelier, */
    private Atelier atelier;          /**! Atelier sur lequel on va utiliser les méthodes d'ordonnancement.*/
    
    /**
     * constructeur par données du nombre de machines de l’atelier et de la liste des tâches à ordonnancer.
     * Si nbMachines strictement inférieure 1, alors on considère que l'atelier utilisera une seule machine.
     */
    public Ordonnancement(int nbMachines, List<Tache> ensTachesAEffectuer)
    {
        nbMachines = Math.max(nbMachines, 1);
        
        this.atelier = new Atelier(nbMachines);
        this.nbMachines = nbMachines;
        
        /*
        this.ensTaches = new ArrayList<>();
        Iterator<Tache> it = ensTachesAEffectuer.iterator();
        while( it.hasNext() )
        {
            this.ensTaches.add( it.next() );
        }
        // Equivalent à :
        */
        
        this.ensTaches = new ArrayList<>(ensTachesAEffectuer);
        
    }
    
    
    /**
     * permet de mettre à jour l’attribut atelier avec nbMachines machines.
     */
    private void resetAtelier()
    {
        this.atelier = new Atelier(this.nbMachines);    // Possible en Java grâce au garbage collector
    }
    
    
    /**
     * réalise une copie de la liste des tâches en paramètres, puis ordonnance ces tâches sur l’atelier.
     * @param taches La liste des tâches à ordonnacer.
     */
    private void ordonnancerCopieTaches(List<Tache> taches)
    {
        // List<Tache> copieTaches = new ArrayList<>(taches);   // Effectue une copie de la liste, MAIS la liste originale et la liste copiée pointent sur les MEMES taches.
        List<Tache> copieTaches = new ArrayList<>();
        for(Tache t : taches)
        {
            copieTaches.add ( new Tache(t) );
        }
        
        this.atelier.ordonnancerTaches(copieTaches);
    }
    
    
    /**
     * remet à jour l’atelier, puis y ordonnance les tâches avec le même modus operandi que celui proposé par Monsieur Sorcier.
     */
    public void ordonnancerMonsieurSorcier()
    {
        this.resetAtelier();
        this.ordonnancerCopieTaches(this.ensTaches);
    }
    
    
    /**
     * qui affiche les critères (temps de production,pénalités) de l’atelier. Si verbose vaut true, alors on affiche également l’atelier (ceci peut être utile pour du débogage).
     * @param verbose Si ce paramètre vaut true, alors on affiche également l’atelier (ceci peut être utile pour du débogage).
     */
    public void afficher(boolean verbose)
    {
        System.out.println("Critères de l'atelier :");
        System.out.println("Temps de production = " + this.atelier.getTempsTotalExec());
        System.out.println("Pénalité = " + this.atelier.getPenaliteFinal() );
        
        if( verbose )
            System.out.println(this.atelier);
    }
    
    
    /**
     * trie les tâches par temps d’exécution croissant avant de faire l’affectation aux machines selon le modus operandi de Monsieur Sorcier
     */
    public void ordonnancerTempsExecCroissant()
    {
        Collections.sort(this.ensTaches);       // Méthode statique.
        this.ordonnancerMonsieurSorcier();
    }
    
    
    /**
     * trie les tâches par temps d’exécution décroissant avant de faire l’affectation aux machines selon le modus operandi de Monsieur Sorcier
     */
    public void ordonnancerTempsExecDecroissant()
    {
        Collections.sort(this.ensTaches);       // Méthode statique.
        Collections.reverse(this.ensTaches);
        this.ordonnancerMonsieurSorcier();
    }
    
    
    /**
     * Trie les tâches de façon aléatoire avant d'effectuer l'affectation aux machines selon le modus operandi. On effectue ces opérations 10 000 fois.
     */
    public void ordonnancerTempsExecHasard()
    {
        Atelier meilleurAtelier = this.atelier;
        
        int meilleurTempsExec = Integer.MAX_VALUE;
        for(int i = 0; i < 10000; i++)
        {
            Collections.shuffle(this.ensTaches);
            this.ordonnancerMonsieurSorcier();
            if( this.atelier.getTempsTotalExec() < meilleurTempsExec )
            {
                meilleurAtelier = this.atelier;
                meilleurTempsExec = this.atelier.getTempsTotalExec();
            }
        } 
        
        this.atelier = meilleurAtelier;
    }
    
    
    
    /**
     * Après avoir ordonnancé les tâches, affiche les valeurs du temps total d’exécution et du coût total de pénalité, 
     * puis essaye d’améliorer l’ordonnancement de sorte que le temps total d’exécution reste le même et le coût total de pénalité diminue.
     */
    public void comparerOrdo()
    {
        this.ordonnancerTempsExecHasard();  // Meilleure méthode pour l'ordonnance des tâches pour les machines.
        this.afficher(false);
        this.atelier.ordonnancerCoutPenalite();
    }
    
    
    public static void main(String[] args) 
    {
        /*  
        // Test question 3
        List<Tache> taches = new ArrayList<>();
        taches.add(new Tache(100));
        taches.add(new Tache(150));
        Ordonnancement ordo = new Ordonnancement(1, taches);
        ordo.ordonnancerCopieTaches(taches);
        System.out.println("Taches initiales :"); // Ne doivent pas avoir changé
        System.out.println(taches.get(0));
        System.out.println(taches.get(1));
        System.out.println("Taches ordonnancees :"); // Les dates de débutsont mises à jour
        System.out.println(ordo.atelier);
        */
        
        /*
        // Test question 5
        // Le test a déjà été effectué dans la méthode main de la classe Atelier.
        */
        
    }
    
    
}
