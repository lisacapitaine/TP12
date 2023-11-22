package bowling;

/**
 * Cette classe a pour but d'enregistrer le nombre de quilles abattues lors des
 * lancés successifs d'<b>un seul et même</b> joueur, et de calculer le score
 * final de ce joueur
 */
public class PartieMonoJoueur {

    private final Tour premierTour; // Le premier tour
    private Tour tourCourant; // Le tour en cours

    /**
     * Constructeur
     */
    public PartieMonoJoueur() {
        // On initialise les 10 tours, en commençant par le dernier
        // Chaque tour (sauf le dernier) est initialisé avec son tour suivant
        // On construit ainsi une "liste chainée" de tours
        tourCourant = new DernierTour();
        for (int numero = 9; numero > 0; numero--) {
            Tour nouveauTour = new Tour(numero, tourCourant);
            tourCourant = nouveauTour;
        }
        premierTour = tourCourant;
    }

    /**
     * Cette méthode doit être appelée à chaque lancer de boule
     *
     * @param nombreDeQuillesAbattues le nombre de quilles abattues lors de ce lancer
	 * @throws IllegalStateException si la partie est terminée
     * @return vrai si le joueur doit lancer à nouveau pour continuer son tour, faux sinon
     */
    public boolean enregistreLancer(int nombreDeQuillesAbattues) {
        if (tourCourant == null)
            throw new IllegalStateException("Le jeu est fini");

        tourCourant.enregistreLancer(nombreDeQuillesAbattues);
        if (tourCourant.estTermine()) {
            tourCourant = tourCourant.next();
        }
        return ! (this.estTerminee() || tourCourant.getBoulesLancees() == 0);
    }

    /**
     * Cette méthode donne le score du joueur.
     * Si la partie n'est pas terminée, on considère que les lancers restants
     * abattent 0 quille.
     * 
     * @return Le score du joueur
     */
    public int score() {
        return premierTour.score();
    }

    /**
     * @return vrai si le jeu est finin faux sinon
     */
    public boolean estTerminee() {
        return (tourCourant == null);
    }

    /**
     * @return Le numéro du tour courant [1..10], ou 0 si le jeu est fini
     */
    public int numeroTourCourant() {
        return estTerminee() ? 0 : tourCourant.getNumero();
    }

    /**
     * @return Le numéro du prochain lancer pour tour courant [1..2], ou 0 si le jeu
     *         est fini
     */
    public int numeroProchainLancer() {
        return estTerminee() ? 0 : tourCourant.getBoulesLancees() + 1;
    }

}
