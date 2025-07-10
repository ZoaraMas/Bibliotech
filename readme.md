# 06-05-2025
- Jusqu'a maintenant, on a initialise le projet spring, et mis en place notre premier bean en prenant en compte la difference entre les bean de scope prototype et singleton

- Ensuite on a commencer a injecter les dependances dans les beans, pour ne pas a devoir recompiler le programme si il y a certaines modifications puisque les informations(du utildb par exemple) ne sont plus ecrit en dur dans le code.
    Aussi getConnection de utilDb ne sera plus en statique et chaque entite aura aussi un attribut utildb pour pouvoir utiliser la connection de maniere plus flexible

# 10-05-2025
- Je vais implementer l'oriente une bonne fois pour toute pour l'injection des dependences

- C'est bon l'oriente objet a ete realise, et les deux methodes d'architecture sont pret a etre porte



# 13-05-2025
On oublie tout ce qui est dao et oriente service et on part sur une implementation de repository avec jpa

On remplace alors les dao avec des repository et toutes les requetes y seront deja disponible sans implementation[repository en interface], a condition de mettre en place les configurations necessaires sur les entityManager dans le fichier xml de configuration

Ensuite, il y a 3 types de passage de message a un framework: convention, fichier de configuration(.xml par exemple) et les annotations.
On va alors annoter les classes d'entites par @Entity, les services par @Service, et les repository par @repository. Les services utiliseront alors les methodes des repository pour effectuer des operations + des operations logiques de metier.

On peut par exemple annoter les attributs d'une entite pour faire la correspondance aux attributs dans les tables dans la base de donnee. Mais on peut aussi ne pas le faire, alors le framework agira sur l'attribut en mode convention[nom d'attribut de classe = nom d'attribut de table], l'element important est de juste annoter l'entite comme equivalent de table de la base de donnne. Il en est a peu pres ainsi aussi pour les noms des requetes dans les repository, comme findByCompteNumero par exemple qui devrait etre automatique via l'attribut CompteNumero sans implementation de compte. Il y a aussi une maniere specifique d'inclurer directement une requete sql en dure pour des soucis de performances ou de complexite

# 16-05-2025
On va implementer la couche vue et controller des choses.
Il existe maintenant des classes qui joueur le role de controller ou on pourrait indiquer la methode utilise pour chaque path. On met en argument un model, ou on va pouvoir ajouter les attributs sur le model. on aura alors besoin d'un web.xml, application.xml 

# 17-05-2025
Implementation web de spring:
- web.xml: on va mettre en place les listeners pour pouvoir rediriger a tout temps quand on commence l'application par exemple

# 18-05-2025 
DIEU merci enfin ca marche, il vient vraiment d'ecouter mon cri de desespoir, alors que tomcat semblait afficher encore que le projet ne marche pas, je clique et ca marche!!! Merci beaucoups tellement Seigneur je vous aime

Bon c'etait vraiment ardu je me rend compte, et devenir developeur spring ca va vraiment etre complique. Alors mon probleme la etait que la version entre tomcat et spring ne marchait pas, mon spring etait encore dans un delire de javax, alors que tomcat necessite jakarta. Bien sure autres que les autres problemes techniques que j'ai rencontre, mais je pense que ca merite un peu plus de recherche les differents aspects qui vont composer le spring, mais au moins maintenant je voie une certaine logique se construire et que chaque fichier a son role a jouer dans l'histoire

# 23-05-2025
Pour compiler le projet et le deployer vers tomcat:
- ./run.bat
- ./CompAndMove.bat

# 26-05-2025
L'objectif d'aujourd'hui est de faire un crud entier pour departement et emp en utilisant l'architecture spring le plus utilise

Voici les couches de l'architecture:
Persistence, repository, dervice, Presentation[controller, vue, modele]
Etapes:
- CRUD DEPARTEMENT
    - Persistence
    - repository
    - service
    - Presentation
        - modele
        - vue
        - controller


- read et create de dept fonctionnent

