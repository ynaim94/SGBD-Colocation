-- Liste des colocations avec leur gestionnaire (done)

Select NOM_COLOCATION AS COLOCATION,NOM_PERSONNE AS GESTIONNAIRE
from COLOCATION,PERSONNE
where COLOCATION.ID_PERSONNE=PERSONNE.ID_PERSONNE;

-- Ensemble des membres d'un colocation donnée (done)

Select NOM_PERSONNE AS MEMBRE
from COLOCATION, CONTRAT_MEMBRE, PERSONNE
where COLOCATION.ID_COLOCATION=??
      and COLOCATION.ID_COLOCATION=CONTRAT_MEMBRE.ID_COLOCATION
      and CONTRAT_MEMBRE.ID_PERSONNE=PERSONNE.ID_PERSONNE;




-- Liste des achats effectués pour une colocation et pour un mois donné (done)

select AC.INTITULE_ACHAT_COLOCATION AS ACHAT_COLOCATION, AC.MONTANT_ACHAT_COLOCATION as PRIX
from ACHAT_COLOCATION AC, COLOCATION C
where AC.ID_COLOCATION=C.ID_COLOCATION
      and C.ID_COLOCATION=??
      and EXTRACT( MONTH FROM AC.DATE_ACHAT_COLOCATION)=??; --format( 1,2,...,9,10,11,12)




--Liste des colocations pour lesquels aucun achat n'a été enregistré au cours des 6 derniers mois (done)


select NOM_COLOCATION
from COLOCATION 
minus
select NOM_COLOCATION 
from ACHAT_RECENT;


