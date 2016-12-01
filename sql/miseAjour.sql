-- Ajout, suppression, modification d’une personne, d’une colocation ; ajout d’un achat, d’un versement, etc. Toute autre requête que vous jugeriez utile pour la gestion de cette base. Au niveau du fonctionnement de l’application, on pourra supposer que lors de la création d’une colocation dans la base, on pourra choisir le mode de modification "ouvert" ou "fermé" ; en mode ouvert, chaque membre peut enregistrer des achats ou versement, en mode fermé, seul le gestionnaire peut enregistrer des achats qui concernent la colocation ou des versements sur la cagnotte ; dans tous les cas, seul le gestionnaire peut inscrire et désinscrire une personne de la colocation.

-- ajouté une personne
create sequence seq_personne
minvalue 1
start with 1
increment by 1
cache 10;

-- NOM, PRENOM et MAIL seront passer par paramètre
insert into PERSONNE
values (seq_personne.nextval, 'NOM', 'PRENOM', 'MAIL');

-- ajouté une colocation
create sequence seq_colocation
minvalue 1
start with 1
increment by 1
cache 10;

-- ADRESSE, NOM, Y, 42 seront passer par paramètre, 42 est l'id d'une personne appartenant à la base
insert into COLOCATION
values (seq_colocation.nextval, 'ADRESSE', 'NOM', 'Y', 42);
