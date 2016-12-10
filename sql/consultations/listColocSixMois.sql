--Liste des colocations pour lesquels aucun achat n'a été enregistré au cours des 6 derniers mois ( compil ok, test pas fait)

select NOM_COLOCATION AS COLOCATION
from  (	select C.NOM_COLOCATION, COUNT(AC.INTITULE_ACHAT_COLOCATION) AS QUANTITE
      	from ACHAT_COLOCATION AC, COLOCATION C
      	where AC.ID_COLOCATION=C.ID_COLOCATION
      	and AC.DATE_ACHAT_COLOCATION < (SYSDATE - '0000-06-00')
      	group by C.NOM_COLOCATION) A
where A.QUANTITE=0;      
