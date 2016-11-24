-- Liste des colocations avec leur gestionnaire

Select COLOCATION_NOM,PERSONNE_NOM
from COLOCATION,PERSONNE
where COLOCATION_ID_PERSONNE=PERSONNE_ID_PERSONNE;

-- Ensemble des membres d'un colocation donnée

Select PERSONNE_NOM
from COLOCATION,CONTRAT_MEMBRE,PERSONNE
where COLOCATION_NOM= XX || COLOCATION_ID_COLOCATION= XX
      and COLOCATION_ID_COLOCATION=CONTRAT_MEMBRE_ID_COLOCATION
      and CONTRAT_MEMBRE_ID_PERSONNE=PERSONNE_ID_PERSONNE;


-- Liste des achats effectués pour une colocation et pour un mois donné;

select *
from ACHAT_COLOCATION AC, COLOCATION C 
where AC.ID_COLOCATION=C.ID_COLOCATION
      and C.NOM=XX
      and AC.DATE><XX 

--Liste des colocations pour lesquels aucun achat n'a été enregistré au cours des 6 derniers mois
