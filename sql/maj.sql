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
--ajouté un versement
select DATE_ENTREE, DATE_SORTIE
from CONTRAT_MEMBRE
where ID_CONTRAT_MEMBRE = 5;

--l'application vérifie que la date du versement est comprit entre les deux dates

select DATE_ENTREE, DATE_SORTIE
from CONTRAT_MEMBRE
where ID_CONTRAT_MEMBRE = 4;

--l'application vérifie que la date du versement est comprit entre les deux dates

select ID_COLOCATION
from CONTRAT_MEMBRE
where ID_CONTRAT_MEMBRE = 4
or ID_CONTRAT_MEMBRE = 5;

--l'application vérifie que les deux id colocation sont égaux

insert into VERSEMENT
values (seq_versement.nextval, '03-DEC-2016', 500, 5, 4);

--------------------------------------------------------------------------------
--ajouté un achat personnel
--3 achet pour 4 et 5*

select DATE_ENTREE, DATE_SORTIE
from CONTRAT_MEMBRE
where ID_CONTRAT_MEMBRE = 3;

--l'application vérifie que la date de l'achat est comprit entre les deux dates

select DATE_ENTREE, DATE_SORTIE
from CONTRAT_MEMBRE
where ID_CONTRAT_MEMBRE = 4;

--l'application vérifie que la date de l'achat est comprit entre les deux dates

select DATE_ENTREE, DATE_SORTIE
from CONTRAT_MEMBRE
where ID_CONTRAT_MEMBRE = 5;

--l'application vérifie que la date de l'achat est comprit entre les deux dates

select ID_COLOCATION
from CONTRAT_MEMBRE
where ID_CONTRAT_MEMBRE = 4
or ID_CONTRAT_MEMBRE = 5
or ID_CONTRAT_MEMBRE = 3;

--l'application vérifie que les ids colocation sont égaux

insert into ACHAT_PERSONNEL
values (seq_achat_personnel.nextval, 'INTITULEE', '01-JAN-08', 500, 3);

insert into BENEFICIAIRE
values (4, seq_achat_personnel.currval);

insert into BENEFICIAIRE
values (5, seq_achat_personnel.currval);


--------------------------------------------------------------------------------

delete from PERSONNE
where ID_PERSONNE = 1;

select * from CONTRAT_MEMBRE;
