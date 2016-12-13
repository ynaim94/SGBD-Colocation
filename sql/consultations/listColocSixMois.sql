--Liste des colocations pour lesquels aucun achat n'a été enregistré au cours des 6 derniers mois

select NOM_COLOCATION
from COLOCATION
minus
select NOM_COLOCATION
from ACHAT_RECENT;
