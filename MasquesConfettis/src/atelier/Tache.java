/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atelier;

/**
 *
 * @author nsalez
 */
public class Tache implements Comparable<Tache> {       // On implémente l'interface Comparable dans le TP4
    
    // ATTRIBUTS
    /**
     * Identifiant de la tâche (est unique)
     */
    private int id;
    
    /**
     * Date de début de la tâche en min (peut être egal à 0 ou plus) 
     */
    private int dateDebut;
    
    /**
     * Date de livraison de la tâche en min (doit être supérieure ou égale à 2*dureeExecution)
     */
    private int dateLivraison;
    
    /**
     * Duree d'execution de la tache en min (doit être supérieure ou égale à 50 min)
     */
    private int dureeExecution;
    
    /**
     * Cout unitaire de la tâche (par minute de retard)
     */
    private double penalite;
    
    /**
     * Variable utilisée pour s'assurer de l'unicité des identifiants.
     */
    private static int maxId = 0;
    
    /**
     * Variable utilisée pour s'assurer que la durée d'execution soit supérieure ou égale à cette dernière.
     */
    private final static int MIN_DUREE_EXEC = 50;
    
    
    
    // CONSTRUCTEURS
    public Tache()
    {
        maxId++;
        this.id = maxId;
        
        this.dureeExecution = MIN_DUREE_EXEC;
        
        this.dateDebut = -1;    // Pour indiquer que cette date n'a pas été affectée (Elle le sera par la classe Machine) 
        this.penalite = 0;
      
        this.dateLivraison = Integer.MAX_VALUE;

    }
    
    /**
     * Utilisé dans le TP4, afin de réaliser une copie en profondeur pour la méthode ordonnancerCopieTaches
     */
    public Tache(Tache t)
    {
        if( t != null)
        {
            this.id = t.id;
            this.dateDebut = t.dateDebut;
            this.dateLivraison = t.dateLivraison;
            this.penalite = t.penalite;
            this.dureeExecution = t.dureeExecution;
        }
        
    }

    
    public Tache(int dureeExecution) 
    {
        this();
        
        if( dureeExecution >= MIN_DUREE_EXEC)
            this.dureeExecution = dureeExecution;
    }

    
    public Tache(int dateLivraison, int dureeExecution, double penalite) 
    {
        this(dureeExecution);
        
        if( dateLivraison >= 2*(this.dureeExecution) )
            this.dateLivraison = dateLivraison;
        else
            this.dateLivraison = 2*(this.dureeExecution);
        
        if( penalite >= 0)
            this.penalite = penalite;
    }
    
    
    // ACCESSEURS & MUTATEURS
    public int getId() {
        return id;
    }

    public int getDateDebut() {
        return dateDebut;
    }

    public int getDateLivraison() {
        return dateLivraison;
    }

    public int getDureeExecution() {
        return dureeExecution;
    }

    public double getPenalite() {
        return penalite;
    }

    
    // TO STRING
    @Override
    public String toString() {
        return "Tache{" + "id=" + id + ", dateDebut=" + dateDebut + ", dateLivraison=" + dateLivraison + ", dureeExecution=" + dureeExecution + ", penalite=" + penalite + '}';
    }
    
    
    // Q7
    public void setDateDebut(int dateDebut) 
    {
        if( dateDebut >= 0 && this.dateDebut < 0 )
            this.dateDebut = dateDebut;
    }

    
    /**
     * Permet d'obtenir la date de fin d'une tâche
     * @return la date de fin de la tâche en question.
     */
    public int dateFinExecution()
    {
        return (this.dateDebut + this.dureeExecution);
    }
 
    
    /**
     * Permet d'obtenir le coût associé à une tâche.
     * @return le coût de la tâche.
     */
    public double coutPenalite()
    {
        return this.penalite* Math.max( this.dateFinExecution() - dateLivraison, 0);     // cf formule du PDF du TP
    }
    
    
    public boolean peutEtreAffectee()
    {
        return this.dateDebut < 0;
    }
    
    // TP4
    @Override
    public int compareTo(Tache o) 
    {
        if ( o.dureeExecution == this.dureeExecution)   // SI 2 instances ont la même durée d'exécution ALORS on compare leur ID
            return this.id - o.id;
        
        return this.dureeExecution - o.dureeExecution;  // Si this > obj alors on renvoie un positif
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        hash = 67 * hash + this.dureeExecution;
        return hash;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tache other = (Tache) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.dureeExecution != other.dureeExecution) {
            return false;
        }
        return true;
    }

    
    
    
    // TESTS
    public static void main(String[] args) {
        
        
        Tache t1 = new Tache();
        Tache t2 = new Tache(50);
        Tache t3 = new Tache(130,60,2.6);
        Tache t4 = new Tache(t2);
        Tache t5 = new Tache(130,70,2.6);
        
        /*
        // TP3 - Question ?
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
        */
        
       
        /*
        // TP3 - Question 7
        t3.setDateDebut(80);
        System.out.println(t3.coutPenalite());
        System.out.println(t3);
        */
        
        // TP4 - Question 7 - compareTo
        /*
        // On a la relation t5 > t3 > t4 = t2
        System.out.println(t3.compareTo(t2));   // t3 > t2 donc on doit avoir une valeur positive
        System.out.println(t2.compareTo(t3));  // t2 < t3 donc on doit avoir une valeur négative
        System.out.println(t4.compareTo(t2));   // affiche 0
        System.out.println(t4.compareTo(t4));   // affiche 0
        */
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