- Faire la meme pour emp + update et delete

# 27-05-2025
Hier et aujourd'hui j'ai ete confronte a un probleme majeur, l'erreur:
27-May-2025 08:30:08.767 INFO [mysql-cj-abandoned-connection-cleanup] org.apache.catalina.loader.WebappClassLoaderBase.checkStateForResourceLoading Illegal access: this web application instance has been stopped already. Could not load []. The following stack trace is thrown for debugging purposes as well as to attempt to terminate the thread which caused the illegal access.
	java.lang.IllegalStateException: Illegal access: this web application instance has been stopped already. Could not load []. The following stack trace is thrown for debugging purposes as well as to attempt to terminate the thread which caused the illegal access.
		at org.apache.catalina.loader.WebappClassLoaderBase.checkStateForResourceLoading(WebappClassLoaderBase.java:1352)
		at org.apache.catalina.loader.WebappClassLoaderBase.getResource(WebappClassLoaderBase.java:950)
		at com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkThreadContextClassLoader(AbandonedConnectionCleanupThread.java:123)
		at com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.run(AbandonedConnectionCleanupThread.java:90)
		at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
		at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
		at java.base/java.lang.Thread.run(Thread.java:842)

ca parlait d'erreur de mysql cleanup qui voulait pas bien marcher, chat me proposait des choses pas tres logiques, mais en faite le probleme etait tout simple:
j'ai creer une fonction dans repo findByName alors que l'attribut name n'existait tout simplement pas, je doit etre plus logique surtout quand je fais des copier coller, ca m'eviterais des heures de debug


# LA CONCEPTIONS(donnee, traitement)
ici on va parler de donnee

Quels sont les entites et les caracteritiques?
Cardinalite: oneToMany, ManyToOne, manyToMany

si c'est le cas de manyToMany, il faut creer une table associatif, -> ca veut dire que chacune des entites peuvent avoir plusieurs references de l'autre cote, en depit du temps, c'est pour ca qu'on a une sorte d'historique alors dans la table associatif

sinon, l'entite qui possede le many n'a pas d'incidence dans sa conception(seul le one aura un fk)


Pour le tp d'aujourd'hui, on va creer une table film et une table categorie, c'est du many to many

- Erreur super chelou encore, cette fois ci, c'etait car j'ai ecris Embedded au lieu de EmbededId dans la classe associative, bref faut vraiment mieux comprendre le code qui atterit dans notre editeur.

# 30-05-2025
- Pour la creation de film, on a la possibilite de choisir plusieurs categories

[
    J'ai longtemps ete bloque pour l'implementation de la table associative, en effet, quand on creer une nouvelle instance de cette derniere, il faut aussi imperativement mettre a jour l'id embeded a l'interieur, c'est ce qui permet au jpa de sauver le tout. aussi, on ajoute le filmCategorie au payement
]

Pour obtenir les categories dans les films, il faut imperativement qu'elles soient initialise dans une fonction transaction, surtout si on a du fetch.lazy

plus que la fonction update, initialiser les categories avant de mettre les checkBoxes et ensuite faire marcher l'update


# Ainsi s'acheve les logs de preparation spring,  maintenant place au projet de bibliotech final

# 01-07-25
- Initialisation projet + base de donnee

- Fonctionalite Pret Livre
    - verifier si l'exemplaire du livre est encore disponible => il va falloir prendre en compte les prets deja existant pour verifier si un exemplaire(en tant qu'individu propre) est encore disponible ou non
    - Je vais creer les vues necessaires pour creer la fonction sql qui va verifier si un exemplaire est disponible maintenant ou non

# 04-07-25
Mon probleme etait de comment interpreter la vue pret_parametre vers une entite

- Plus tard on aura des prets qui existeront deja pour des dates > now(), on ne les comptes pas quand on va verifier la penalisation actuelle


# 05-07-2025
- Le pret etant regle on enchaine les foncitonalites, du plus vital au moins vital

