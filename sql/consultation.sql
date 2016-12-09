-- Liste des colocations avec leur gestionnaire (testé ok)

Select NOM_COLOCATION AS COLOCATION,NOM_PERSONNE AS GESTIONNAIRE
from COLOCATION,PERSONNE
where COLOCATION.ID_PERSONNE=PERSONNE.ID_PERSONNE;

-- Ensemble des membres d'un colocation donnée (testé ok)

Select NOM_PERSONNE AS MEMBRE
from COLOCATION, CONTRAT_MEMBRE, PERSONNE
where NOM_COLOCATION= 'XX' || ID_COLOCATION=XX
      and COLOCATION.ID_COLOCATION=CONTRAT_MEMBRE.ID_COLOCATION
      and CONTRAT_MEMBRE.ID_PERSONNE=PERSONNE.ID_PERSONNE;




-- Liste des achats effectués pour une colocation et pour un mois donné (compil ok, test sur donnée pas fais)

select AC.INTITULE_ACHAT_COLOCATION AS ACHAT_COLOCATION
from ACHAT_COLOCATION AC, COLOCATION C
where AC.ID_COLOCATION=C.ID_COLOCATION
      and C.NOM_COLOCATION='XX'
      and EXTRACT( MONTH FROM AC.DATE_ACHAT_COLOCATION)=XX; --format( 1,2,...,9,10,11,12)




--Liste des colocations pour lesquels aucun achat n'a été enregistré au cours des 6 derniers mois ( compil ok, test pas fait)

select NOM_COLOCATION AS COLOCATION
from  (	select C.NOM_COLOCATION, COUNT(AC.INTITULE_ACHAT_COLOCATION) AS QUANTITE
      	from ACHAT_COLOCATION AC, COLOCATION C
      	where AC.ID_COLOCATION=C.ID_COLOCATION
      	and AC.DATE_ACHAT_COLOCATION < (SYSDATE - '0000-06-00')
      	group by C.NOM_COLOCATION) A
where A.QUANTITE=0;      
