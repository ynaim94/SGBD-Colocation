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
