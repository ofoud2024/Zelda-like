
# Jeu Zelda-Like
## Présentation
Ce projet a été réalisé dans le cadre du projet du deuxième semestre. C'est un jeu en 2D développé en JavaFX dans le but d'appliquer nos connaissances en conception et en développement logiciel. Les contributeurs à ce projet sont : 
- **Omar FOUDANE**
- **Dorian CASSAGNE**
- **William LIN**


## Classes principales :
### Diagramme de classe
Vous pouvez retrouver le diagramme de classe dans le lien suivant : [[LIEN]]

### Scénario
La particularité de ce jeu, c'est que le scénario est rédigé dans des fichiers texte interprétés par la GameLoop. Ainsi, la modification du scénario n'entraînera pas des modifications dans le code.
Par définition, un scénario est une suite d'événements : des actions réalisées lorsqu'une condition est satisfaite.

#### Syntaxe générale
Afin que le scénario soit interprété correctement, la syntaxe suivante doit être respectée : 

**CONDITION{-OPERATEUR_LOGIQUE-CONDITION...}->ACTION-{ACTION...}**.


Les sections suivantes expliquent comment rédiger un scénario correct. Les termes entre {{}} doivent être remplacés par des valeurs cohérentes lors de la rédaction du scénario. Ces termes sont : 
- {{message}} : Le texte du message à afficher au joueur.
- {{Nom-Item}} : Le nom d'item. La liste des items possibles se trouvent dans la classe [ItemFactory]().
- {{Identifiant-Case}} : CaseX + CaseY * Nombre-Case-Par-Ligne.
- {{Type-Monstre}} : Le type du monstre. Vous pouvez trouver la liste des types possbiles dans la classe [EnemyFactory]().
- {{Identifiant-Monstre}} : Un identifiant unique au monstre créé, permettant de le référencer par la suite.
- {{Type-NPC}} : Définit le type du NPC ou l'image associée à cet NPC. La liste des NPC possibles se trouve dans la classe [TalkingNPC]().
- {{Message-NPC}} : Le message affiché lorsqu'on parle à un NPC.
- {{Type-Case}} : Le nouveau décor à placer sur la case (2601 pour rendre la case libre).
- {{Nom-Map}} : Il existe actuellement 4 cartes différentes. Elles sont déclarées dans la classe [MapChangerEnum]().
- {{Délai-Map}} : Nombre de cycles à attendre avant l'exécution de la prochaine action sur la map.
- {{Délai-Scénario}} : Nombre de cycles à attendre avant la prochaine exécution du scénario.
- {{Négation}} : Si cette variable est remplacé par "!", alors la négation sera appliquée sur la condition à vérifier. 
- {{HP-Monstre}} : Les points de vie d'un monstre. Ils sont égals à 0 si le monstre est mort.
- {{Numéro-Ligne-Action}} : Numéro du ligne dans le fichier texte contenant le scénario.

#### Conditions
On peut combiner plusieurs conditions à l'aide des opérateurs logiques (AND ou OR). Ces opérateurs sont placés juste après la condition. La négation peut être appliquée sur une condition en utilisant le caractère "!".
Les conditions compréhensibles par ce jeu sont : 
- **Conditions sur le contenu d'une case** : Vérifie si une case :  
	- Contient un item : C:{{Identifiant-Case}}:{{Négation}}:I.
	- Est traversable : C:{{Identifiant-Case}}:{{Négation}}:W.
- **Conditions sur les points du vie d'un monstre** : M:{{Identifiant-Monstre}}:{{Négation}}:{{HP-Monstre}}.
- **Conditions sur la position du héro** : H:{{identifiant-Case}}:{{Négation}}:C.
- **Conditions relatives à l'exécution d'un événement** : O:{{Numéro-Ligne-Action}}:{{Négation}}:A. Cette condition ne sera vraie que lorsque l'événement situé à la ligne {{Numéro-Ligne-Action}} est exécuté dans sa totalité.


