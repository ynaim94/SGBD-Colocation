-- Ensemble des membres d'un colocation donnée

Select distinct PERSONNE.ID_PERSONNE AS Identifiant, PERSONNE.NOM_PERSONNE AS nom 
from COLOCATION, CONTRAT_MEMBRE, PERSONNE
where COLOCATION.ID_COLOCATION=?
      and COLOCATION.ID_COLOCATION=CONTRAT_MEMBRE.ID_COLOCATION
      and CONTRAT_MEMBRE.ID_PERSONNE=PERSONNE.ID_PERSONNE;
