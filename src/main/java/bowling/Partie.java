package bowling;

import java.util.*;

public class Partie implements IPartieMultiJoueurs {
	private HashMap<String, PartieMonoJoueur> laGame;
  private int numJoueurAct=-1;
	private String[] noms;
	private int nombreDeJoueurs;
	

	private String phraseRetour;

	public String getPhraseRetour() {
		this.phraseRetour = "Prochain tir : joueur " + noms[numJoueurAct] + ", tour n° " + laGame.get(noms[numJoueurAct]).numeroTourCourant() + ", boule n° " + laGame.get(noms[numJoueurAct]).numeroProchainLancer();
		return phraseRetour;
	}

	/**
	 * Démarre une nouvelle partie pour un groupe de joueurs
	 *
	 * @param nomsDesJoueurs un tableau des noms de joueurs (il faut au moins un joueur)
	 * @return une chaîne de caractères indiquant le prochain joueur,
	 * de la forme "Prochain tir : joueur Bastide, tour n° 1, boule n° 1"
	 * @throws java.lang.IllegalArgumentException si le tableau est vide ou null
	 */

	@Override
	public String demarreNouvellePartie(String[] nomsDesJoueurs) throws IllegalArgumentException {
		if (nomsDesJoueurs == null || nomsDesJoueurs.length == 0) {
			throw new IllegalArgumentException("le tableau de noms est vide");
		}

		//initialisation
		laGame = new HashMap<>();
		this.noms = nomsDesJoueurs;
		nombreDeJoueurs = nomsDesJoueurs.length;
		numeroJoueurAct = 0;

		for (String nom : nomsDesJoueurs) {
			laGame.put(nom, new PartieMonoJoueur());
		}

		return getPhraseRetour();
	}

	/**
	 * Enregistre le nombre de quilles abattues pour le joueur courant, dans le tour courant, pour la boule courante
	 *
	 * @param nombreDeQuillesAbattues : nombre de quilles abattue à ce lancer
	 * @return une chaîne de caractères indiquant le prochain joueur,
	 * de la forme "Prochain tir : joueur Bastide, tour n° 5, boule n° 2",
	 * ou bien "Partie terminée" si la partie est terminée.
	 * @throws java.lang.IllegalStateException si la partie n'est pas démarrée.
	 */
	@Override
	public String enregistreLancer(int nombreDeQuillesAbattues) throws IllegalStateException {
		if(numeroJoueurAct==-1){
			throw new IllegalStateException("La partie n'a pas commencé");
		}
		
		if (laGame.get((noms[0])).numeroTourCourant()==0) {
			return "Partie terminée";
		}
		
		PartieMonoJoueur joueurPartie = laGame.get(noms[numJoueurAct]);
		joueurPartie.enregistreLancer(nombreDeQuillesAbattues);

		if (joueurPartie.numeroProchainLancer() == 1 || joueurPartie.estTerminee()) {
			numJoueurAct = (numJoueurAct + 1) % nombreDeJoueurs;
		}
		return getPhraseRetour();
	}

	/**
	 * Donne le score pour le joueur playerName
	 *
	 * @param nomDuJoueur le nom du joueur recherché
	 * @return le score pour ce joueur
	 * @throws IllegalArgumentException si nomDuJoueur ne joue pas dans cette partie
	 */
	@Override
	public int scorePour(String nomJoueur) throws IllegalArgumentException {
		if (laGame.get(nomJoueur) == null) {
			throw new IllegalArgumentException(nomDuJoueur + "ne joue pas cette partie");
		}
		
		return laGame.get(nomJoueur).score();
	}
}
