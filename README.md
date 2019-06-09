# Zelda-like

## Architecture MVC
L'architecture MVC est respectée grâce au concept du Gameloop. La Gameloop se charge de la synchronisation entre le modèle et la vue. A chaque tour du jeu, la carte est mise à jour suite aux actions du joueur ou du scénario. Ces actions sont interprétés dans l'ordre suivant:
	
1. Ajout de nouveaux personnages.

2. Suppression des personnages morts.

3. Interprétation des différentes actions produites (Attaques, déplacements, etc...).

## Classes principales :
### Movable
Movable est la classe principale dans ce jeu. En effet, movable représente tout objet pouvant se déplacer. Il existe deux sous classes abstraites qui étendent movable: Attaque et GameCharacter. La différence entre ces deux classes est qu'une instance de GameCharacter occupe une case, tandis qu'une instance d'attaque traverse la case sans l'occuper.

### Attaque
Toute attaque est lancée sur une map. Elle contient plusieurs attributs obligatoires, notamment: 

- **CellPerTurn** : Nombre de cases à parcourir en un seul tour.
- **MaxDistance** :  Nombre maximum de cases à parcourir.
- **LastTurn** : Dernier tour de l’attaque, pour éviter que l’attaque disparaissent au moment du déplacement, supposé instantané.

Les fonctions les plus importantes d’Attaque sont : 

- **handleMove()** : Définit le comportement de l’attaque en fonction du contenu de la case.

- **handlePlay()** : Effet de l’attaque sur le personnage visé.
	
### GameCharacter
Chaque personnage a un nombre de cycle qu’il devra attendre avant d’effectuer sa prochaine action,La superclasse Movable est responsable de déterminer le tour du personnage qui pourra jouer. Tant que le personnage n’a pas atteint son cycle, son action sera null. Les sous classes de Movable n’auront pas le droit de contrôler leur tour de jeu, c’est à dire qu’ils ne peuvent pas jouer sans que ce soit leur tour. Par contre, ils peuvent toujours réduire leurs cycle pour un tour avec la méthode setWait().
Pour fluidifier les mouvements, on a décidé d’avoir un coefficient pour chaque personnage. Ce coefficient permettra au personnage de glisser sur la map (Côté vue).

### Hero :
Le héro est composé des attributs stockés dans une instance de HeroStats. Afin de garantir l’encapsulation de ces attributs, seul la classe CopyOfHeroStats est partagée avec les classes externes. Cette classe contient les attributs du héro en lecture seule et sera principalement utilisée pour lier le modèle à la vue. La modification des attributs du héro passe par des fonctions prédéfinis. Les propriétés du héro sont : 
- **HP** : Points de vie du héro. Cet attribut détermine l’arrêt du Gameloop. En appelant la méthode “isAlive()”, la Gameloop décidera d'arrêter ou de continuer le jeu.
- **MP** : Points de magie du héro.  
- **MaxHP**: Points de vie maximum du héro. Cet attribut varie en fonction des équipements du héro..
- **MaxMP** : Points de magie maximum du héro. Comme maxHP, il varie en fonction des équipements du héro..
- **Attaques** : La liste des attaques pouvant être lancées.

### Ennemis
Comme le héro, les ennemis sont capables de:
* Effectuer un tour de jeu en revoyant ou non un Move
* Se deplacer sur la map
* Lancer des attaques
* Etre retirés de la map.

### Items
Les items sont posés sur une case mais n’en empêche pas l'accès. Ils modifient le héro en fonction de leur spécification. (Augmenter MaxHP, augmenter la vitesse du hero, etc…). Les items sont divisés en plusieurs catégories : 
* **AttackItems** : Définissent les dégâts infligés aux ennemis.
* **DefenseItems** : Augmentent les points de défense du héro
* **HPPotions** : Augmentent les points de vie du héro sans qu’ils augmentent le seuil maximum.
* **MapChanges** : Permettent de changer de Map
* **MPPotiions** : Augmentent les points de magie du héro sans augmenter son seuil maximum..
* **MPItems** : Augmentent le seuil maximum des points des MP sans qu’ils augmentent son MP.
* **PVItems** : Augmentent le seuil maximum des points de vie du héro
* **SpeedItems** : Définissent la vitesse des mouvements du héro.



## Menus du jeu 
- **Menu d’Accueil** : Ce Menu comporte plusieurs boutons :
	- **Nouvelle partie** : Démarrer une nouvelle partie.
	- **Charger une partie** : Accéder au menu des parties sauvegardées.
	- **Quitter** : Quitter le jeu.

- **Menu des parties sauvegardées** : Permet à l'utilisateur de reprendre une partie déjà sauvegardée.

- **Menu pause** : Comporte plusieurs boutons, à savoir : 
	- **Continuer la partie** : Reprendre le jeu.
	- **Sauvegarder la partie** : Enregister la partie courante dans un fichier local.
	- **Charger une partie** : Accéder au menu des parties sauvegardées.
	- **Quitter** : Quitter la partie.

- **Menu Gameover** : Affiché lorsque le héro est mort. Il permet la réalisation des actions suivantes :
	- **Rejouer** : Propose au joueur de retenter sa chance. Il n’amorce pas une nouvelle partie mais reprend le jeu au début de la dernière carte.
	- **Menu principal** : Redirige vers le menu d'accueil.
	- **Quitter** : Fermer le jeu.
		
## Mécanismes
- **Flèches directionnelles**: Pour attaquer vers une direction.
- **Z, Q, S, D** : Touches directionnelles .
- **E**: Changer d’attaque (basculer vers  une attaque spéciale …).
- **CTRL+ESPACE** : Passer un message. Le choix de le rendre plus compliqué est pour s’assurer que le joueur lit le message en entier, vu qu’il contient des informations importantes sur les missions.
- **X**: Parler à un NPC

## Items
- **Botte** : Augmente la vitesse du joueur.
- **Epée** : Inflige des dégâts au personnage qui se trouve à une case du héro.
- **Flèches** : Disparaissent après avoir parcourir un nombre n de cases.
- **Hache** : Attaque jusqu'à 3 cases devant le héro.
- **coeur** : Regénère des points de vie.
- **bombe** : Détruit certains obstacles.
- **bouclier** : Réduit les dégâts infligés au héro.
	
## Ennemis 
- **Boss NyaNyaNya** : se déplace horizontalement. Lance un arc en ciel si le héro est sur la ligne horizontale, sinon des attaques qui changent de façon aléatoire.
- **BadMonkey** : Attaque le héro dès qu’il est à sa portée.
- **Tower** : Une tourelle qui attaque aléatoirement dans toutes les directions.
- **IntelligentTower** : Une tourelle qui lance des attaques vers la direction du héro.
- **StaticBadMonkey** : Un monstre qui ne bouge pas, n’attaque pas et qui est comme un NPC auquel on pourra infliger des dégâts.
	
