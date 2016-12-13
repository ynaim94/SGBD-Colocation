-- Ensemble des membres d'un colocation donnée

Select NOM_PERSONNE AS MEMBRE
from COLOCATION, CONTRAT_MEMBRE, PERSONNE
where COLOCATION.ID_COLOCATION=??
      and COLOCATION.ID_COLOCATION=CONTRAT_MEMBRE.ID_COLOCATION
      and CONTRAT_MEMBRE.ID_PERSONNE=PERSONNE.ID_PERSONNE;