#### Actions
Il existe plusieurs actions possibles, à savoir : 
- **Affichage d'un message** : Dans ce cas là, la syntaxe utilisée est : S-m-{{message}}.
- **Création d'un objet** : En fonction de l'objet à créer, cette action peut être relative à : 
	- Un item : C-I-{{Nom-Item}}-{{Identifiant-Case}}. 
	- Un monstre : C-M-{{Type-Monstre}}-{{Identifiant-Monstre}}-{{Identifiant-Case}}.
	- Un NPC : C-N-{{Type-NPC}}-{{Message-NPC}}.
- **Suppression d'une instance** : La suppression peut être relative à plusieurs instances, à savoir :
	- Un item : D-I--{{Identifiant-Case}}.
	- Un monstre : D-M--{{Identifiant-Monstre}}.
	- Une case : D-W--{{Type-Case}}-{{Identifiant-Case}}.
	- Héro : D-H---, retire le joueur de la map sans le tuer. 
- **Modification du comportement du héro** : 
	- Equiper un item au héro : A-I-{{Nom-Item}}--.
	- Faire apparaître le héro sur la map, suite à une suppression par exemple : A-H--{Identifiant-Case}.
- **Changement du Map** : M-C-{{Nom-Map}}.
- **Retarder l'éxecution** : d- - -{{Nombre-Cycle}}-{{Delai-Secondes}}. Retarde l'exécution du jeu pendant un certain délai.

#### Exemples
Evénement 	: Si l'événement à la ligne 2 est exécuté dans sa totalité, alors affiche le messsage suivant : "Jeu : Attention, on reçoit une attaque de l'extérieur".
Représentation 	: O:2: :A->S-m--Jeu : Attention, on reçoit une attaque de l'extérieur

Evénement 	: Si les points de vie du boss sont inférieurs à 500, alors affiche le message "Tu m'as vaincu aujourd’hui" puis ouvre une porte à la case 58 puis retire le boss de la map.
Représentation 	: M:boss: :500->S-m--Tu m'a vaincu aujourd’hui->D-W--2601-58->D-M--boss

Evénement 	: Si l'ennemi identifié par ennemi1 est mort et que le héro est sur la case 1824, alors crée le boss sur la case 2016, puis retarde l'exécution du joueur de 40 cycles et du scénario de 35 cycles.
Représentation  : M:ennemi1: :0-AND-H:1824: :->C-M-NYANYANAY-boss-2016->d---40-35

Pour plus de détails, vous pouvez consulter : 
- **Scénarios prédéfinis** : [[LIEN]]
- **Intrépreteur du scénario** : [[LIEN]]

### Movable
Movable est la classe principale dans ce jeu. En effet, movable représente tout objet pouvant se déplacer. Il existe deux sous classes abstraites qui étendent movable: Attaque et GameCharacter. La différence entre ces deux classes est qu'une instance de GameCharacter occupe une case, tandis qu'une instance d'attaque traverse la case sans l'occuper.

#### Attaque
Toute attaque est lancée sur une map. Elle contient plusieurs attributs obligatoires, notamment: 

- **CellPerTurn** : Nombre de cases à parcourir en un seul tour.
- **MaxDistance** :  Nombre maximum de cases à parcourir.
- **LastTurn** : Dernier tour de l’attaque, pour éviter que l’attaque disparaissent au moment du déplacement, supposé instantané.

Les fonctions les plus importantes d’Attaque sont : 

- **handleMove()** : Définit le comportement de l’attaque en fonction du contenu de la case.
- **handlePlay()** : Effet de l’attaque sur le personnage visé.
	
