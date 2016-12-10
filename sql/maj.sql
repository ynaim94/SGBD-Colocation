@base
@donnees

--------------------------------------------------------------------------------
-- ajouté une personne
insert into PERSONNE
values (seq_personne.nextval, 'NOM', 'PRENOM', 'MAIL');

--------------------------------------------------------------------------------
-- ajouté une colocation
insert into COLOCATION
values (seq_colocation.nextval, 'ADRESSE', 'NOM', 'Y', 5);

--------------------------------------------------------------------------------
-- ajouté une personne a une colocation (= crée un contrat member)

insert into CONTRAT_MEMBREV
values (seq_contrat_membre.nextval, '01-DEC-2008', '02-DEC-2009', 5, 5);

insert into CONTRAT_MEMBREV
values (seq_contrat_membre.nextval, '09-DEC-2009', '04-DEC-2009', 3, 4);

select * from CONTRAT_MEMBRE;

--------------------------------------------------------------------------------
-- ajouté abondement
select DATE_ENTREE, DATE_SORTIE
from CONTRAT_MEMBRE
where ID_CONTRAT_MEMBRE = 5;

--l'application vérifie que la date de l'abondement est comprit entre les deux dates

select A_UNE_CAGNOTTE
from COLOCATION, CONTRAT_MEMBRE
where CONTRAT_MEMBRE.ID_COLOCATION = COLOCATION.ID_COLOCATION
and CONTRAT_MEMBRE.ID_CONTRAT_MEMBRE = 5;

--l'application vérifie que a_une_cagnotte = Y

insert into ABONDEMENT
values (seq_abondement.nextval, '01-JAN-2016', 500, 5);

--------------------------------------------------------------------------------
--ajouté un achat colocation
select DATE_ENTREE, DATE_SORTIE
from CONTRAT_MEMBRE
where ID_CONTRAT_MEMBRE = 3;

--l'application vérifie que la date de l'achat est comprit entre les deux dates

insert into ACHAT_COLOCATION
values (seq_colocation.nextval, 'INTITULE', '01-JAN-2016', 500, 2, 3);

--avec la cagnotte
select A_UNE_CAGNOTTE
from COLOCATION
where ID_COLOCATION = 1;

--l'application vérifie que a_une_cagnotte = Y

insert into ACHAT_COLOCATION
values (seq_colocation.nextval, 'UN AUTRE INTITULE', '16-JAN-2016', 500, 1, null);

--------------------------------------------------------------------------------

delete from PERSONNE
where ID_PERSONNE = 1;

select * from CONTRAT_MEMBRE;