[ok]- remettre un exemplaire de livre
[ok]- reserver un exemplaire de livre
[ok]- valider une reservation 
[ok]- automatiser script + securite pour les parametres
[ok]- looping -> mysqlWorkBench
- mise en forme liste de fonctionalites
- frontend


- gestion jour ferrie
- demande de prolongation de pret
- validation de prolongement de pret
- dashboard


- 1) Remettre un exemplaire de livre
    - on doit pouvoir selectioner la date de remise du livre, cela implique de rajouter les fonctions necessaires qui prenaient jusqu'a maintenant pas d'argument de date mais qui utilisaient now()

- reprise:
les regles de gestions sont plies, implementer la fonction save

maintenant je vais creer une fonction qui verifie si un pret sera couvert par une inscription, donc je vais aller creer l'entite parametre_pret

# 06-07-2025
- Alea possible:
    - est ce que mon programme permet de detecter un exemplaire disponible s'il a ete rendu avant la date limite? est ce que je part des remise pour verifier la disponibilite d'un exemplaire?

[regle]- probleme l'utilisateur 13 de l'inscription 12 sera penalise apre le 11 Juillet car il n'aura pas encore remis le livre

- Debut fonctionalite validation validation-reservation

# Elements a reverifier:
- quota a un certain date lors de la reservation par exemple

# 07-07-25
Ne peut pas convertir le resultat du repo en LivreNombre DTO dans livreService

# 10-07-25
Testons la reservation ainsi que la validation de reservation:
insert into inscription (date_inscription, id_user, id_typ  e_adherent, duree_mois, id_employe) values('2025-07-02', 1, 1, 10, 1);

- la validation de reservation semble deja marcher actuellement

=>PB: Ne peut pas convertir le resultat du repo en LivreNombre DTO dans livreService 
- mamorona vue ho an'ny pret_nombre_desc dia apiasaina @ repo an'ny livre mba hireglena ny erreur.


- - Comment tester le bon fonctionnement des modules?
    - Creer un systeme(excel ou autre)
    - Avoir des donnees logiques et coherentes et graphes 
        - inclure des donnees de toutes les sortes.


- Je dois d'abord rajouter la possibilite de creer un pret a une date donnee
    - Pour l''instant ca marche sans encore les verifications metiers rigoureuses

- cela etant fait:  
    Voici les fonctionalites a tester:
        - pret
        - reservation qui sont finalement la meme chose

# BIBLIOTECH
- CompAndMove ou CAM(recente) pour compiler et deplacer le projet compile vers tomcat, deux version pour le pc itu et le pc TUF
- [a verifier] CopyJsp pour copier uniquement les jsp
- C'est dans pom.xml qu'on setup le nom du projet 

- fichiers cles: pom.xml, web.xml, spring.xml(applicationContext.xml), dispatcher-servlet, CAM.bat

# INITIALISATION DU PROJET
# 1- Initialisation base de donnee
- Executer le script sql

# 2- Compilation et deploiement
- executer run.bat
- Dans le batch file CAMTuf, Changer le nom du projet [war_name] si besoin, et preciser le vrai chemin vers webapps de tomcat-10.1.28
- Executer le batch

# Prevoir 
- demande de pret
- config age

# DEVELOPPEMENT
# astuces:
- Enlever la fonctionalite de login:
Dans le web.xml: commenter /*, et mettre un autre url lambda
<url-pattern>/user/hello</url-pattern>
        <!-- <url-pattern>/*</url-pattern> -->
        Par contre les insertions ne marcheront plus
A utiliser pour les requetes GET

# Les fichiers du projet:
# LES SCRIPTS DE BASE DE DONNEE
- script.sql: conception de base prevu(il y a des tables qui sont non present dans final.sql, mais que je vais ajouter petit a petit a final.sql)
- data.sql: donnees fonctionnels
- query.sql: test de script tout simplement
    - inconnu: reverse.sql
    
- final: script + donnees finale, script pret a etre execute
- cross.sql: peupler les possibilites de parametres de pret non insere encore   