#### GameCharacter
Chaque personnage a un nombre de cycle qu’il devra attendre avant d’effectuer sa prochaine action,La superclasse Movable est responsable de déterminer le tour du personnage qui pourra jouer. Tant que le personnage n’a pas atteint son cycle, son action sera null. Les sous classes de Movable n’auront pas le droit de contrôler leur tour de jeu, c’est à dire qu’ils ne peuvent pas jouer sans que ce soit leur tour. Par contre, ils peuvent toujours réduire leurs cycle pour un tour avec la méthode setWait().
Pour fluidifier les mouvements, on a décidé d’avoir un coefficient pour chaque personnage. Ce coefficient permettra au personnage de glisser sur la map (Côté vue).

##### Hero :
Le héro est composé des attributs stockés dans une instance de HeroStats. Afin de garantir l’encapsulation de ces attributs, seul la classe CopyOfHeroStats est partagée avec les classes externes. Cette classe contient les attributs du héro en lecture seule et sera principalement utilisée pour lier le modèle à la vue. La modification des attributs du héro passe par des fonctions prédéfinis. Les propriétés du héro sont : 
- **HP** : Points de vie du héro. Cet attribut détermine l’arrêt du Gameloop. En appelant la méthode “isAlive()”, la Gameloop décidera d'arrêter ou de continuer le jeu.
- **MP** : Points de magie du héro.  
- **MaxHP**: Points de vie maximum du héro. Cet attribut varie en fonction des équipements du héro..
- **MaxMP** : Points de magie maximum du héro. Comme maxHP, il varie en fonction des équipements du héro..
- **Attaques** : La liste des attaques pouvant être lancées.

##### Ennemis
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

### Movement
La classe Movement définit les mouvements possibles dans le jeu : avancer, reculer, à gauche, à droite, en haut à droite, en haut à gauche, en bas à droite et en bas à gauche et "stay". Cette classe définit aussi une méthode indiquant la bonne direction vers une case donnée. Ce qui est très util dans le cas de la tourelle intelligente par exemple. 

### Gameloop
Chaque tour de jeu est contrôlé par la Gameloop. Elle tourne en continue pour mettre à jour la carte, tant que le héro n’est pas mort. La gameLoop est une classe cruciale dans le jeu. A chaque tour, la Gameloop communique la liste des actions effectuées à la vue. Ces actions seront une instance de Move et contiendront trois paramètres essentiels : 
- la case d’arrivée.
- l’identifiant du personnage : partagé entre la liste principale de GameMap et la liste des éléments visuels de la vue.
- la vitesse des mouvements.  





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
- **Flèches directionnelles** : Pour attaquer vers une direction.
- **Z, Q, S, D** : Touches directionnelles .
- **E**: Changer d’attaque (basculer vers  une attaque spéciale …).
- **CTRL+ESPACE** : Passer un message. Le choix de le rendre plus compliqué est pour s’assurer que le joueur lit le message en entier, vu qu’il contient des informations importantes sur les missions.
- **X**: Parler à un NPC

### Items
- **Botte** : Augmente la vitesse du joueur.
- **Epée** : Inflige des dégâts au personnage qui se trouve à une case du héro.
- **Flèches** : Disparaissent après avoir parcourir un nombre n de cases.
- **Hache** : Attaque jusqu'à 3 cases devant le héro.
- **coeur** : Regénère des points de vie.
- **bombe** : Détruit certains obstacles.
- **bouclier** : Réduit les dégâts infligés au héro.
	
### Ennemis 
- **Boss NyaNyaNya** : se déplace horizontalement. Lance un arc en ciel si le héro est sur la ligne horizontale, sinon des attaques qui changent de façon aléatoire.
- **BadMonkey** : Attaque le héro dès qu’il est à sa portée.
- **Tower** : Une tourelle qui attaque aléatoirement dans toutes les directions.
- **IntelligentTower** : Une tourelle qui lance des attaques vers la direction du héro.
- **StaticBadMonkey** : Un monstre qui ne bouge pas, n’attaque pas et qui est comme un NPC auquel on pourra infliger des dégâts.
	
	
