# Captain Morlot

Captain Morlot est un jeu de société qui se joue en groupe. Pour y jouer, vous devez être au minimum 3 joueurs et avoir un smartphone (Android de préférence).

### Prérequis

- Récupérer la branche `Master`
- Installer [Android Studio](https://developer.android.com/studio)

---------------

## Règles du jeu

Au lancement d'une partie, un mot va être donné. La première personne qui commencera devra dire un mot en relation avec le mot de départ. La personne suivante devra ensuite dire un mot qui est en relation avec celui du joueur précédent etc...  
Si un joueur n'arrive pas à donner un mot dans les 3 secondes, il meurt.  
Le but est d'être le dernier à rester en vie.

## Les personnages dans Captain Morlot

Chaque joueur se verra assigner un personnage à chaque début de manche. Chaque personnage influence la manière de dire son mot. 

### Le Matelot

Il doit dire un mot qui décrit le mot donné en début de partie ou par le joueur précédent.

> Si le mot est guitare, alors le matelot peut dire : corde, instrument, manche, bois, acoustique, électrique...

### Le Pirate

Il doit dire un mot qui est soit le contraire, soit qui complète le mot donné en début de partie ou par le joueur précédent.

> Si le mot est guitare, alors le pirate peut dire : acoustique ou électrique  
> Si le mot est mère, alors le pirate peut dire père  
> Si le mot est Youtube, alors le pirate peut aussi dire heure (pour former youtuber)

### Le Moussaillon

Il possède les facultés du matelot et du pirate. De plus, lorsqu'il dira son mot, il devra désigner quelqu'un du doigt et celui-ci devra mimer son mot. S'il oublie, il mourra.

--------------

## Spécifications techniques

### API REST

Pour charger les mots, une `API REST` a été créée et utilisée : [WordGeneration](https://bridge.buddyweb.fr/docs/wordgeneration#table-1984)

### Architecture

L'application adopte l'architecture `MVP`. L'application est séparée par fonctionnalité. Chaque fonctionnalité possède son presenter ainsi que sa vue.

### Ecran

Il y a 6 activités dans cette application : 
- chargement
- règles du jeu
- accueil
- choix des rôles
- jeu
- la fin d'une manche

### RecyclerView

L'affichage des joueurs est réalisée à l'aide d'une `recyclerView`.

### Stockage de données en cache

Grâce aux `SharedPreferencies`, l'application affiche les règles du jeu si la personne démarre l'application pour la première fois, sinon elle ne s'affiche pas.

### Gitflow

### Fonctions supplémentaires

- Animation entre chaque activité
- Popup de confirmation
- Design
- `Splash screen` au lancement de l'application
- `Seekbar` pour sélectionner le nombre de rôles
- `ViewPager` pour visualiser les règles du jeu
- `GridLayout` pour la représentation des joueurs à la fin d'une manche

--------------

## Interface du jeu

### Ecran de chargement
Lorsque vous lancez le jeu, vous verrez le logo dans un premier temps.  

<img src="../images/loading.png" width="150" height="275">

Si c'est la première fois que vous lancez le jeu, vous aurez les règles du jeu affichées.  

<img src="../images/rules.png" width="150" height="275">

### Ecran d'accueil
Vous pouvez entrer de nouveaux joueurs en indiquant leur nom. Vous pouvez cliquer sur le nom du joueur pour le retirer de la liste.  

<img src="../images/main.png" width="150" height="275">

### Ecran du choix des rôles
Une fois les joueurs choisis, vous pouvez choisir le nombre de moussaillons ou de pirates que vous voulez avoir.  

<img src="../images/choose-roles.gif" width="150" height="275">

### Ecran de jeu
Au début de chaque manche, vous verrez une popup apparaître indiquant le rôle de chacun et le joueur commençant en premier.  

<img src="../images/roles-popup.png" width="150" height="275">

Le jeu donne un mot, il est possible de changer de mot si ce dernier ne convient pas.  

<img src="../images/game.png" width="150" height="275"><img src="../images/game2.png" width="150" height="275">

### Ecran de fin de manche
Vous avez la liste des joueurs. Lorsqu'un joueur est mort, cliquez sur ce dernier pour l'enlever de la liste. Une popup de confirmation s'affiche.  

<img src="../images/end-game.png" width="150" height="275"><img src="../images/end-game-popup.png" width="150" height="275">

Lorsqu'il reste 2 joueurs, ces derniers deviennent des matelots.  

<img src="../images/last-roles-popup.png" width="150" height="275">

Lorsqu'une personne gagne, une popup s'affiche.  

<img src="../images/end-game--last-popup.png" width="150" height="275">



