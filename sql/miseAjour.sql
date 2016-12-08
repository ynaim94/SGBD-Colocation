-- Ajout, suppression, modification d’une personne, d’une colocation ; ajout d’un achat, d’un versement, etc. Toute autre requête que vous jugeriez utile pour la gestion de cette base. Au niveau du fonctionnement de l’application, on pourra supposer que lors de la création d’une colocation dans la base, on pourra choisir le mode de modification "ouvert" ou "fermé" ; en mode ouvert, chaque membre peut enregistrer des achats ou versement, en mode fermé, seul le gestionnaire peut enregistrer des achats qui concernent la colocation ou des versements sur la cagnotte ; dans tous les cas, seul le gestionnaire peut inscrire et désinscrire une personne de la colocation.

-- donné test
insert into PERSONNE
values (42, 'NOM', 'PRENOM', 'MAIL');
insert into COLOCATION
values (666, 'ADRESSE', 'NOM', 'Y', 42);
insert into CONTRAT_MEMBRE
values (42, '01-JAN-08', '02-JAN-09', 666, 42);
insert into CONTRAT_MEMBRE
values (666, '01-JAN-08', '02-JAN-09', 666, 42);
insert into CONTRAT_MEMBRE
values (13, '01-JAN-08', '02-JAN-09', 666, 42);

--------------------------------------------------------------------------------
-- ajouté une personne
insert into PERSONNE
values (seq_personne.nextval, 'NOM', 'PRENOM', 'MAIL');

--------------------------------------------------------------------------------
-- ajouté une colocation
-- soit la personne 42 qui serat le gérantr de la futur colocation
insert into COLOCATION
values (seq_colocation.nextval, 'ADRESSE', 'NOM', 'Y', 42);

--------------------------------------------------------------------------------
-- ajouté une personne a une colocation (= crée un contrat member)
-- soit la personne 42 et la colocation 666
insert into CONTRAT_MEMBRE
values (seq_contrat_membre.nextval, '01-JAN-08', '02-JAN-09', 666, 42);


--Avant chaque ajout d'achat/abondement/versement par un contrat membre, vérifire 
--que la date de l'achat/abondement/versement est comprise entre les deux dates du contrat membre

--------------------------------------------------------------------------------
-- ajouté un achat_personnel
-- soit le contrat membre  42 qui fait l'achat et 13 et 666 qui en bénificie
select ID_COLOCATION
from CONTRAT_MEMBRE
where ID_CONTRAT_MEMBRE = 42
or ID_CONTRAT_MEMBRE = 13
or ID_CONTRAT_MEMBRE = 666;
-- vérification (les valeur retourné doivent êtres deux à deux égale)

insert into ACHAT_PERSONNEL
values (seq_achat_personnel.nextval, 'INTITULEE', '01-JAN-08', 500, 42);

insert into BENEFICIAIRE
values (13, seq_achat_personnel.currval);

insert into BENEFICIAIRE
values (666, seq_achat_personnel.currval);

--------------------------------------------------------------------------------
-- ajouté un achat_colocation
-- soit le contrat membre  42 qui fait l'achat et la colocation 666 qui en bénificie
select ID_COLOCATION
from CONTRAT_MEMBRE
where ID_CONTRAT_MEMBRE = 42;
-- vérification (la valeur retourné est égale a 666)

insert into ACHAT_COLOCATION
values (seq_colocation.nextval, 'INTITULE', '01-JAN-08', 500, 666, 42);

-- si l'achat se fait avec la cagnionette
select A_UNE_CAGNOTTE
from COLOCATION
where ID_COLOCATION = 666;
-- vérification (doit etre égale a 'Y')

insert into ACHAT_COLOCATION
values (seq_colocation.nextval, 'INTITULE', '01-JAN-08', 500, 666, null);

--------------------------------------------------------------------------------
-- ajouté abondement
-- soit le contrat membre  42 qui fait un abondement
select A_UNE_CAGNOTTE
from COLOCATION, CONTRAT_MEMBRE
where CONTRAT_MEMBRE.ID_COLOCATION = COLOCATION.ID_COLOCATION
and CONTRAT_MEMBRE.ID_CONTRAT_MEMBRE = 42;
-- vérification (doit etre égale a 'Y')

insert into ABONDEMENT
values (seq_abondement.nextval, '01-JAN-09', 500, 42);

--------------------------------------------------------------------------------
-- ajouté un versement
-- soit le contrat membre  42 qui fait un versement au contrat membre 13
select ID_COLOCATION
from CONTRAT_MEMBRE
where ID_CONTRAT_MEMBRE = 42
or ID_CONTRAT_MEMBRE = 13;
-- vérification (les valeur retourné doivent êtres deux à deux égale)

insert into VERSEMENT
values (seq_versement.nextval)

--==============================================================================

-- suprimet une personne
-- supprimet 42

select ID_COLOCATION
from COLOCATION
where ID_PERSONNE = 42;
-- (dans l'exemple, retourne 666)

select ID_PERSONNE
from COLOCATION
where ID_COLOCATION = 666;
